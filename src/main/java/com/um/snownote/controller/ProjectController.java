package com.um.snownote.controller;

import com.um.snownote.dto.ProjectDTO;
import com.um.snownote.filters.*;
import com.um.snownote.jwtUtils.JwtTokenRequired;
import com.um.snownote.jwtUtils.JwtUtil;
import com.um.snownote.model.Project;
import com.um.snownote.model.StructuredData;
import com.um.snownote.model.User;
import com.um.snownote.services.interfaces.ILoaderFile;
import com.um.snownote.services.interfaces.IProjectServices;
import com.um.snownote.services.interfaces.IUserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.List;
import java.util.Map;

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


    @PostMapping("/")
    @JwtTokenRequired
    public Project createProject(@RequestHeader("Authorization") String token, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("visibility") String visibility, @RequestParam("file") MultipartFile file) {

        if (name == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is null");

        User userFromToken = JwtUtil.getUserFromToken(token);

        User userOwner = userService.getUser(userFromToken.getUsername());

        StructuredData structuredData = this.loaderFileCsv.load(file);

        if (userOwner == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");


        return projectServices.createProject(name, userOwner, description, visibility, structuredData);
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable String id) {
        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is null or empty");

        return projectServices.getProjectById(id);

    }


    @GetMapping
    public List<Project> getProjects(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer sizePage) {

        Pageable pageable = Pageable.ofSize(sizePage).withPage(page);
        return projectServices.getAllProjects(pageable);

    }

    @GetMapping("/filter")
    public PageImpl<ProjectDTO> getFilterProjects(@RequestParam Map<String, String> params, @RequestHeader HttpHeaders headers) {

        CompoundFilter<ProjectDTO> filter = new CompoundFilter<>();

        if (params.containsKey("pageSize") && params.containsKey("page")) {

            int page = Integer.parseInt(params.get("page"));
            int pageSize = Integer.parseInt(params.get("pageSize"));

            params.remove("pageSize");
            params.remove("page");

            filter.setPagination(new Pagination(page, pageSize));
        }

        if (params.containsKey("orderField") && params.containsKey("orderDirection")) {

            String orderField = params.get("orderField");
            String orderDirection = params.get("orderDirection");

            if (!orderField.isEmpty() && !orderDirection.isEmpty()) {

                OrderBy<ProjectDTO> orderBy = new OrderBy<>(orderField, Sort.Direction.valueOf(orderDirection.toUpperCase()));

                filter.addSortCriteria(orderBy);
            }

            params.remove("orderField");
            params.remove("orderDirection");
        }

        if (params.containsKey("visibility")) {

            User user = null;

            try {
                user = JwtUtil.getUserFromToken(headers.getFirst("Authorization"));
            } catch (Exception ignored) {

            }


            filter.addCriteria(new VisibilityFilter(params.get("visibility"), user));
            params.remove("visibility");
        }

        params.forEach((k, v) -> {
            filter.addCriteria(new GenericFilter<>(k, v));
        });

        return projectServices.filter(filter);

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

    @PutMapping("/")
    @JwtTokenRequired
    public Project updateProject(@RequestHeader("Authorization") String token, @RequestBody Project project) {
        if (project == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project is null");

        User userFromToken = JwtUtil.getUserFromToken(token);

        if (userFromToken == null)
            throw new JwtException("Token is invalid");

        User user = userService.getUser(userFromToken.getUsername());

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exist");

        return projectServices.updateProject(project, user);
    }

    @DeleteMapping("/{id}")
    @JwtTokenRequired
    public Boolean deleteProject(@RequestHeader("Authorization") String token, @PathVariable String id) {
        if (id == null || id.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Project id is null or empty");

        User userFromToken = JwtUtil.getUserFromToken(token);

        if (userFromToken == null)
            throw new JwtException("Token is invalid");

        User user = userService.getUser(userFromToken.getUsername());

        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user not exist");

        return projectServices.deleteProject(id, user);
    }

    @PostMapping("/addReader/{id}")
    @JwtTokenRequired
    public Project addReader(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestParam String username) {

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
    @JwtTokenRequired
    public Project addWriter(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestParam String username) {

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
    @JwtTokenRequired
    public Project removeReader(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestParam String username) {

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
    @JwtTokenRequired
    public Project removeWriter(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestParam String username) {

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
