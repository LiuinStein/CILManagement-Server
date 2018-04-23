package cn.opencil.service.implementation;

import cn.opencil.mapper.UserInfoMapper;
import cn.opencil.po.UserDepartment;
import cn.opencil.po.UserInfo;
import cn.opencil.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<UserInfo> querySummaryUserInfo(UserInfo info) {
        return userInfoMapper.querySummaryInfo(info);
    }

    @Override
    public List<UserInfo> queryAllUserInfo(UserInfo info) {
        return userInfoMapper.queryAllInfo(info);
    }

    @Override
    public List<UserDepartment> getCollege() {
        return userInfoMapper.getCollege();
    }

    @Override
    public List<UserDepartment> getClass(Integer collegeId) {
        return userInfoMapper.getClass(collegeId);
    }
}
