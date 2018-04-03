package cn.opencil.service;

import cn.opencil.po.RBACRole;
import cn.opencil.po.RBACUserRole;

import java.util.List;

public interface RBACUserRoleService {
    boolean assignRoleToUser(RBACUserRole userRole);

    List<RBACRole> getRoleByUser(Long userId);

    boolean revokeRoleFromUser(RBACUserRole userRole);
}
