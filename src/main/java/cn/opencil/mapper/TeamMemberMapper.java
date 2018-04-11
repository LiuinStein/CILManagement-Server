package cn.opencil.mapper;

import cn.opencil.po.TeamMember;

public interface TeamMemberMapper {

    Integer addMemberToTeam(TeamMember teamMember);

    Integer deleteMemberFromTeam(TeamMember teamMember);

    Integer modifyMemberJobs(TeamMember teamMember);
}
