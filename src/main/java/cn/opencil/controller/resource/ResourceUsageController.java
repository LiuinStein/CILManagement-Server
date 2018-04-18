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
import java.util.List;

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
     * Rent or give back some resources
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult rentResource(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        ResourceUsage usage = validationService.validate(input.toJavaObject(ResourceUsage.class), AddResourceUsageValidation.class);
        if (!usageService.rentResource(usage)) {
            throw new SimpleHttpException(400, usage.getAmount() > 0 ? "the given back resource amount surpass the amount of this resource" : "not have or not enough resource can be rent", HttpStatus.BAD_REQUEST);
        }
        return new RestfulResult(0, usage.getAmount() > 0 ? "resource give back successfully" : "resource rent successfully", new HashMap<>());
    }

    /**
     * Query resource usage info
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryResourceUsage(@RequestParam("condition") String condition, @RequestParam("value") String value) throws SimpleHttpException {
        ResourceUsage usage = new ResourceUsage();
        switch (condition.toLowerCase()) {
            case "id":
                usage.setId(Integer.parseInt(value));
                break;
            case "resource":
                usage.setResourceId(Integer.parseInt(value));
                break;
            case "user":
                usage.setUserId(Long.parseLong(value));
                break;
            default:
                throw new SimpleHttpException(400, "invalid condition was given", HttpStatus.BAD_REQUEST);
        }
        List<ResourceUsage> result = usageService.queryResourceUsage(usage);
        if (result == null || result.size() == 0) {
            throw new SimpleHttpException(404, "resource usage not found", HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("usages", result);
        return new RestfulResult(0, "", data);
    }
}
