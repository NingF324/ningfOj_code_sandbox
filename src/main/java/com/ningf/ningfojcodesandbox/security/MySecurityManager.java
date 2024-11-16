package com.ningf.ningfojcodesandbox.security;

import java.security.Permission;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/14 下午2:22
 */
public class MySecurityManager extends SecurityManager{
    @Override
    public void checkPermission(Permission perm) {
        System.out.println("权限校验通过");
        System.out.println(perm.getActions());
        super.checkPermission(perm);
    }

    @Override
    public void checkExec(String cmd) {
        throw new SecurityException("checkExec 权限校验失败：" + cmd);
    }

    @Override
    public void checkRead(String file) {
        throw new SecurityException("checkRead 权限校验失败：" + file);
    }

    @Override
    public void checkWrite(String file) {
        throw new SecurityException("checkWrite 权限校验失败：" + file);
    }

    @Override
    public void checkDelete(String file) {
        throw new SecurityException("checkDelete 权限校验失败：" + file);
    }

    @Override
    public void checkConnect(String host, int port) {
        throw new SecurityException("checkConnect 权限校验失败：" + host + ":"+ port);
    }
}
