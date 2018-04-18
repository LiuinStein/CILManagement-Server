package cn.opencil.po;

import cn.opencil.validation.group.AddResourceUsageValidation;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.*;
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

    @NotNull(groups = {
            AddResourceUsageValidation.class
    })
    private Integer amount;

    @PastOrPresent
    @NotNull(groups = {
            AddResourceUsageValidation.class
    })
    private Date transactionDate;

    @Size(max = 200)
    @NotNull(groups = {
            AddResourceUsageValidation.class
    })
    private String note;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}