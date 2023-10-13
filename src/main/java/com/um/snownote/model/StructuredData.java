package com.um.snownote.model;

import java.util.ArrayList;
import java.util.List;

public class StructuredData {

    private String idProject;
    private List<Row> rows;

    public StructuredData() {
        rows = new ArrayList<>();
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}
