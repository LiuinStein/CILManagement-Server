package cn.opencil.controller.authorization;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.RBACPermissionRole;
import cn.opencil.service.RBACPermissionService;
import cn.opencil.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/auth/permission")
public class PermissionController {

    private final RBACPermissionService permissionService;

    @Autowired
    public PermissionController(RBACPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    /**
     * Query permissions
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryPermission(@RequestParam("mode") String mode, @RequestParam("condition") String condition, @RequestParam("value") String value) throws SimpleHttpException {
        boolean isAll = mode.toLowerCase().equals("all");
        List<RBACPermissionRole> result;
        switch (condition.toLowerCase()) {
            case "user":
                result = permissionService.getPermissionByUserId(Long.parseLong(value), isAll);
                break;
            case "summary":
                result = permissionService.getPermissionByRoleId(Integer.parseInt(value), isAll);
                break;
            default:
                throw new SimpleHttpException(400, "unable to parse query condition", HttpStatus.BAD_REQUEST);
        }
        if (result == null || result.size() == 0) {
            throw new SimpleHttpException(404, "no permission", HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("permissions", result);
        return new RestfulResult(0, "", data);
    }
}
