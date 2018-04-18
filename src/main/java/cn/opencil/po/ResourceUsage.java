package cn.opencil.po;

import cn.opencil.validation.group.AddResourceUsageValidation;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

public class ResourceUsage {
    @Positive
    private Integer id;

    @Positive
    @NotNull(groups = {
            AddResourceUsageValidation.class
    })
    @DatabaseColumnReference(table = "t_resource", column = "id", groups = {
            AddResourceUsageValidation.class
    })
    private Integer resourceId;

    @Positive
    @NotNull(groups = {
            AddResourceUsageValidation.class
    })
    @DatabaseColumnReference(table = "t_rbac_user", column = "id", groups = {
            AddResourceUsageValidation.class
    })
    private Long userId;

    @Positive
    @NotNull(groups = {
            AddResourceUsageValidation.class
    })
    private Integer usageAmount;

    @FutureOrPresent
    @NotNull(groups = {
            AddResourceUsageValidation.class
    })
    private Date startDate;

    @NotNull(groups = {
            AddResourceUsageValidation.class
    })
    private Date endDate;

    @Size(max = 200)
    @NotNull(groups = {
            AddResourceUsageValidation.class
    })
    private String purpose;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUsageAmount() {
        return usageAmount;
    }

    public void setUsageAmount(Integer usageAmount) {
        this.usageAmount = usageAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }
}