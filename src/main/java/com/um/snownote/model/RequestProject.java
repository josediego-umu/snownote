package com.um.snownote.model;

import org.springframework.web.multipart.MultipartFile;

public class RequestProject {

    private Project project;
    private MultipartFile file;

    public RequestProject() {
    }

    public RequestProject(Project project, MultipartFile file) {
        this.project = project;
        this.file = file;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
