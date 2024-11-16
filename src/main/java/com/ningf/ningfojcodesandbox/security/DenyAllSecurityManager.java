package com.ningf.ningfojcodesandbox.security;

import java.security.Permission;

/**
 * @description: 禁止所有权限
 * @author: Lenovo
 * @time: 2024/11/14 下午2:22
 */
public class DenyAllSecurityManager extends SecurityManager{
    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException("权限异常"+perm.toString());
    }
}
