package cn.opencil.service.implementation;

import cn.opencil.mapper.RBACUserMapper;
import cn.opencil.po.RBACUser;
import cn.opencil.service.RBACUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("myRBACUserService")
public class MyRBACUserService implements RBACUserService {

    private final RBACUserMapper userMapper;

    @Autowired
    public MyRBACUserService(RBACUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get user form database
        return userMapper.getUserByUserId(Long.parseLong(username));
    }
}
