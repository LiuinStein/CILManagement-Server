package cn.opencil.controller.resource;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.ResourceType;
import cn.opencil.service.ResourceTypeService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.AddResourceTypeValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/resource/type")
public class ResourceTypeController {

    private final ValidationService validationService;
    private final ResourceTypeService resourceTypeService;

    @Autowired
    public ResourceTypeController(ValidationService validationService, ResourceTypeService resourceTypeService) {
        this.validationService = validationService;
        this.resourceTypeService = resourceTypeService;
    }

    /**
     * Add a type of resource
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addResourceType(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        ResourceType resourceType = validationService.validate(input.toJavaObject(ResourceType.class), AddResourceTypeValidation.class);
        if (!resourceTypeService.addResourceType(resourceType)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "resource add success", new HashMap<>());
    }

    /**
     * Delete a type of resource
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTypeOfResource(@RequestBody JSONObject input) {

    }

    /**
     * Modify resource type properties
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyResourceType(@RequestBody JSONObject input) {
        return null;
    }

    /**
     * Query resource types
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryResourceType() {
        return null;
    }
}
