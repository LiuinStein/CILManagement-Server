package cn.opencil.service.implementation;

import cn.opencil.mapper.RBACUserRoleMapper;
import cn.opencil.po.RBACUserRole;
import cn.opencil.service.RBACUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myRBACUserRoleService")
public class MyRBACUserRoleService implements RBACUserRoleService {
    private final RBACUserRoleMapper userRoleMapper;

    @Autowired
    public MyRBACUserRoleService(RBACUserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public List<Byte> getRoleByUser(Long userId) {
        return userRoleMapper.getRoleByUser(userId);
    }

    @Override
    public boolean revokeRoleFromUser(RBACUserRole userRole) {
        if (userRole.getUserId().equals(10001L)) {
            /*
             * must be true here!
             * look at the assignRoleToUser method
             */
            return true;
        }
        return userRoleMapper.revokeRoleFromUser(userRole) != 0;
    }

    @Override
    public boolean assignRoleToUser(RBACUserRole userRole) {
        /*
         * If the assignee's current role is admin or the new role is admin,
         * then the assign operation will first take back his current role and
         * reassign the new role.
         */
        if (userRole.getRoleId().equals((byte) 1) || getRoleByUser(userRole.getUserId()).contains((byte) 1)) {
            RBACUserRole tmp = new RBACUserRole();
            tmp.setUserId(userRole.getUserId());
            tmp.setRoleId(null);
            if (!revokeRoleFromUser(tmp)) {
                return false;
            }
        }
        return userRoleMapper.addUserRole(userRole) == 1;
    }
}
