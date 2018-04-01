package cn.opencil.service;

import cn.opencil.po.RBACPermissionRole;

public interface RBACPermissionRoleService {

    boolean grantPermissionToRole(RBACPermissionRole permissionRole);

    boolean revokePermissionFromRole(RBACPermissionRole permissionRole);

    boolean addRole(RBACPermissionRole permissionRole);
}
