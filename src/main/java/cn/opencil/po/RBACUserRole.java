package cn.opencil.po;

import cn.opencil.validation.group.RegisterValidation;
import cn.opencil.validation.group.database.DatabaseRoleValidation;
import cn.opencil.validation.group.database.DatabaseUserValidation;
import com.alibaba.fastjson.annotation.JSONField;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class RBACUserRole {
    @Positive
    private Integer id;

    @Positive
    @NotNull(groups = {
            RegisterValidation.class
    })
    @DatabaseColumnReference(table = "t_rbac_user", column = "id", groups = {
            DatabaseUserValidation.class
    })
    @JSONField(name = "id")
    private Long userId;

    @Positive
    @NotNull(groups = {
            RegisterValidation.class
    })
    @DatabaseColumnReference(table = "t_rbac_role", column = "id", groups = {
            DatabaseRoleValidation.class
    })
    @JSONField(name = "role")
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