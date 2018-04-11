package cn.opencil.service;

import cn.opencil.po.Team;

public interface TeamService {

    boolean addTeam(Team team);

    boolean deleteTeam(Team team);
}
