package cn.opencil.po;

import cn.opencil.validation.group.AddTeamMemberValidation;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.*;

public class TeamMember {
    @Positive
    private Integer id;

    @Positive
    @NotNull(groups = {
            AddTeamMemberValidation.class
    })
    @DatabaseColumnReference(table = "t_team", column = "id", groups = {
            AddTeamMemberValidation.class
    })
    private Integer teamId;

    @Positive
    @NotNull(groups = {
            AddTeamMemberValidation.class
    })
    @DatabaseColumnReference(table = "t_rbac_user", column = "id", groups = {
            AddTeamMemberValidation.class
    })
    private Long personId;

    @Min(value = 0)
    @Max(value = 9)
    @NotNull(groups = {
            AddTeamMemberValidation.class
    })
    private Byte position;

    @Size(max = 300)
    @NotNull(groups = {
            AddTeamMemberValidation.class
    })
    private String jobs;

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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Byte getPosition() {
        return position;
    }

    public void setPosition(Byte position) {
        this.position = position;
    }

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs == null ? null : jobs.trim();
    }
}