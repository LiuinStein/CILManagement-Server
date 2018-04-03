package cn.opencil.mapper;

import cn.opencil.po.RBACRole;

import java.util.List;

public interface RBACRoleMapper {

    List<RBACRole> getRole(RBACRole role);

}
