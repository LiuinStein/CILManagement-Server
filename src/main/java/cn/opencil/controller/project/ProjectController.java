package cn.opencil.controller.project;

import cn.opencil.exception.SimpleHttpException;
import cn.opencil.po.Project;
import cn.opencil.service.ProjectService;
import cn.opencil.service.ValidationService;
import cn.opencil.validation.group.AddProjectValidation;
import cn.opencil.validation.group.NotNullProjectIdValidation;
import cn.opencil.validation.group.database.DatabaseSubjectValidation;
import cn.opencil.validation.group.database.DatabaseUserValidation;
import cn.opencil.vo.RestfulResult;
import com.alibaba.fastjson.JSONObject;
import com.shaoqunliu.validation.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/v1/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ValidationService validationService;

    @Autowired
    public ProjectController(ProjectService projectService, ValidationService validationService) {
        this.projectService = projectService;
        this.validationService = validationService;
    }

    /**
     * Add a project
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addProject(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        Project project = validationService.validate(input.toJavaObject(Project.class), AddProjectValidation.class, DatabaseUserValidation.class, DatabaseSubjectValidation.class);
        if (!projectService.addProject(project)) {
            throw new SimpleHttpException(500, "database access error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new RestfulResult(0, "new project created", new HashMap<>());
    }

    /**
     * Modify project information
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyProjectInfo(@RequestBody JSONObject input) throws ValidationException, SimpleHttpException {
        Project project = validationService.validate(input.toJavaObject(Project.class), NotNullProjectIdValidation.class, DatabaseUserValidation.class, DatabaseSubjectValidation.class);
        if (!projectService.modifyProject(project)) {
            throw new SimpleHttpException(404, "project was not found", HttpStatus.NOT_FOUND);
        }
        return new RestfulResult(0, "project information has been changed", new HashMap<>());
    }

    /**
     * Delete a project
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@RequestBody JSONObject input) throws ValidationException {
        Project project = validationService.validate(input.toJavaObject(Project.class), NotNullProjectIdValidation.class);
        projectService.deleteProject(project);
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
