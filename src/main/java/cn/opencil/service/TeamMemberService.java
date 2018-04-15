package cn.opencil.service;

import cn.opencil.po.TeamMember;

import java.util.List;

public interface TeamMemberService {

    boolean addMemberToTeam(TeamMember teamMember);

    boolean deleteMemberFromTeam(TeamMember teamMember);

    boolean modifyMemberJobs(TeamMember teamMember);

    List<TeamMember> queryTeamMembers(TeamMember teamMember);
}
