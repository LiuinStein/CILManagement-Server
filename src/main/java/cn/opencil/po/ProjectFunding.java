package cn.opencil.po;

import com.shaoqunliu.validation.annotation.DatabaseColumnReference;
import com.shaoqunliu.validation.annotation.DigitsPattern;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Date;

public class ProjectFunding {
    @Positive
    private Integer id;

    @DigitsPattern(regexp = "-?1")
    private Byte flow;

    @Positive
    private Long amount;

    @PositiveOrZero
    private Long balance;

    @Positive
    @DatabaseColumnReference(table = "t_project", column = "id")
    private Integer projectId;

    @Size(max = 100)
    private String note;

    private Date revenueDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getFlow() {
        return flow;
    }

    public void setFlow(Byte flow) {
        this.flow = flow;
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