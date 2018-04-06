package cn.opencil.controller.user;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.RBACUser;
import cn.opencil.po.RBACUserRole;
import cn.opencil.po.UserInfo;
import cn.opencil.service.RBACUserService;
import cn.opencil.service.UserInfoService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.NotNullUserIdValidation;
import cn.opencil.validation.group.RegisterValidation;
import cn.opencil.validation.group.database.DatabaseClassValidation;
import cn.opencil.validation.group.database.DatabaseCollegeValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final RBACUserService userService;
    private final UserInfoService infoService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;


    @Autowired
    public UserController(RBACUserService userService, UserInfoService infoService, BCryptPasswordEncoder passwordEncoder, ValidationService validationService) {
        this.userService = userService;
        this.infoService = infoService;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
    }

    /**
     * Sign up a new member
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult register(@RequestBody JSONObject input) throws SimpleHttpException, ValidationException {
        RBACUser user = validationService.validate(input.toJavaObject(RBACUser.class), RegisterValidation.class);
        RBACUserRole role = validationService.validate(input.toJavaObject(RBACUserRole.class), RegisterValidation.class);
        UserInfo info = validationService.validate(input.toJavaObject(UserInfo.class), RegisterValidation.class, role.getRoleId() > (byte) 2 ? DatabaseClassValidation.class : DatabaseCollegeValidation.class);
        if (!userService.addMember(user, info, role)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "new member created", new HashMap<>());
    }

    /**
     * Delete a member
     * Http status 204 doesn't have any response body, so make the function return void
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@RequestBody JSONObject input) throws ValidationException {
        RBACUser user = validationService.validate(input.toJavaObject(RBACUser.class));
        userService.deleteMember(user.getId());
    }

    /**
     * Modify someone's personal information
     */
    @RequestMapping(value = "/info/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyInfo(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        UserInfo info = validationService.validate(input.toJavaObject(UserInfo.class));
        RBACUser userDetails = (RBACUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if ((info.getEnrollTime() != null || info.getExitTime() != null) && !userDetails.getAuthorities().toString().equals("[admin]")) {
            // Only administers can modify the value of enroll_time&exit_time fields. If others submit that, it would be ignored.
            info.setEnrollTime(null);
            info.setExitTime(null);
        }
        if (!info.getId().equals(userDetails.getId()) && !userDetails.getAuthorities().toString().equals("[admin]")) {
            throw new SimpleHttpException(403, "need administer privilege", HttpStatus.FORBIDDEN);
        }
        if (!infoService.modifyUserInfo(info)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "information has been changed!", new HashMap<>());
    }

    /**
     * Query someone's information by given condition
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryMemberInfo(@RequestParam("mode") String mode, @RequestParam("condition") String condition, @RequestParam("value") String value) throws SimpleHttpException, ValidationException {
        UserInfo info = new UserInfo();
        switch (condition.toLowerCase()) {
            case "id":
                info.setId(Long.parseLong(value));
                break;
            case "name":
                info.setName(value);
                break;
            case "department":
                info.setDepartment(Integer.parseInt(value));
                break;
            default:
                throw new SimpleHttpException(2, "condition is not supported", HttpStatus.BAD_REQUEST);
        }
        info = validationService.validate(info);
        List<UserInfo> result;
        switch (mode.toLowerCase()) {
            case "summary":
                result = infoService.querySummaryUserInfo(info);
                break;
            case "all":
                result = infoService.queryAllUserInfo(info);
                break;
            default:
                result = null;
        }
        if (result == null || result.size() == 0) {
            throw new SimpleHttpException(404, "user not found", HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("users", result);
        return new RestfulResult(0, "", data);
    }

    /**
     * Modify your own password
     */
    @RequestMapping(value = "/password/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyPassword(@RequestBody JSONObject input, HttpServletRequest request) throws SimpleHttpException, ValidationException {
        String oldPassword = input.getString("old_password");
        RBACUser userDetails = (RBACUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (oldPassword == null || !passwordEncoder.matches(oldPassword, userDetails.getPassword())) {
            throw new SimpleHttpException(403, "Old password error", HttpStatus.FORBIDDEN);
        }
        userDetails.setPassword(input.getString("new_password"));
        if (!userService.changeUserPassword(validationService.validate(userDetails))) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // must re-login after password changing, otherwise, replay attacks maybe occurred
        new SecurityContextLogoutHandler().logout(request, null, SecurityContextHolder.getContext().getAuthentication());
        return new RestfulResult(0, "Password has been changed!", new HashMap<>());
    }

    /**
     * For admin to initialize someone's password
     * The default password for everyone is 666666
     */
    @RequestMapping(value = "/password/", method = RequestMethod.PATCH, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult initPassword(@RequestBody JSONObject input) throws SimpleHttpException, ValidationException {
        RBACUser user = input.toJavaObject(RBACUser.class);
        user.setPassword("666666");
        if (!userService.changeUserPassword(validationService.validate(user))) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "Password has been initialized!", new HashMap<>());
    }

    /**
     * Enable or disable an account (set the enabled flag to true or false)
     * The disabled user can not log in and do any jobs
     */
    @RequestMapping(value = "/", method = RequestMethod.PATCH, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult enableOrDisableUser(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        RBACUser user = validationService.validate(input.toJavaObject(RBACUser.class), NotNullUserIdValidation.class);
        if (user.getId().equals(10001L)) {
            throw new SimpleHttpException(400, "the default admin user can not be disabled", HttpStatus.BAD_REQUEST);
        }
        if (!userService.enableOrDisableUser(user)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "Account has been " + (user.isEnabled() ? "enabled" : "disabled") + "!", new HashMap<>());
    }

}
