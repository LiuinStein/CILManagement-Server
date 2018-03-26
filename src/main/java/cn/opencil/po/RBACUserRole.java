package cn.opencil.po;

import com.alibaba.fastjson.annotation.JSONField;

public class RBACUserRole {
    private Integer id;

    @JSONField(name = "id")
    private Long userId;

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