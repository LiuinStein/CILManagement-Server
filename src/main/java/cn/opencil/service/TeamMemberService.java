package cn.opencil.service;

import cn.opencil.po.TeamMember;

public interface TeamMemberService {

    boolean addMemberToTeam(TeamMember teamMember);

    boolean deleteMemberFromTeam(TeamMember teamMember);

    boolean modifyMemberJobs(TeamMember teamMember);
}
