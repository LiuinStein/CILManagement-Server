package cn.opencil.service.implementation;

import cn.opencil.mapper.RBACRoleMapper;
import cn.opencil.po.RBACRole;
import cn.opencil.service.RBACRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myRBACRoleService")
public class MyRBACRoleService implements RBACRoleService {

    private final RBACRoleMapper roleMapper;

    @Autowired
    public MyRBACRoleService(RBACRoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RBACRole> getRole(RBACRole role) {
        return roleMapper.getRole(role);
    }
}
