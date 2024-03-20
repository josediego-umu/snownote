package com.um.snownote.services.interfaces;

import com.um.snownote.model.Project;
import com.um.snownote.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProjectServices {

    Project createProject(String name, User owner);

    Project createProject(String name, User owner, String description);

    Project updateProject(Project project);
    Project updateProject(Project project, User user);

    Project getProjectById(String id);

    List<Project> getProjectsByName(String name, Pageable pageable);
    List<Project> getProjectsByUser(User user, Pageable pageable);
    List<Project> getAllProjects();
    List<Project> getAllProjects(Pageable pageable);
    List<Project>getByReaderOrWrite(User user);
    Boolean deleteProject(String id);
    Boolean deleteProject(String id, User user);
    Project addReader(String id, User user);
    Project addWriter(String id, User user);
    Project removeReader(String name, User user);
    Project removeWriter(String name, User user);
}
