package cn.opencil.po;

import cn.opencil.validation.group.NotNullRoleIdValidation;
import cn.opencil.validation.group.PermissionRoleIdVaildation;
import cn.opencil.validation.group.RoleOperationValidation;
import cn.opencil.validation.group.database.DatabasePermissionValidation;
import cn.opencil.validation.group.database.DatabaseRoleValidation;
import com.alibaba.fastjson.annotation.JSONField;
import com.shaoqunliu.security.SecurityComponentException;
import com.shaoqunliu.security.util.BasicHttpRequest;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

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
    @DatabaseColumnReference(table = "t_rbac_role", column = "name")
    @JSONField(alternateNames = "role_name")
    private String name;

    @Positive
    @NotNull(groups = {
            PermissionRoleIdVaildation.class
    })
    @DatabaseColumnReference(table = "t_rbac_permission", column = "id", groups = {
            DatabasePermissionValidation.class
    })
    @JSONField(name = "permission")
    private Integer permissionId;

    @Positive
    @NotNull(groups = {
            PermissionRoleIdVaildation.class,
            NotNullRoleIdValidation.class
    })
    @DatabaseColumnReference(table = "t_rbac_role", column = "id", groups = {
            DatabaseRoleValidation.class
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
