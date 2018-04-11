package cn.opencil.po;

import cn.opencil.validation.group.AddProjectValidation;
import cn.opencil.validation.group.NotNullProjectIdValidation;
import cn.opencil.validation.group.database.DatabaseSubjectValidation;
import cn.opencil.validation.group.database.DatabaseUserValidation;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.*;
import java.util.Date;

public class Project {
    @Positive
    @NotNull(groups = {
            NotNullProjectIdValidation.class,
            AddProjectValidation.class
    })
    private Integer id;

    @Size(max = 100)
    @NotNull(groups = {
            AddProjectValidation.class
    })
    private String topic;

    @Size(max = 400)
    private String description;

    @Size(max = 400)
    private String codeUri;

    @Size(max = 400)
    private String docsUri;

    @Positive
    @NotNull(groups = {
            AddProjectValidation.class
    })
    @DatabaseColumnReference(table = "t_rbac_user", column = "id", groups = {
            DatabaseUserValidation.class
    })
    private Long leader;

    @Positive
    @NotNull(groups = {
            AddProjectValidation.class
    })
    @DatabaseColumnReference(table = "t_academic_subject", column = "id", groups = {
            DatabaseSubjectValidation.class
    })
    private Integer subject;

    @Positive
    @NotNull(groups = {
            AddProjectValidation.class
    })
    private Long funding;

    @Size(max = 200)
    private String affiliation;

    @PastOrPresent
    @NotNull(groups = {
            AddProjectValidation.class
    })
    private Date applicationDate;

    @NotNull(groups = {
            AddProjectValidation.class
    })
    private Date startDate;

    @NotNull(groups = {
            AddProjectValidation.class
    })
    private Date deadline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCodeUri() {
        return codeUri;
    }

    public void setCodeUri(String codeUri) {
        this.codeUri = codeUri == null ? null : codeUri.trim();
    }

    public String getDocsUri() {
        return docsUri;
    }

    public void setDocsUri(String docsUri) {
        this.docsUri = docsUri == null ? null : docsUri.trim();
    }

    public Long getLeader() {
        return leader;
    }

    public void setLeader(Long leader) {
        this.leader = leader;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Long getFunding() {
        return funding;
    }

    public void setFunding(Long funding) {
        this.funding = funding;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation == null ? null : affiliation.trim();
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}