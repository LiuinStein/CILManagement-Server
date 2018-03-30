package cn.opencil.service.implementation;

import cn.opencil.mapper.UserInfoMapper;
import cn.opencil.po.UserInfo;
import cn.opencil.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myUserInfoService")
public class MyUserInfoService implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public MyUserInfoService(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public boolean modifyUserInfo(UserInfo info) {
        return userInfoMapper.modifyInfo(info) == 1;
    }
}
