package cn.opencil.mapper;

import cn.opencil.po.TeamMember;

import java.util.List;

public interface TeamMemberMapper {

    Integer addMemberToTeam(TeamMember teamMember);

    Integer deleteMemberFromTeam(TeamMember teamMember);

    Integer modifyMemberJobs(TeamMember teamMember);

    List<TeamMember> queryTeamMembers(TeamMember teamMember);
}
