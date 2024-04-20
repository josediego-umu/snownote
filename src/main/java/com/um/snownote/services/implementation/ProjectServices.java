package com.um.snownote.services.implementation;

import com.um.snownote.dto.ProjectDTO;
import com.um.snownote.filters.CompoundFilter;
import com.um.snownote.model.Project;
import com.um.snownote.model.StructuredData;
import com.um.snownote.model.User;
import com.um.snownote.repository.interfaces.IProjectRepository;
import com.um.snownote.services.interfaces.IProjectServices;
import com.um.snownote.services.interfaces.IStructuredDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectServices implements IProjectServices {
    private final IProjectRepository projectRepository;
    private final IStructuredDataServices structuredDataServices;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ProjectServices(IProjectRepository projectRepository, IStructuredDataServices structuredDataServices, MongoTemplate mongoTemplate) {
        this.projectRepository = projectRepository;
        this.structuredDataServices = structuredDataServices;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public Project createProject(String name, User owner) {

        return createProject(name, owner, null, null, null);
    }

    @Override
    public Project createProject(String name, User owner, String description,String visibility ,StructuredData structuredData) {

        Project project = new Project(name, owner, description);

        visibility = (visibility == null) ? "private" : visibility;
        structuredData = (structuredData == null) ? structuredDataServices.createStructuredData() : structuredDataServices.updateStructuredData(structuredData);

        project.setStructuredData(structuredData);
        project.setVisibility(visibility);

        return projectRepository.insert(project);
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
        this.structuredDataServices.updateStructuredData(project.getStructuredData());
        return projectRepository.save(project);
    }

    @Override
    public Project getProjectById(String id) {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isPresent())
            return project.get();

        return null;
    }

    @Override
    public List<Project> getProjectsByName(String name, Pageable pageable) {


        return projectRepository.findByName(name, pageable);
    }

    @Override
    public List<Project> getProjectsByUser(User user, Pageable pageable) {

        return projectRepository.findAllByOwner(user, pageable);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> getAllProjects(Pageable pageable) {

        Page<Project> projectPage = projectRepository.findAll(pageable);
        return projectPage.getContent();
    }

    @Override
    public List<Project> getByReaderOrWrite(User user) {
        return projectRepository.findProjectsByReadersOrWriters(user, user);
    }

    ;

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
            if (project.getOwner().equals(user)) {
                updateProject(project, user);
                return true;
            }
        }
        return false;
    }

    @Override
    public Project addReader(String id, User user) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.addReader(user);
            updateProject(project);
            return project;
        }
        return null;
    }

    @Override
    public Project addWriter(String id, User user) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.addWriter(user);
            updateProject(project);
            return project;
        }
        return null;
    }

    @Override
    public Project removeReader(String name, User user) {
        Optional<Project> optionalProject = projectRepository.findById(name);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.removeReader(user);
            updateProject(project);
            return project;
        }
        return null;
    }

    @Override
    public Project removeWriter(String name, User user) {
        Optional<Project> optionalProject = projectRepository.findById(name);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.removeWriter(user);
            updateProject(project);
            return project;
        }
        return null;
    }

    public PageImpl<ProjectDTO> filter(CompoundFilter<ProjectDTO> filter){

        Query query = new Query();
        Query countQuery = new Query();

        if (filter.hasCriteria()){
            countQuery.addCriteria(filter.toCriteria());
            query.addCriteria(filter.toCriteria());
        }


        long totalCount = mongoTemplate.count(countQuery, ProjectDTO.class);

        if (filter.hasSort())
            query.with(filter.toSort());
        if (filter.hasPagination())
            query.with(filter.toPageable());

        List<ProjectDTO> result = mongoTemplate.find(query, ProjectDTO.class);

        return new PageImpl<ProjectDTO>(result, filter.toPageable(), totalCount);

    }
}
