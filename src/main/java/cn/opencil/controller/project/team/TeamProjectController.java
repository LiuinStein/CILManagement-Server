package cn.opencil.controller.project.team;

import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/team/project")
public class TeamProjectController {

    /**
     * Assign a project to a team
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult assignProjectToTeam(@RequestBody JSONObject input) {
        return null;
    }

    /**
     * Take back a project from a team
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void takeBackProjectFromTeam(@RequestBody JSONObject input) {

    }
}
