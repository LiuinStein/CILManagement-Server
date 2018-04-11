package cn.opencil.controller.project.team;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.Team;
import cn.opencil.service.TeamService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.OrganizeTeamVlidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
    public RestfulResult modifyTeamInfo(@RequestBody JSONObject input) {
        return null;
    }

    /**
     * Dissolve a team
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dissolveTeam(@RequestBody JSONObject input) {

    }

    /**
     * Query projects
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryProject() {
        return null;
    }
}
