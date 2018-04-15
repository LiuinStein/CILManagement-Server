package cn.opencil.po;

import cn.opencil.validation.group.AddFundingExpenditureValidation;
import com.alibaba.fastjson.annotation.JSONField;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.*;
import java.util.Date;

public class ProjectFunding {
    @Positive
    private Integer id;

    @NotNull(groups = {
            AddFundingExpenditureValidation.class
    })
    private Long amount;

    @PositiveOrZero
    private Long balance;

    @Positive
    @NotNull(groups = {
            AddFundingExpenditureValidation.class
    })
    @DatabaseColumnReference(table = "t_project", column = "id")
    private Integer projectId;

    @Size(max = 100)
    @NotNull(groups = {
            AddFundingExpenditureValidation.class
    })
    private String note;

    @PastOrPresent
    @NotNull(groups = {
            AddFundingExpenditureValidation.class
    })
    @JSONField(name = "date")
    private Date revenueDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getRevenueDate() {
        return revenueDate;
    }

    public void setRevenueDate(Date revenueDate) {
        this.revenueDate = revenueDate;
    }
}