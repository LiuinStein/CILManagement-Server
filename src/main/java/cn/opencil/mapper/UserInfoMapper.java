package cn.opencil.mapper;

import cn.opencil.po.UserDepartment;
import cn.opencil.po.UserInfo;

import java.util.List;

public interface UserInfoMapper {

    Integer addMember(UserInfo info);

    Integer modifyInfo(UserInfo info);

    List<UserInfo> querySummaryInfo(UserInfo info);

    List<UserInfo> queryAllInfo(UserInfo info);

    List<UserDepartment> getCollege();

    List<UserDepartment> getClass(Integer collegeId);
}
