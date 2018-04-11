package cn.opencil.service;

import cn.opencil.po.Project;

public interface ProjectService {

    boolean addProject(Project project);

    boolean deleteProject(Project project);

    boolean modifyProject(Project project);
}
