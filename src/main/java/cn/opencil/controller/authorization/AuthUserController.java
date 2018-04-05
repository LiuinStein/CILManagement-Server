package cn.opencil.controller.authorization;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.RBACUserRole;
import cn.opencil.service.RBACUserRoleService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.RegisterValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/auth/user")
public class AuthUserController {

    private final RBACUserRoleService userRoleService;
    private final ValidationService validationService;

    @Autowired
    public AuthUserController(RBACUserRoleService userRoleService, ValidationService validationService) {
        this.userRoleService = userRoleService;
        this.validationService = validationService;
    }

    /**
     * Assign a new role to somebody
     */
    @RequestMapping(value = "/role/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult assignRoleToUser(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACUserRole userRole = validationService.validate(input.toJavaObject(RBACUserRole.class), RegisterValidation.class);
        if (!userRoleService.assignRoleToUser(userRole)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "role assign successfully", new HashMap<>());
    }

    /**
     * Take back a role from someone
     */
    @RequestMapping(value = "/role/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokeRoleFormUser(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACUserRole userRole = validationService.validate(input.toJavaObject(RBACUserRole.class), RegisterValidation.class);
        if (userRole.getUserId().equals(10001L)) {
            throw new SimpleHttpException(400, "10001 is the default admin, we can not revoke permission from him", HttpStatus.BAD_REQUEST);
        }
        userRoleService.revokeRoleFromUser(userRole);
    }
}
