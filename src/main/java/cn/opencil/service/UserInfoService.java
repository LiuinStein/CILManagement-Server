package cn.opencil.service;

import cn.opencil.po.UserInfo;

import java.util.List;

public interface UserInfoService {
    boolean modifyUserInfo(UserInfo info);

    List<UserInfo> querySummaryUserInfo(UserInfo info);

    List<UserInfo> queryAllUserInfo(UserInfo info);
}
