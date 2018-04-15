package cn.opencil.controller.project.team;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.Team;
import cn.opencil.service.TeamService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.NotNullTeamIdValidation;
import cn.opencil.validation.group.OrganizeTeamVlidation;
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
@RequestMapping("/v1/team")
public class TeamController {

    private final ValidationService validationService;
    private final TeamService teamService;

    @Autowired
    public TeamController(ValidationService validationService, TeamService teamService) {
        this.validationService = validationService;
        this.teamService = teamService;
    }

    /**
     * Organize a team
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult organizeTeam(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        Team team = validationService.validate(input.toJavaObject(Team.class), OrganizeTeamVlidation.class);
        if (!teamService.addTeam(team)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "new team created", new HashMap<>());
    }

    /**
     * Modify team information
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyTeamInfo(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        Team team = validationService.validate(input.toJavaObject(Team.class), NotNullTeamIdValidation.class, DatabaseUserValidation.class);
        if (!teamService.modifyTeamInfo(team)) {
            throw new SimpleHttpException(404, "team not found", HttpStatus.NOT_FOUND);
        }
        return new RestfulResult(0, "team information has been changed", new HashMap<>());
    }

    /**
     * Dissolve a team
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dissolveTeam(@RequestBody JSONObject input) throws ValidationException {
        Team team = validationService.validate(input.toJavaObject(Team.class), NotNullTeamIdValidation.class);
        teamService.deleteTeam(team);
    }

    /**
     * Query team info
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryTeam(@RequestParam("mode") String mode, @RequestParam("condition") String condition, @RequestParam("value") String value) throws SimpleHttpException {
        List<Team> result;
        boolean isAll;
        switch (mode.toLowerCase()) {
            case "summary":
                isAll = false;
                break;
            case "all":
                isAll = true;
                break;
            default:
                throw new SimpleHttpException(404, "project not found", HttpStatus.NOT_FOUND);
        }
        switch (condition.toLowerCase()) {
            case "id":
                result = teamService.queryInfoByTeamId(Integer.parseInt(value), isAll);
                break;
            case "member":
                result = teamService.queryInfoByMemberId(Long.parseLong(value), isAll);
                break;
            case "project":
                result = teamService.queryInfoByProjectId(Integer.parseInt(value), isAll);
                break;
            default:
                throw new SimpleHttpException(400, "condition is not supported", HttpStatus.BAD_REQUEST);
        }
        if (result == null || result.size() == 0) {
            throw new SimpleHttpException(404, "team not found", HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("teams", result);
        return new RestfulResult(0, "", data);
    }
}
