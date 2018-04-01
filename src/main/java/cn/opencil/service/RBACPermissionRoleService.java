package cn.opencil.service;

import cn.opencil.po.RBACPermissionRole;

public interface RBACPermissionRoleService {

    boolean grantPermissionToRole(RBACPermissionRole permissionRole);
}
