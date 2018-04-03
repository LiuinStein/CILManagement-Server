package cn.opencil.mapper;

import cn.opencil.po.RBACRole;
import cn.opencil.po.RBACUserRole;

import java.util.List;

public interface RBACUserRoleMapper {
    Integer addUserRole(RBACUserRole userRole);

    List<RBACRole> getRoleByUser(Long id);

    Integer revokeRoleFromUser(RBACUserRole userRole);
}
