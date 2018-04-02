package cn.opencil.service;

import cn.opencil.po.RBACUserRole;

import java.util.List;

public interface RBACUserRoleService {
    boolean assignRoleToUser(RBACUserRole userRole);

    List<Byte> getRoleByUser(Long userId);

    boolean revokeRoleFromUser(RBACUserRole userRole);
}
