package cn.opencil.controller.user;

import cn.opencil.vo.RestfulResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public RestfulResult register() {
        Map<String, Object> test = new HashMap<>();
        return new RestfulResult(1, "hello", test);
    }

}
