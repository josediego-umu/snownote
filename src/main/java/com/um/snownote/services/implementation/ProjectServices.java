package com.um.snownote.services.implementation;

import com.um.snownote.model.Project;
import com.um.snownote.model.User;
import com.um.snownote.repository.interfaces.IProjectRepository;
import com.um.snownote.services.interfaces.IProjectServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

//TODO implement here
@Service
public class ProjectServices implements IProjectServices {
    private final IProjectRepository projectRepository;

    @Autowired
    public ProjectServices(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project createProject(String name, User owner) {
        Project project = new Project(name, owner);
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        project.setUpdateDate(new Date());
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project, User user) {
        project.setUpdateBy(user);
        project.setUpdateDate(new Date());
        return projectRepository.save(project);
    }

    @Override
    public Project getProjectById(String id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.orElseGet(Project::new);
    }

    @Override
    public List<Project> getProjectsByName(String name) {
        return projectRepository.findByName(name);
    }

    @Override
    public Boolean deleteProject(String id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            updateProject(project);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteProject(String id, User user) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            updateProject(project, user);
            return true;
        }
        return false;
    }
}
