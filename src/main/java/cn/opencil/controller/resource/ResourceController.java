package cn.opencil.controller.resource;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.Resource;
import cn.opencil.service.ResourceService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.AddResourceTypeValidation;
import cn.opencil.validation.group.NotNullResourceIdValidation;
import cn.opencil.validation.group.database.DatabaseTypeIdValidation;
import cn.opencil.validation.group.database.DatabaseUserValidation;
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
@RequestMapping("/v1/resource")
public class ResourceController {

    private final ResourceService resourceService;
    private final ValidationService validationService;

    @Autowired
    public ResourceController(ResourceService resourceService, ValidationService validationService) {
        this.resourceService = resourceService;
        this.validationService = validationService;
    }

    /**
     * Add a resource
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addResource(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        Resource resource = validationService.validate(input.toJavaObject(Resource.class), AddResourceTypeValidation.class);
        if (!resourceService.addResource(resource)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "resource add success", new HashMap<>());
    }

    /**
     * Delete a resource
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(@RequestBody JSONObject input) throws ValidationException {
        Resource resource = validationService.validate(input.toJavaObject(Resource.class), NotNullResourceIdValidation.class);
        resourceService.deleteResource(resource);
    }

    /**
     * Modify resource info
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyResourceProperties(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        Resource resource = validationService.validate(input.toJavaObject(Resource.class), NotNullResourceIdValidation.class, DatabaseUserValidation.class, DatabaseTypeIdValidation.class);
        if (!resourceService.modifyResourceInfo(resource)) {
            throw new SimpleHttpException(404, "resource was not found", HttpStatus.NOT_FOUND);
        }
        return new RestfulResult(0, "resource info modified", new HashMap<>());
    }

    /**
     * Query resource info
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryResourceInfo(@RequestParam("condition") String condition, @RequestParam("value") String value) throws SimpleHttpException {
        Resource resource = new Resource();
        switch (condition.toLowerCase()) {
            case "resource":
                resource.setId(Integer.parseInt(value));
                break;
            case "type":
                resource.setTypeId(Integer.parseInt(value));
                break;
            case "purchaser":
                resource.setPurchaserId(Long.parseLong(value));
                break;
            default:
                throw new SimpleHttpException(400, "invalid condition", HttpStatus.BAD_REQUEST);
        }
        List<Resource> result = resourceService.queryResourceInfo(resource);
        if (result == null || result.size() == 0) {
            throw new SimpleHttpException(404, "no resource matchs your given condition & value", HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("resources", result);
        return new RestfulResult(0, "", data);
    }
}
