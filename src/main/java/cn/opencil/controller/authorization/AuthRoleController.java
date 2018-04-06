package cn.opencil.controller.authorization;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.RBACPermissionRole;
import cn.opencil.po.RBACRole;
import cn.opencil.service.RBACPermissionRoleService;
import cn.opencil.service.RBACRoleService;
import cn.opencil.service.RBACUserRoleService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.NotNullRoleIdValidation;
import cn.opencil.validation.group.PermissionRoleIdVaildation;
import cn.opencil.validation.group.RoleOperationValidation;
import cn.opencil.validation.group.database.DatabasePermissionValidation;
import cn.opencil.validation.group.database.DatabaseRoleValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/auth/role")
public class AuthRoleController {

    private final RBACPermissionRoleService permissionRoleService;
    private final RBACUserRoleService userRoleService;
    private final RBACRoleService roleService;
    private final ValidationService validationService;

    @Autowired
    public AuthRoleController(RBACPermissionRoleService permissionRoleService, RBACUserRoleService userRoleService, RBACRoleService roleService, ValidationService validationService) {
        this.permissionRoleService = permissionRoleService;
        this.userRoleService = userRoleService;
        this.roleService = roleService;
        this.validationService = validationService;
    }

    /**
     * Grant some permissions to a role
     */
    @RequestMapping(value = "/permission/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult grantPermissionToRole(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACPermissionRole permissionRole = validationService.validate(input.toJavaObject(RBACPermissionRole.class),
                PermissionRoleIdVaildation.class, DatabaseRoleValidation.class, DatabasePermissionValidation.class);
        if (permissionRole.getRoleId().equals(1)) {
            throw new SimpleHttpException(400, "the default admin user can not be grant or revoke any permission", HttpStatus.BAD_REQUEST);
        }
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
        RBACPermissionRole permissionRole = validationService.validate(input.toJavaObject(RBACPermissionRole.class), PermissionRoleIdVaildation.class);
        if (permissionRole.getRoleId().equals(1)) {
            throw new SimpleHttpException(400, "the default admin user can not be deleted", HttpStatus.BAD_REQUEST);
        }
        permissionRoleService.revokePermissionFromRole(permissionRole);
    }

    /**
     * Add a new role
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addRole(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACPermissionRole permissionRole = validationService.validate(input.toJavaObject(RBACPermissionRole.class), RoleOperationValidation.class);
        if (!permissionRoleService.addRole(permissionRole)) {
            throw new SimpleHttpException(400, "role naming conflict", HttpStatus.BAD_REQUEST);
        }
        return new RestfulResult(0, "role add successfully", new HashMap<>());
    }

    /**
     * Delete a role
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACPermissionRole permissionRole = validationService.validate(input.toJavaObject(RBACPermissionRole.class), NotNullRoleIdValidation.class);
        if (permissionRole.getRoleId().equals(1)) {
            throw new SimpleHttpException(400, "the default admin user can not be deleted", HttpStatus.BAD_REQUEST);
        }
        permissionRoleService.deleteRole(permissionRole);
    }

    /**
     * Rename a role
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult renameRole(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACPermissionRole permissionRole = validationService.validate(input.toJavaObject(RBACPermissionRole.class), NotNullRoleIdValidation.class, RoleOperationValidation.class);
        if (permissionRole.getRoleId().equals(1)) {
            throw new SimpleHttpException(400, "the default admin user can not be deleted", HttpStatus.BAD_REQUEST);
        }
        if (!permissionRoleService.renameRole(permissionRole)) {
            throw new SimpleHttpException(404, "role not found", HttpStatus.NOT_FOUND);
        }
        return new RestfulResult(0, "role rename successfully", new HashMap<>());
    }

    /**
     * Query roles
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryRoles(@RequestParam("condition") String condition, @RequestParam("value") String value) throws SimpleHttpException, ValidationException {
        List<RBACRole> roles;
        try {
            switch (condition) {
                case "user_id":
                    Long userId = Long.parseLong(value);
                    roles = userRoleService.getRoleByUser(userId);
                    break;
                case "permission":
                    Integer permissionId = Integer.parseInt(value);
                    roles = permissionRoleService.getRoleByPermission(permissionId);
                    break;
                default:
                    RBACRole role = new RBACRole();
                    switch (condition) {
                        case "id":
                            role.setId(Byte.parseByte(value));
                            break;
                        case "name":
                            role.setName(value);
                            break;
                        default:
                            throw new SimpleHttpException(400, "invalid query value", HttpStatus.BAD_REQUEST);
                    }
                    roles = roleService.getRole(validationService.validate(role));
                    break;
            }
        } catch (NumberFormatException e) {
            throw new SimpleHttpException(400, "invalid query value", HttpStatus.BAD_REQUEST);
        }
        if (roles == null) {
            throw new SimpleHttpException(404, "no role matched", HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("roles", roles);
        return new RestfulResult(0, "", data);
    }

}
