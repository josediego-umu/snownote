package com.um.snownote.services.interfaces;

import com.um.snownote.model.Project;
import com.um.snownote.model.User;

import java.util.List;

//TODO implement here
public interface IProjectServices {

    Project createProject(String name, User owner);

    Project updateProject(Project project);
    Project updateProject(Project project, User user);

    Project getProjectById(String id);

    List<Project> getProjectsByName(String name);

    Boolean deleteProject(String id);
    Boolean deleteProject(String id, User user);

}
