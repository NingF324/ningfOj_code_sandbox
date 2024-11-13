package com.ningf.ningfojcodesandbox.model;

import lombok.Data;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/13 下午10:39
 */
@Data
public class ExecuteMessage {
    private Integer exitValue;
    private String message;
    private String errorMessage;
    private Long time;
}
