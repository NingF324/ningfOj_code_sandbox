package com.ningf.ningfojcodesandbox.model;

import lombok.Data;

/**
 * @description:题目判断
 * @author: Lenovo
 * @time: 2024/10/15 下午7:44
 */
@Data
public class JudgeInfo {
    /**
     * 时间限制(ms)
     */
    private long timeLimit;
    /**
     * 内存限制(kb)
     */
    private long memoryLimit;
    /**
     * 判题信息
     */
    private String message;
}
