package cn.opencil.controller.project.team;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.TeamProject;
import cn.opencil.service.TeamProjectService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.database.DatabaseProjectIdValidation;
import cn.opencil.validation.group.database.DatabaseTeamIdValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/team/project")
public class TeamProjectController {

    private final ValidationService validationService;
    private final TeamProjectService teamProjectService;

    @Autowired
    public TeamProjectController(ValidationService validationService, TeamProjectService teamProjectService) {
        this.validationService = validationService;
        this.teamProjectService = teamProjectService;
    }

    /**
     * Assign a project to a team
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult assignProjectToTeam(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        TeamProject teamProject = validationService.validate(input.toJavaObject(TeamProject.class), DatabaseTeamIdValidation.class, DatabaseProjectIdValidation.class);
        if (!teamProjectService.assignProjectToTeam(teamProject)) {
            throw new SimpleHttpException(400, "this project is already assigned to the team", HttpStatus.BAD_REQUEST);
        }
        return new RestfulResult(0, "project has been assigned successfully", new HashMap<>());
    }

    /**
     * Take back a project from a team
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void takeBackProjectFromTeam(@RequestBody JSONObject input) throws ValidationException {
        TeamProject teamProject = validationService.validate(input.toJavaObject(TeamProject.class));
        teamProjectService.takeBackProjectFromTeam(teamProject);
    }
}
