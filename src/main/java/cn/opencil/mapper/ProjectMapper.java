package cn.opencil.mapper;

import cn.opencil.po.Project;

public interface ProjectMapper {

    Integer addProject(Project project);

    Integer deleteProject(Integer id);

}
