package com.ningf.ningfojcodesandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.dfa.FoundWord;
import cn.hutool.dfa.WordTree;
import com.ningf.ningfojcodesandbox.model.ExecuteCodeRequest;
import com.ningf.ningfojcodesandbox.model.ExecuteCodeResponse;
import com.ningf.ningfojcodesandbox.model.ExecuteMessage;
import com.ningf.ningfojcodesandbox.model.JudgeInfo;
import com.ningf.ningfojcodesandbox.utils.ProcessUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class JavaCodeSandBoxTemplate implements CodeSandBox{
    private static final String GLOBAL_CODE_DIR_NAME = "tmpCode";

    private static final String GLOBAL_JAVA_CLASS_NAME = "Main.java";

    private static final long TIME_OUT = 5000L;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();

        File codeFile = saveCode2File(code);

        ExecuteMessage compileFileExecuteMessage = compileFile(codeFile);

        //执行代码，得到结果
        List<ExecuteMessage> executeMessageList = runFile(codeFile, inputList);

        //收集整理输出结果
        ExecuteCodeResponse outputResponse = getOutputResponse(executeMessageList);

        //多余文件清理
        boolean b = deleteFile(codeFile);
        if(!b){
            log.error("deleteFile error, userCodeFilePath = {}",codeFile.getAbsolutePath());
        }

        return outputResponse;
    }

    /**
     * 1.将用户的题解代码保存为文件
     * @param code
     * @return file
     */
    public File saveCode2File(String code){
        String userDir = System.getProperty("user.dir");
        String globalCodePathName = userDir + File.separator + GLOBAL_CODE_DIR_NAME;
        //判断全局代码目录是否存在
        if (!FileUtil.exist(globalCodePathName)) {
            FileUtil.mkdir(globalCodePathName);
        }

        //把用户代码隔离存放
        String userCodeParentPath = globalCodePathName + File.separator + UUID.randomUUID();
        String userCodePath = userCodeParentPath + File.separator + GLOBAL_JAVA_CLASS_NAME;
        File userCodeFile = FileUtil.writeString(code, userCodePath, StandardCharsets.UTF_8);
        return userCodeFile;
    }

    /**
     * 2.编译代码文件
     * @param codeFile
     * @return
     */
    public ExecuteMessage compileFile(File codeFile){
        String compileCmd = String.format("javac -encoding utf-8 %s", codeFile.getAbsolutePath());
        try {
            Process compileProcess = Runtime.getRuntime().exec(compileCmd);
            ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(compileProcess, "编译");
            System.out.println(executeMessage);
            if(executeMessage.getExitValue()!=0){
                throw new RuntimeException("编译错误");
            }
            return executeMessage;
        } catch (Exception e) {
            //错误处理，提升程序健壮性
            throw new RuntimeException(e);
        }

    }

    /**
     *3. 执行文件，获得执行结果列表
     * @param codeFile
     * @param inputList
     * @return
     */
    public List<ExecuteMessage> runFile(File codeFile,List<String> inputList) {
        String userCodeParentPath = codeFile.getParentFile().getAbsolutePath();
        List<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String inputArgs : inputList) {
            //使用-Xmx+内存大小，限制JVM占用的内存
            //String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s;%s -Djava.security.manager=%s Main %s", userCodeParentPath,SECURITY_MANAGER_PATH, SECURITY_MANAGER_CLASS_NAME,inputArgs);
            String runCmd = String.format("java -Xmx256m -Dfile.encoding=UTF-8 -cp %s Main %s", userCodeParentPath,inputArgs);
            try {
                Process runProcess = Runtime.getRuntime().exec(runCmd);
                //通过设置删除进程的睡眠，停止占用系统过久的进程
                new Thread(() -> {
                    try {
                        Thread.sleep(TIME_OUT);
                        System.out.println("已超时");
                        runProcess.destroy();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                ExecuteMessage executeMessage = ProcessUtils.runProcessAndGetMessage(runProcess, "运行");
                executeMessageList.add(executeMessage);
                System.out.println(executeMessage);
            } catch (Exception e) {
                //错误处理，提升程序健壮性
                throw new RuntimeException("程序执行错误",e);
            }
        }
        return executeMessageList;
    }

    /**
     * 4.收集整理输出结果
     * @param executeMessageList
     * @return
     */
    public ExecuteCodeResponse getOutputResponse(List<ExecuteMessage> executeMessageList){
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        List<String> outputList = new ArrayList<>();
        long maxTime = 0;
        for (ExecuteMessage executeMessage : executeMessageList) {
            if (StrUtil.isNotBlank(executeMessage.getErrorMessage())) {
                executeCodeResponse.setMessage(executeMessage.getErrorMessage());
                //执行中存在错误
                executeCodeResponse.setStatus(3);
                break;
            }
            outputList.add(executeMessage.getMessage());
            Long time = executeMessage.getTime();
            if (time != null) {
                maxTime = Math.max(maxTime, time);
            }
        }
        //执行代码正常
        if (outputList.size() == executeMessageList.size()) {
            executeCodeResponse.setStatus(1);
        }
        executeCodeResponse.setOutputList(outputList);

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTimeLimit(maxTime);
        //todo 获取memory使用
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }

    /**
     * 5.清理文件
     * @param codeFile
     * @return
     */
    public boolean deleteFile(File codeFile){
        if (codeFile.getParentFile() != null) {
            String userCodeParentPath = codeFile.getParentFile().getAbsolutePath();
            boolean del = FileUtil.del(userCodeParentPath);
            System.out.println("清理" + (del ? "成功" : "失败"));
            return del;
        }

        return true;
    }

    /**
     * 6.获取错误响应的信息
     *
     * @param e
     * @return
     */
    private ExecuteCodeResponse getErrorResponse(Throwable e) {
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(new ArrayList<>());
        executeCodeResponse.setMessage(e.getMessage());
        executeCodeResponse.setStatus(2);
        executeCodeResponse.setJudgeInfo(new JudgeInfo());
        return executeCodeResponse;
    }
}
