package cn.opencil.service.implementation;

import cn.opencil.mapper.RBACPermissionRoleMapper;
import cn.opencil.po.RBACPermissionRole;
import cn.opencil.security.MySecurityMetadataSource;
import cn.opencil.service.RBACPermissionRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myRBACPermissionRoleService")
public class MyRBACPermissionRoleService implements RBACPermissionRoleService {

    private final RBACPermissionRoleMapper permissionRoleMapper;

    @Autowired
    public MyRBACPermissionRoleService(RBACPermissionRoleMapper permissionRoleMapper) {
        this.permissionRoleMapper = permissionRoleMapper;
    }

    private void refreshPermission() {
        new MySecurityMetadataSource(permissionRoleMapper).refreshPermissions();
    }

    @Override
    public boolean grantPermissionToRole(RBACPermissionRole permissionRole) {
        if (permissionRoleMapper.grantPermissionToRole(permissionRole) == 1) {
            refreshPermission();
            return true;
        }
        return false;
    }

    @Override
    public boolean revokePermissionFromRole(RBACPermissionRole permissionRole) {
        if (permissionRoleMapper.revokePermissionFromRole(permissionRole) == 1) {
            refreshPermission();
            return true;
        }
        return false;
    }

    @Override
    public boolean addRole(RBACPermissionRole permissionRole) {
        // no need for refresh role-permission table here
        return permissionRoleMapper.addRole(permissionRole) == 1;
    }

    @Override
    public void deleteRole(RBACPermissionRole permissionRole) {
        permissionRoleMapper.deleteRole(permissionRole.getRoleId());
    }

    @Override
    public boolean renameRole(RBACPermissionRole permissionRole) {
        if (permissionRoleMapper.renameRole(permissionRole) == 1) {
            refreshPermission();
            return true;
        }
        return false;
    }
}
