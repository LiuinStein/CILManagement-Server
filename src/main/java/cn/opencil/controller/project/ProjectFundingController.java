package cn.opencil.controller.project;

import cn.opencil.service.ValidationService;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/project/funding")
public class ProjectFundingController {

    private final ValidationService validationService;

    @Autowired
    public ProjectFundingController(ValidationService validationService) {
        this.validationService = validationService;
    }

    /**
     * Income/Outcome a sum of money
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult moneyFlowing(@RequestBody JSONObject input) throws ValidationException {
        return null;
    }

    /**
     * Query expenditures
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryExpenditure() {
        return null;
    }
}
