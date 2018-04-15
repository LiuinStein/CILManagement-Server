package cn.opencil.controller.project;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.ProjectFunding;
import cn.opencil.service.ProjectFundingService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.AddFundingExpenditureValidation;
import cn.opencil.validation.group.database.DatabaseProjectIdValidation;
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
@RequestMapping("/v1/project/funding")
public class ProjectFundingController {

    private final ValidationService validationService;
    private final ProjectFundingService fundingService;

    @Autowired
    public ProjectFundingController(ValidationService validationService, ProjectFundingService fundingService) {
        this.validationService = validationService;
        this.fundingService = fundingService;
    }

    /**
     * Income/Outcome a sum of money
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult moneyFlowing(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        ProjectFunding funding = validationService.validate(input.toJavaObject(ProjectFunding.class), AddFundingExpenditureValidation.class, DatabaseProjectIdValidation.class);
        if (!fundingService.addFundingExpenditure(funding)) {
            throw new SimpleHttpException(400, "there is not enough money you can spend on your project!", HttpStatus.BAD_REQUEST);
        }
        return new RestfulResult(0, "expenditures has been recorded", new HashMap<>());
    }

    /**
     * Query expenditures
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryExpenditure(@RequestParam("condition") String condition, @RequestParam("value") String value) throws SimpleHttpException, ValidationException {
        ProjectFunding funding = new ProjectFunding();
        switch (condition.toLowerCase()) {
            case "project":
                funding.setProjectId(Integer.parseInt(value));
                break;
            default:
                throw new SimpleHttpException(400, "invalid query condition", HttpStatus.BAD_REQUEST);
        }
        List<ProjectFunding> result = fundingService.queryExpenditures(validationService.validate(funding));
        if (result == null || result.size() == 0) {
            throw new SimpleHttpException(404, "no expenditures was found",HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("users", result);
        return new RestfulResult(0, "", data);
    }
}
