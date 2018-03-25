package cn.opencil.controller.user;

import cn.opencil.exception.SimpleException;
import cn.opencil.vo.RestfulResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    /**
     * Sign up a new member
     *
     * @throws SimpleException
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RestfulResult register() throws SimpleException {
        throw new SimpleException(22, "sss");
//        Map<String, Object> test = new HashMap<>();
//        return new RestfulResult(1, "hello", test);
    }

    /**
     * Delete a member
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RestfulResult deleteMember() {
        return null;
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
     * Modify user's password
     *
     * @return
     */
    @RequestMapping(value = "/password/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RestfulResult modifyPassword() {
        return null;
    }

    /**
     * For admin to initialize someone's password
     * The default password is 666666
     *
     * @return
     */
    @RequestMapping(value = "/password/", method = RequestMethod.PATCH, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RestfulResult initPassword() {
        return null;
    }


}
