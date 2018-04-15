package cn.opencil.service.implementation;

import cn.opencil.mapper.ProjectMapper;
import cn.opencil.po.Project;
import cn.opencil.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myProjectService")
public class MyProjectService implements ProjectService {

    private final ProjectMapper projectMapper;

    @Autowired
    public MyProjectService(ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
    }

    @Override
    public boolean addProject(Project project) {
        return projectMapper.addProject(project) == 1;
    }

    @Override
    public boolean deleteProject(Project project) {
        return projectMapper.deleteProject(project.getId()) != 0;
    }

    @Override
    public boolean modifyProject(Project project) {
        return projectMapper.modifyProject(project) == 1;
    }

    @Override
    public List<Project> querySummaryProjectInfo(Project project) {
        return projectMapper.querySummaryProjectInfo(project);
    }

    @Override
    public List<Project> queryAllProjectInfo(Project project) {
        return projectMapper.queryAllProjectInfo(project);
    }

}
