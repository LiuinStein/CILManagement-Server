package cn.opencil.controller.authorization;

import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth/role")
public class AuthRoleController {

    /**
     * Grant some permissions to a role
     */
    @RequestMapping(value = "/permission/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult grantPermissionToRole(@RequestBody JSONObject input) {
        return null;
    }

    /**
     * Revoke permissions from role
     */
    @RequestMapping(value = "/permission/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokePermissionFromRole(@RequestBody JSONObject input) {

    }

    /**
     * Add a new role
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addRole(@RequestBody JSONObject input) {
        return null;
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
