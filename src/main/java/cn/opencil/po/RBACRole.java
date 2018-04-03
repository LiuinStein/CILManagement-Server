package cn.opencil.po;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class RBACRole {
    @Positive
    private Byte id;

    @Size(max = 20)
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