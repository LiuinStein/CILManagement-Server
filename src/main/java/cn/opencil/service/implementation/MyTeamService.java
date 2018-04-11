package cn.opencil.service.implementation;

import cn.opencil.mapper.TeamMapper;
import cn.opencil.po.Team;
import cn.opencil.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myTeamService")
public class MyTeamService implements TeamService {

    private final TeamMapper teamMapper;

    @Autowired
    public MyTeamService(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @Override
    public boolean addTeam(Team team) {
        return teamMapper.addTeam(team) == 1;
    }

    @Override
    public boolean deleteTeam(Team team) {
        return teamMapper.deleteTeam(team.getId()) != 0;
    }

    @Override
    public boolean modifyTeamInfo(Team team) {
        return teamMapper.modifyTeamInfo(team) == 1;
    }
}
