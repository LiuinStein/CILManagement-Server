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
    public RestfulResult queryExpenditure() {
        return null;
    }
}
