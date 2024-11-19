package com.ningf.ningfojcodesandbox;

import com.ningf.ningfojcodesandbox.model.ExecuteCodeRequest;
import com.ningf.ningfojcodesandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;


@Component
public class JavaNativeCodeSandBox extends JavaCodeSandBoxTemplate{

    //使用模板方法对JavaCodeSandBoxTemplate中部分方法进行重写，减少代码量

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }

}
