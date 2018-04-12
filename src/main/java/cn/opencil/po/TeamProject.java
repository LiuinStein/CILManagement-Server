package cn.opencil.po;

import cn.opencil.validation.group.database.DatabaseProjectIdValidation;
import cn.opencil.validation.group.database.DatabaseTeamIdValidation;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.Positive;

public class TeamProject {
    @Positive
    private Integer id;

    @Positive
    @DatabaseColumnReference(table = "t_team", column = "id", groups = {
            DatabaseTeamIdValidation.class
    })
    private Integer teamId;

    @Positive
    @DatabaseColumnReference(table = "t_project", column = "id", groups = {
            DatabaseProjectIdValidation.class
    })
    private Integer projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}