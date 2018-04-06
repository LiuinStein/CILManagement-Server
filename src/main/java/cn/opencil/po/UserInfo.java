package cn.opencil.po;

import cn.opencil.validation.group.database.DatabaseClassValidation;
import cn.opencil.validation.group.database.DatabaseCollegeValidation;
import cn.opencil.validation.group.ModifyUserInfoValidation;
import cn.opencil.validation.group.RegisterValidation;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;
import org.aspectj.lang.annotation.Before;

import javax.validation.constraints.*;
import java.util.Date;

public class UserInfo {
    @Positive
    @NotNull(groups = {
            RegisterValidation.class,
            ModifyUserInfoValidation.class
    })
    @DatabaseColumnReference(table = "t_rbac_user", column = "id")
    private Long id;

    @Size(max = 30)
    @NotEmpty(groups = {
            RegisterValidation.class
    })
    private String name;

    @Min(value = 0)
    @Max(value = 1)
    @NotNull(groups = {
            RegisterValidation.class
    })
    private Byte gender;

    @Positive
    @NotNull(groups = {
            RegisterValidation.class
    })
    @DatabaseColumnReference(table = "t_class", column = "id", groups = {
            DatabaseClassValidation.class
    })
    @DatabaseColumnReference(table = "t_college", column = "id", groups = {
            DatabaseCollegeValidation.class
    })
    private Integer department;

    @Past
    @NotNull(groups = {
            RegisterValidation.class
    })
    private Date enrollTime;

    @Past
    private Date exitTime;

    @Past
    private Date birthday;

    @Email
    @Size(max = 30)
    private String email;

    @Size(max = 20)
    private String phone;

    @Size(max = 400)
    private String achievement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name.trim();
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Date getEnrollTime() {
        return enrollTime;
    }

    public void setEnrollTime(Date enrollTime) {
        this.enrollTime = enrollTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? "" : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? "" : phone.trim();
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement == null ? "" : achievement.trim();
    }
}