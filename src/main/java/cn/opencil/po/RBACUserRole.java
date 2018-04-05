package cn.opencil.po;

import cn.opencil.validation.group.RegisterValidation;
import com.alibaba.fastjson.annotation.JSONField;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RBACUserRole {
    @Positive
    private Integer id;

    @JSONField(name = "id")
    @Positive
    @NotNull(groups = {RegisterValidation.class})
    @DatabaseColumnReference(table = "t_rbac_user", column = "id")
    private Long userId;

    @JSONField(name = "role")
    @Positive
    @NotNull(groups = {RegisterValidation.class})
    @DatabaseColumnReference(table = "t_rbac_role", column = "id")
    private Byte roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getRoleId() {
        return roleId;
    }

    public void setRoleId(Byte roleId) {
        this.roleId = roleId;
    }
}