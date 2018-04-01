package cn.opencil.po;

import cn.opencil.validation.group.PermissionRoleIdVaildation;
import cn.opencil.validation.group.RoleOperationValidation;
import com.alibaba.fastjson.annotation.JSONField;
import com.shaoqunliu.security.SecurityComponentException;
import com.shaoqunliu.security.util.BasicHttpRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class RBACPermissionRole {
    /**
     * for url and method field
     */
    private BasicHttpRequest request = new BasicHttpRequest();
    /**
     * role name
     */
    @Size(max = 50)
    @NotNull(groups = {
            RoleOperationValidation.class
    })
    @JSONField(alternateNames = "role_name")
    private String name;

    @Positive
    @NotNull(groups = {
            PermissionRoleIdVaildation.class
    })
    @JSONField(name = "permission")
    private Integer permissionId;

    @Positive
    @NotNull(groups = {
            PermissionRoleIdVaildation.class
    })
    @JSONField(name = "role")
    private Integer roleId;

    public String getUrl() {
        return request.getUrl();
    }

    public void setUrl(String url) {
        request.setUrl(url);
    }

    public Integer getMethod() {
        return request.getIntMethod();
    }

    public void setMethod(Integer method) throws SecurityComponentException {
        request.setIntMethod(method);
    }

    public String getName() {
        return name == null ? "anonymous" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BasicHttpRequest getRequest() {
        return request;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
