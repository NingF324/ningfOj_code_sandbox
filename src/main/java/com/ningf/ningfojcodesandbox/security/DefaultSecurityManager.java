package com.ningf.ningfojcodesandbox.security;

import java.security.Permission;

/**
 * @description:
 * @author: Lenovo
 * @time: 2024/11/14 下午2:22
 */
public class DefaultSecurityManager extends SecurityManager{
    @Override
    public void checkPermission(Permission perm) {
        System.out.println("允许所有权限");
        System.out.println(perm.getActions());
        super.checkPermission(perm);
    }


}
