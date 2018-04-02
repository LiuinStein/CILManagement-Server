package cn.opencil.service.implementation;

import cn.opencil.mapper.RBACUserMapper;
import cn.opencil.mapper.RBACUserRoleMapper;
import cn.opencil.mapper.UserInfoMapper;
import cn.opencil.po.RBACUser;
import cn.opencil.po.RBACUserRole;
import cn.opencil.po.UserInfo;
import cn.opencil.service.RBACUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("myRBACUserService")
public class MyRBACUserService implements RBACUserService {

    private final RBACUserMapper userMapper;
    private final UserInfoMapper infoMapper;
    private final RBACUserRoleMapper roleMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MyRBACUserService(RBACUserMapper userMapper, UserInfoMapper infoMapper, RBACUserRoleMapper roleMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.infoMapper = infoMapper;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user form database
        UserDetails userDetails = userMapper.getUserByUserId(Long.parseLong(username));
        if (userDetails == null) {
            throw new UsernameNotFoundException(username + " was not found.");
        }
        return userDetails;
    }

    @Override
    public boolean changeUserPassword(RBACUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.changePassword(user) == 1;
    }

    @Override
    public boolean addMember(RBACUser user, UserInfo info, RBACUserRole role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.addMember(user) == 1 &&
                infoMapper.addMember(info) == 1 &&
                roleMapper.addUserRole(role) == 1;
    }

    @Override
    public boolean enableOrDisableUser(RBACUser user) {
        return userMapper.enableOrDisableUser(user) == 1;
    }

    @Override
    public void deleteMember(Long username) {
        // The default admin user with id 10001 can not be deleted!
        if (username.equals(10001L)) {
            return;
        }
        userMapper.deleteMember(username);
    }
}
