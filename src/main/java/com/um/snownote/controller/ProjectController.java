package com.um.snownote.controller;

import com.um.snownote.model.Project;
import com.um.snownote.repository.interfaces.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {
    @Autowired
    private IProjectRepository projectRepository;

    public ProjectController() {
    }

    private Project createProject(String name, String description, String owner) {
        //TODO implement here
        return null;
    }

    private Project getProjectByName(String name) {
        //TODO implement here
        return null;
    }

    private Project getProjectById(String id) {
        //TODO implement here
        return null;
    }

    private Project getProjects() {
        //TODO implement here
        return null;
    }

    private Project getProjectsByUser(String username) {
        //TODO implement here
        return null;
    }

    private Project updateProject(String name, String description, String owner) {
        //TODO implement here
        return null;
    }

    private Project deleteProject(String name) {
        //TODO implement here
        return null;
    }

    private Project addReader(String name, String member) {
        //TODO implement here
        return null;
    }

    private Project addWriter(String name, String member) {
        //TODO implement here
        return null;
    }

    private Project removeReader(String name, String member) {
        //TODO implement here
        return null;
    }

    private Project removeWriter(String name, String member) {
        //TODO implement here
        return null;
    }


}
