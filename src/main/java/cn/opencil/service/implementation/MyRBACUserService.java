package cn.opencil.service.implementation;

import cn.opencil.mapper.RBACUserMapper;
import cn.opencil.po.RBACUser;
import cn.opencil.service.RBACUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("myRBACUserService")
public class MyRBACUserService implements RBACUserService {

    private final RBACUserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MyRBACUserService(RBACUserMapper userMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
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
    public boolean changeUserPassword(Long username, String newPassword) {
        RBACUser user = new RBACUser();
        user.setId(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        return userMapper.changePassword(user) == 1;
    }
}
