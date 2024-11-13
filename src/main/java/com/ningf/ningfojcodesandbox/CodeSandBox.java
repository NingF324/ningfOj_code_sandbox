package com.ningf.ningfojcodesandbox;


import com.ningf.ningfojcodesandbox.model.ExecuteCodeRequest;
import com.ningf.ningfojcodesandbox.model.ExecuteCodeResponse;

public interface CodeSandBox {

    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
