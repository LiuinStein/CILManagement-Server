package cn.opencil.service;

import cn.opencil.po.Project;

import java.util.List;

public interface ProjectService {

    boolean addProject(Project project);

    boolean deleteProject(Project project);

    boolean modifyProject(Project project);

    List<Project> querySummaryProjectInfo(Project project);

    List<Project> queryAllProjectInfo(Project project);
}
