package cn.opencil.controller.user;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.RBACUser;
import cn.opencil.po.RBACUserRole;
import cn.opencil.po.UserInfo;
import cn.opencil.service.RBACUserService;
import cn.opencil.validation.group.RegisterValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.ValidationException;
import com.shaoqunliu.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final RBACUserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(RBACUserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Sign up a new member
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult register(@RequestBody JSONObject input) throws SimpleHttpException, ValidationException {
        RBACUser user = ValidationUtils.validate(input.toJavaObject(RBACUser.class), RegisterValidation.class);
        UserInfo info = ValidationUtils.validate(input.toJavaObject(UserInfo.class), RegisterValidation.class);
        RBACUserRole role = ValidationUtils.validate(input.toJavaObject(RBACUserRole.class), RegisterValidation.class);
        if (!userService.addMember(user, info, role)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "new member created", new HashMap<>());
    }

    /**
     * Delete a member
     *
     * Http status 204 doesn't have any response body, so make the function return void
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@RequestBody JSONObject input) throws ValidationException {
        RBACUser user = ValidationUtils.validate(input.toJavaObject(RBACUser.class));
        userService.deleteMember(user.getId());
    }

    /**
     * Modify someone's personal information
     *
     * @return
     */
    @RequestMapping(value = "/info/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RestfulResult modifyInfo() {
        return null;
    }

    /**
     * Query someone's information by given condition
     *
     * @return
     */
    @RequestMapping(value = "/info/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RestfulResult queryMemberInfo() {
        return null;
    }

    /**
     * Modify your own password
     */
    @RequestMapping(value = "/password/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyPassword(@RequestBody JSONObject input) throws SimpleHttpException {
        Long username;
        try {
            username = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (NumberFormatException e) {
            throw new SimpleHttpException(401, e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        String oldPassword = input.getString("old_password");
        String newPassword = input.getString("new_password");
        if (oldPassword == null || newPassword == null || newPassword.length() < 6) {
            throw new SimpleHttpException(400, "invalid input data", HttpStatus.BAD_REQUEST);
        }

        RBACUser userDetails = (RBACUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
            throw new SimpleHttpException(403, "Old password error", HttpStatus.FORBIDDEN);
        }
        if (!userService.changeUserPassword(username, newPassword)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "Password has been changed!", new HashMap<>());
    }

    /**
     * For admin to initialize someone's password
     * The default password for everyone is 666666
     */
    @RequestMapping(value = "/password/", method = RequestMethod.PATCH, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult initPassword(@RequestBody JSONObject input) throws SimpleHttpException {
        Long username = input.getLong("the-man-who-forgot-password");
        if (username == null) {
            throw new SimpleHttpException(400, "invalid input data", HttpStatus.BAD_REQUEST);
        }
        if (!userService.changeUserPassword(username, "666666")) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "Password has been initialized!", new HashMap<>());
    }


}
