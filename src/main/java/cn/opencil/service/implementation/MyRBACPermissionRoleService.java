package cn.opencil.service.implementation;

import cn.opencil.mapper.RBACPermissionRoleMapper;
import cn.opencil.po.RBACPermissionRole;
import cn.opencil.po.RBACRole;
import cn.opencil.security.MySecurityMetadataSource;
import cn.opencil.service.RBACPermissionRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myRBACPermissionRoleService")
public class MyRBACPermissionRoleService implements RBACPermissionRoleService {

    private final RBACPermissionRoleMapper permissionRoleMapper;

    @Autowired
    public MyRBACPermissionRoleService(RBACPermissionRoleMapper permissionRoleMapper) {
        this.permissionRoleMapper = permissionRoleMapper;
    }

    private void refreshPermission() {
        /*
         * no need to invoke .refreshPermissions() method due to
         * the constructor of this class will automatically invoke it
         */
        new MySecurityMetadataSource(permissionRoleMapper);
    }

    @Override
    public boolean grantPermissionToRole(RBACPermissionRole permissionRole) {
        if (permissionRole.getRoleId().equals(1)) {
            return true;
        }
        if (permissionRoleMapper.grantPermissionToRole(permissionRole) == 1) {
            refreshPermission();
            return true;
        }
        return false;
    }

    @Override
    public boolean revokePermissionFromRole(RBACPermissionRole permissionRole) {
        if (permissionRole.getRoleId().equals(1)) {
            return true;
        }
        if (permissionRoleMapper.revokePermissionFromRole(permissionRole) == 1) {
            refreshPermission();
            return true;
        }
        return false;
    }

    @Override
    public boolean addRole(RBACPermissionRole permissionRole) {
        // no need for refresh role-permission table here
        // we use UNIQUE key at database to ensure only one default role named admin
        return permissionRoleMapper.addRole(permissionRole) == 1;
    }

    @Override
    public void deleteRole(RBACPermissionRole permissionRole) {
        if (permissionRole.getRoleId().equals(1)) {
            return;
        }
        permissionRoleMapper.deleteRole(permissionRole.getRoleId());
    }

    @Override
    public boolean renameRole(RBACPermissionRole permissionRole) {
        if (permissionRole.getRoleId().equals(1)) {
            return true;
        }
        if (permissionRoleMapper.renameRole(permissionRole) == 1) {
            refreshPermission();
            return true;
        }
        return false;
    }

    @Override
    public List<RBACRole> getRoleByPermission(Integer permissionId) {
        return permissionRoleMapper.getRoleByPermission(permissionId);
    }
}
