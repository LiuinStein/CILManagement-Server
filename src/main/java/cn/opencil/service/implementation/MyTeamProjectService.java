package cn.opencil.service.implementation;

import cn.opencil.mapper.TeamProjectMapper;
import cn.opencil.po.TeamProject;
import cn.opencil.service.TeamProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myTeamProjectService")
public class MyTeamProjectService implements TeamProjectService {

    private final TeamProjectMapper teamProjectMapper;

    @Autowired
    public MyTeamProjectService(TeamProjectMapper teamProjectMapper) {
        this.teamProjectMapper = teamProjectMapper;
    }

    @Override
    public boolean assignProjectToTeam(TeamProject teamProject) {
        return teamProjectMapper.assignProjectToTeam(teamProject) == 1;
    }

    @Override
    public boolean takeBackProjectFromTeam(TeamProject teamProject) {
        return teamProjectMapper.takeBackProjectFromTeam(teamProject) != 0;
    }
}
