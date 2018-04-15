package cn.opencil.service;

import cn.opencil.po.Team;

import java.util.List;

public interface TeamService {

    boolean addTeam(Team team);

    boolean deleteTeam(Team team);

    boolean modifyTeamInfo(Team team);

    List<Team> queryInfoByTeamId(Integer id, boolean isAll);

    List<Team> queryInfoByMemberId(Long id, boolean isAll);

    List<Team> queryInfoByProjectId(Integer id, boolean isAll);
}
