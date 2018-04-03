package cn.opencil.mapper;

import cn.opencil.po.RBACPermissionRole;
import cn.opencil.po.RBACRole;

import java.util.List;

public interface RBACPermissionRoleMapper {
    List<RBACPermissionRole> getPermissionRoleMapper();

    List<RBACRole> getRoleByPermission(Integer id);

    Integer grantPermissionToRole(RBACPermissionRole permissionRole);

    Integer revokePermissionFromRole(RBACPermissionRole permissionRole);

    Integer addRole(RBACPermissionRole permissionRole);

    Integer deleteRole(Integer id);

    Integer renameRole(RBACPermissionRole permissionRole);
}
