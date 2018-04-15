package cn.opencil.controller.project.team;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.TeamMember;
import cn.opencil.service.TeamMemberService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.AddTeamMemberValidation;
import cn.opencil.validation.group.database.DatabaseTeamIdValidation;
import cn.opencil.validation.group.database.DatabaseUserValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/team/member")
public class TeamMemberController {

    private final ValidationService validationService;
    private final TeamMemberService teamMemberService;

    public TeamMemberController(ValidationService validationService, TeamMemberService teamMemberService) {
        this.validationService = validationService;
        this.teamMemberService = teamMemberService;
    }

    /**
     * Add a member to a team
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addMember(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        TeamMember teamMember = validationService.validate(input.toJavaObject(TeamMember.class), AddTeamMemberValidation.class);
        if (!teamMemberService.addMemberToTeam(teamMember)) {
            throw new SimpleHttpException(400, "member already exists", HttpStatus.BAD_REQUEST);
        }
        return new RestfulResult(0, "new member added", new HashMap<>());
    }

    /**
     * Modify someone's job or position
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyMemberPosition(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        TeamMember teamMember = validationService.validate(input.toJavaObject(TeamMember.class), DatabaseUserValidation.class, DatabaseTeamIdValidation.class);
        if (!teamMemberService.modifyMemberJobs(teamMember)) {
            throw new SimpleHttpException(400, "error parameters", HttpStatus.BAD_REQUEST);
        }
        return new RestfulResult(0, "member position & job info has been changed", new HashMap<>());
    }

    /**
     * Kick out a man from a team
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@RequestBody JSONObject input) throws ValidationException {
        TeamMember teamMember = validationService.validate(input.toJavaObject(TeamMember.class), DatabaseUserValidation.class, DatabaseTeamIdValidation.class);
        teamMemberService.deleteMemberFromTeam(teamMember);
    }

    /**
     * Query team members
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult queryMember(@RequestParam("condition") String condition, @RequestParam("value") String value) throws SimpleHttpException, ValidationException {
        TeamMember member = new TeamMember();
        switch (condition.toLowerCase()) {
            case "team":
                member.setTeamId(Integer.parseInt(value));
                break;
            case "position":
                member.setPosition(Byte.parseByte(value));
                break;
            default:
                throw new SimpleHttpException(400, "invalid condition was given", HttpStatus.BAD_REQUEST);
        }
        List<TeamMember> result = teamMemberService.queryTeamMembers(validationService.validate(member));
        if (result == null || result.size() == 0) {
            throw new SimpleHttpException(404, "member was not found in given team", HttpStatus.NOT_FOUND);
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("members", result);
        return new RestfulResult(0, "", data);
    }
}
