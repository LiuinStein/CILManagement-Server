package cn.opencil.service;

import cn.opencil.po.RBACUser;
import cn.opencil.po.RBACUserRole;
import cn.opencil.po.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RBACUserService extends UserDetailsService {
    boolean changeUserPassword(Long username, String newPassword);
    boolean addMember(RBACUser user, UserInfo info, RBACUserRole role);
    boolean deleteMember(Long username);
}
