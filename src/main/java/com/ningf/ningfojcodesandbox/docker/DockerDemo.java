package com.ningf.ningfojcodesandbox.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.PingCmd;
import com.github.dockerjava.core.DockerClientBuilder;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/15 上午8:40
 */
public class DockerDemo {

    public static void main(String[] args) {
        //获取默认的DockerClient
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        PingCmd pingCmd = dockerClient.pingCmd();
        pingCmd.exec();
    }
}
