package cn.opencil.mapper;

import cn.opencil.po.Team;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamMapper {

    Integer addTeam(Team team);

    Integer deleteTeam(Integer id);

    Integer modifyTeamInfo(Team team);

    List<Team> queryInfoByTeamId(@Param("id") Integer id, @Param("isAll") Boolean isAll);

    List<Team> queryInfoByMemberId(@Param("id") Long id, @Param("isAll") Boolean isAll);

    List<Team> queryInfoByProjectId(@Param("id") Integer id, @Param("isAll") Boolean isAll);

}
