package com.um.snownote.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

public class Label {

    private String conceptId;
    private String description;


    public Label() {

    }

    public Label(String conceptId, String description) {
        this.conceptId = conceptId;
        this.description = description;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Label{" +
                "conceptId='" + conceptId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Label label)) return false;
        return Objects.equals(getConceptId(), label.getConceptId()) && Objects.equals(getDescription(), label.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConceptId(), getDescription());
    }
}
