package cn.opencil.service;

import cn.opencil.po.RBACPermissionRole;

import java.util.List;

public interface RBACPermissionService {

    List<RBACPermissionRole> getPermissionByUserId(Long id, boolean isAll);

    List<RBACPermissionRole> getPermissionByRoleId(Integer id, boolean isAll);
}
