package cn.opencil.mapper;

import cn.opencil.po.UserInfo;

public interface UserInfoMapper {
    Integer addMember(UserInfo info);
    Integer modifyInfo(UserInfo info);
}
