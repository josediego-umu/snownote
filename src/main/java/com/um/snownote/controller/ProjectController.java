package com.um.snownote.controller;

import com.um.snownote.model.Project;
import com.um.snownote.model.User;
import com.um.snownote.services.interfaces.ILoaderFile;
import com.um.snownote.services.interfaces.IProjectServices;
import com.um.snownote.services.interfaces.IUserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.StringWriter;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final IProjectServices projectServices;
    private final ILoaderFile loaderFileCsv;
    private final ILoaderFile loaderFileJson;
    private final IUserService userService;

    @Autowired
    public ProjectController(IProjectServices projectServices, @Qualifier("LoaderFileCsv") ILoaderFile loaderFileCsv, @Qualifier("LoaderFileJson") ILoaderFile loaderFileJson, IUserService userService) {
        this.projectServices = projectServices;
        this.loaderFileCsv = loaderFileCsv;
        this.loaderFileJson = loaderFileJson;
        this.userService = userService;
    }


    @PostMapping
    public Project createProject(String name, String description, String owner) {
        if (name == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is null");

        if (owner == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner is null");

        User userOwner = userService.getUser(owner);

        if (userOwner == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");

        return projectServices.createProject(name, userOwner, description);
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable String id) {
        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is null or empty");

        return projectServices.getProjectById(id);

    }

    @GetMapping
    public List<Project> getProjects(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer sizePage) {


        Pageable pageable = Pageable.ofSize(sizePage).withPage(page);

        return projectServices.getAllProjects(pageable);

    }

    @GetMapping("/owner/{username}")
    public List<Project> getProjectsOwner(@PathVariable String username, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "1000") Integer sizePage) {

        User user = userService.getUser(username);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exist");

        Pageable pageable = Pageable.ofSize(sizePage).withPage(page);

        return projectServices.getProjectsByUser(user, pageable);

    }

    @GetMapping("/readerOrWriter/{username}")
    public List<Project> getProjectsByReadersOrWriters(@PathVariable String username) {

        User user = userService.getUser(username);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exist");

        return projectServices.getByReaderOrWrite(user);

    }

    @GetMapping(value = "/csv/{id}", produces = "text/csv")
    public String getExportCsv(@PathVariable String id) {

        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is null or empty");

        Project pj = projectServices.getProjectById(id);

        if (pj == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with id: " + id + "not found");

        if (pj.getStructuredData() == null || pj.getStructuredData().isEmpty())
            throw new ResponseStatusException(HttpStatus.OK, "EMPTY PROJECT");

        StringWriter export = loaderFileCsv.export(pj.getStructuredData());

        return export.toString();
    }

    @GetMapping(value = "/json/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getExportJson(@PathVariable String id) {

        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is null or empty");

        Project pj = projectServices.getProjectById(id);

        if (pj == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with id: " + id + "not found");

        StringWriter export = loaderFileJson.export(pj.getStructuredData());

        return export.toString();
    }

    @PutMapping
    public Project updateProject(Project project, String userName) {
        if (project == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project is null");

        if (userName == null || userName.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is null or empty");

        User user = userService.getUser(userName);

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exist");

        return projectServices.updateProject(project, user);
    }

    @DeleteMapping("/{id}")
    public Boolean deleteProject(@PathVariable String id, @RequestBody String username) {
        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project id is null or empty");

        if (username == null || username.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is null or empty");

        User user = userService.getUser(username);

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not exist");

        return projectServices.deleteProject(id, user);
    }

    @PostMapping("/addReader/{id}")
    public Project addReader(@PathVariable String id, @RequestParam String username) {

        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is null or empty");

        if (username == null || username.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is null or empty");

        User user = userService.getUser(username);

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exist");

        return projectServices.addReader(id, user);

    }

    @PostMapping("/addWriter/{id}")
    public Project addWriter(@PathVariable String id, @RequestParam String username) {

        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is null or empty");

        if (username == null || username.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is null or empty");

        User user = userService.getUser(username);

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exist");

        return projectServices.addWriter(id, user);

    }

    @DeleteMapping("/removeReader/{id}")
    public Project removeReader(@PathVariable String id, @RequestParam String username) {

        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is null or empty");

        if (username == null || username.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is null or empty");

        User user = userService.getUser(username);

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exist");


        return projectServices.removeReader(id, user);

    }

    @DeleteMapping("/removeWriter/{id}")
    public Project removeWriter(@PathVariable String id, @RequestParam String username) {

        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id is null or empty");

        if (username == null || username.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is null or empty");

        User user = userService.getUser(username);

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exist");

        return projectServices.removeWriter(id, user);

    }


}
