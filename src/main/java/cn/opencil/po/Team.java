package cn.opencil.po;

import cn.opencil.validation.group.NotNullTeamIdValidation;
import cn.opencil.validation.group.OrganizeTeamVlidation;
import cn.opencil.validation.group.database.DatabaseUserValidation;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class Team {
    @Positive
    @NotNull(groups = {
            NotNullTeamIdValidation.class
    })
    private Integer id;

    @Positive
    @NotNull(groups = {
            OrganizeTeamVlidation.class
    })
    @DatabaseColumnReference(table = "t_rbac_user", column = "id", groups = {
            DatabaseUserValidation.class,
            OrganizeTeamVlidation.class
    })
    private Long leader;

    @Size(max = 30)
    @NotNull(groups = {
            OrganizeTeamVlidation.class
    })
    private String title;

    @Size(max = 300)
    @NotNull(groups = {
            OrganizeTeamVlidation.class
    })
    private String description;

    @Size(max = 50)
    @NotNull(groups = {
            OrganizeTeamVlidation.class
    })
    private String slogan;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan == null ? null : slogan.trim();
    }
}