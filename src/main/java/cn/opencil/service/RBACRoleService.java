package cn.opencil.service;

import cn.opencil.po.RBACRole;

import java.util.List;

public interface RBACRoleService {

    List<RBACRole> getRole(RBACRole role);
}
