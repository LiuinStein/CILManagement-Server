package cn.opencil.controller.authorization;

import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth/user")
public class AuthUserController {

    /**
     * Assign a new role to somebody
     */
    @RequestMapping(value = "/role/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult assignRoleToUser(@RequestBody JSONObject input) {
        return null;
    }

    /**
     * Take back a role from someone
     */
    @RequestMapping(value = "/role/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revokeRoleFormUser(@RequestBody JSONObject input) {

    }


}
