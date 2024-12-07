package com.ningf.ningfojcodesandbox.controller;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/13 下午6:59
 */

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.ningf.ningfojcodesandbox.JavaNativeCodeSandBox;
import com.ningf.ningfojcodesandbox.model.ExecuteCodeRequest;
import com.ningf.ningfojcodesandbox.model.ExecuteCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController("/")
public class MainController {
    //todo AUTH_REQUEST_SECRET需要加密
    //定义鉴权请求头与密钥
    private static final String ACCESS_KEY = "auth";

    private static final String SECRET_KEY = "secretKey";

    @Resource
    private JavaNativeCodeSandBox javaNativeCodeSandBox;

    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }

    /**
     * 执行用户的题解并得到结果
     * @param executeCodeRequest
     * @return
     */
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest , HttpServletRequest request, HttpServletResponse response){
        String accessKey = request.getHeader("accessKey");
        String nonce = request.getHeader("nonce");
        String timestamp = request.getHeader("timestamp");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        // 校验权限,这里模拟一下,直接判断 accessKey 是否为"ACCESS_KEY",实际应该查询数据库验证权限
        // todo 前端传过来的accessKey应该在数据库中查询是否分配，nonce与SECRET_KEY应该在数据库中查询后判断是否匹配
        if (!accessKey.equals(ACCESS_KEY)){
            throw new RuntimeException("无权限");
        }
        if (nonce.length() > 10000) {
            throw new RuntimeException("无权限");
        }
        long currentTime = System.currentTimeMillis()/1000;
        if(Math.abs(Long.parseLong(timestamp)-currentTime)>300){
            throw new RuntimeException("无权限");
        }
        if(!genSign(body,SECRET_KEY).equals(sign)){
            throw new RuntimeException("无权限");
        }
        if(executeCodeRequest == null){
            throw new RuntimeException("请求参数为空");
        }
        return javaNativeCodeSandBox.executeCode(executeCodeRequest);
    }

    /**
     * 生成签名
     * @param body 包含需要签名的参数的哈希映射
     * @param secretKey 密钥
     * @return 生成的签名字符串
     */
    public static String genSign(String body, String secretKey) {
        // 使用SHA256算法的Digester
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        // 构建签名内容，将哈希映射转换为字符串并拼接密钥
        String content = body + "." + secretKey;
        // 计算签名的摘要并返回摘要的十六进制表示形式
        return md5.digestHex(content);
    }
}