package cn.opencil.service.implementation;

import cn.opencil.mapper.RBACPermissionMapper;
import cn.opencil.po.RBACPermissionRole;
import cn.opencil.service.RBACPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myRBACPermissionService")
public class MyRBACPermissionService implements RBACPermissionService {

    private final RBACPermissionMapper permissionMapper;

    @Autowired
    public MyRBACPermissionService(RBACPermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<RBACPermissionRole> getPermissionByUserId(Long id, boolean isAll) {
        return permissionMapper.getPermissionByUserId(id, isAll);
    }

    @Override
    public List<RBACPermissionRole> getPermissionByRoleId(Integer id, boolean isAll) {
        return permissionMapper.getPermissionByRoleId(id, isAll);
    }
}
