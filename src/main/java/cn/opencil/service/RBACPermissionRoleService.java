package cn.opencil.service;

import cn.opencil.po.RBACPermissionRole;
import cn.opencil.po.RBACRole;

import java.util.List;

public interface RBACPermissionRoleService {

    boolean grantPermissionToRole(RBACPermissionRole permissionRole);

    boolean revokePermissionFromRole(RBACPermissionRole permissionRole);

    boolean addRole(RBACPermissionRole permissionRole);

    void deleteRole(RBACPermissionRole permissionRole);

    boolean renameRole(RBACPermissionRole permissionRole);

    List<RBACRole> getRoleByPermission(Integer permissionId);
}
