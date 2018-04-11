package cn.opencil.service.implementation;

import cn.opencil.mapper.TeamMemberMapper;
import cn.opencil.po.TeamMember;
import cn.opencil.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myTeamMemberService")
public class MyTeamMemberService implements TeamMemberService {

    private final TeamMemberMapper teamMemberMapper;

    @Autowired
    public MyTeamMemberService(TeamMemberMapper teamMemberMapper) {
        this.teamMemberMapper = teamMemberMapper;
    }

    @Override
    public boolean addMemberToTeam(TeamMember teamMember) {
        return teamMemberMapper.addMemberToTeam(teamMember) == 1;
    }

    @Override
    public boolean deleteMemberFromTeam(TeamMember teamMember) {
        return teamMemberMapper.deleteMemberFromTeam(teamMember) != 0;
    }

    @Override
    public boolean modifyMemberJobs(TeamMember teamMember) {
        return teamMemberMapper.modifyMemberJobs(teamMember) == 1;
    }
}
