package com.ningf.ningfojcodesandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/11 下午5:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    private List<String> outputList;
    private String message;
    //1为正常，2为代码沙箱错误，3为提交代码执行错误
    private Integer status;
    private JudgeInfo judgeInfo;
}
