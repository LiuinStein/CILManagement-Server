package cn.opencil.mapper;

import cn.opencil.po.Project;

import java.util.List;

public interface ProjectMapper {

    Integer addProject(Project project);

    Integer deleteProject(Integer id);

    Integer modifyProject(Project project);

    List<Project> querySummaryProjectInfo(Project project);

    List<Project> queryAllProjectInfo(Project project);

}
