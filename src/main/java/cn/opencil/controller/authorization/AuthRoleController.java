package cn.opencil.controller.authorization;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.RBACPermissionRole;
import cn.opencil.security.MySecurityMetadataSource;
import cn.opencil.service.RBACPermissionRoleService;
import cn.opencil.validation.group.PermissionRoleIdVaildation;
import cn.opencil.validation.group.RoleOperationValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.ValidationException;
import com.shaoqunliu.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/auth/role")
public class AuthRoleController {

    private final RBACPermissionRoleService permissionRoleService;

    @Autowired
    public AuthRoleController(RBACPermissionRoleService permissionRoleService) {
        this.permissionRoleService = permissionRoleService;
    }

    /**
     * Grant some permissions to a role
     */
    @RequestMapping(value = "/permission/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult grantPermissionToRole(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACPermissionRole permissionRole = ValidationUtils.validate(input.toJavaObject(RBACPermissionRole.class), PermissionRoleIdVaildation.class);
        if (!permissionRoleService.grantPermissionToRole(permissionRole)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "granted success", new HashMap<>());
    }

    /**
     * Revoke permissions from role
     */
    @RequestMapping(value = "/permission/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokePermissionFromRole(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACPermissionRole permissionRole = ValidationUtils.validate(input.toJavaObject(RBACPermissionRole.class), PermissionRoleIdVaildation.class);
        if (!permissionRoleService.revokePermissionFromRole(permissionRole)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Add a new role
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addRole(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACPermissionRole permissionRole = ValidationUtils.validate(input.toJavaObject(RBACPermissionRole.class), RoleOperationValidation.class);
        if (!permissionRoleService.addRole(permissionRole)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "role add successfully", new HashMap<>());
    }


    /**
     * Delete a role
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@RequestBody JSONObject input) {

    }

    /**
     * Rename a role
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult renameRole(@RequestBody JSONObject input) {
        return null;
    }

    /**
     * Query roles
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryRoles() {
        return null;
    }

}
