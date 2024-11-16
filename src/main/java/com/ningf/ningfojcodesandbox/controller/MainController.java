package com.ningf.ningfojcodesandbox.controller;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/13 下午6:59
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class MainController {

    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }
}
