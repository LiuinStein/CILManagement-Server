package cn.opencil.controller.resource;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.ResourceUsage;
import cn.opencil.service.ResourceUsageService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.AddResourceUsageValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/resource/usage")
public class ResourceUsageController {

    private final ValidationService validationService;
    private final ResourceUsageService usageService;

    @Autowired
    public ResourceUsageController(ValidationService validationService, ResourceUsageService usageService) {
        this.validationService = validationService;
        this.usageService = usageService;
    }

    /**
     * Rent something to somebody
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult rentResource(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        ResourceUsage usage = validationService.validate(input.toJavaObject(ResourceUsage.class), AddResourceUsageValidation.class);
        if (!usageService.rentResource(usage)) {
            throw new SimpleHttpException(404, "not have or not enough resource can be rent", HttpStatus.NOT_FOUND);
        }
        return new RestfulResult(0, "resource rent successfully", new HashMap<>());
    }

    /**
     * Give back some resources
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void giveBackResource(@RequestBody JSONObject input) throws ValidationException {

    }

    /**
     * Modify resources usage info
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyResourceUsageInfo(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        return null;
    }

    /**
     * Query resource usage info
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryResourceUsage(@RequestParam("condition") String condition, @RequestParam("value") String value) throws SimpleHttpException {
        return null;
    }
}
