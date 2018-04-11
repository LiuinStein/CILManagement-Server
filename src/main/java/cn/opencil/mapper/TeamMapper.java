package cn.opencil.mapper;

import cn.opencil.po.Team;

public interface TeamMapper {

    Integer addTeam(Team team);

    Integer deleteTeam(Integer id);

    Integer modifyTeamInfo(Team team);

}
