package cn.opencil.po;

import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class RBACRole {
    @Positive
    @DatabaseColumnReference(table = "t_rbac_role", column = "id")
    private Byte id;

    @Size(max = 20)
    @DatabaseColumnReference(table = "t_rbac_role", column = "name")
    private String name;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}