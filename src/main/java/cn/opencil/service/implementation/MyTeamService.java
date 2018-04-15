package cn.opencil.service.implementation;

import cn.opencil.mapper.TeamMapper;
import cn.opencil.mapper.TeamMemberMapper;
import cn.opencil.po.Team;
import cn.opencil.po.TeamMember;
import cn.opencil.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myTeamService")
public class MyTeamService implements TeamService {

    private final TeamMapper teamMapper;
    private final TeamMemberMapper memberMapper;

    @Autowired
    public MyTeamService(TeamMapper teamMapper, TeamMemberMapper memberMapper) {
        this.teamMapper = teamMapper;
        this.memberMapper = memberMapper;
    }

    @Override
    public boolean addTeam(Team team) {
        TeamMember member = new TeamMember();
        member.setPersonId(team.getLeader());
        teamMapper.addTeam(team);
        member.setTeamId(team.getId());
        member.setPosition((byte) 0);
        member.setJobs("to be a leader");
        return memberMapper.addMemberToTeam(member) == 1;
    }

    @Override
    public boolean deleteTeam(Team team) {
        return teamMapper.deleteTeam(team.getId()) != 0;
    }

    @Override
    public boolean modifyTeamInfo(Team team) {
        return teamMapper.modifyTeamInfo(team) == 1;
    }

    @Override
    public List<Team> queryInfoByTeamId(Integer id, boolean isAll) {
        return teamMapper.queryInfoByTeamId(id, isAll);
    }

    @Override
    public List<Team> queryInfoByMemberId(Long id, boolean isAll) {
        return teamMapper.queryInfoByMemberId(id, isAll);
    }

    @Override
    public List<Team> queryInfoByProjectId(Integer id, boolean isAll) {
        return teamMapper.queryInfoByProjectId(id, isAll);
    }
}
