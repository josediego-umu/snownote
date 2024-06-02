package com.um.snownote.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "ontologies")
public class Ontology extends AuditData{

    @Id
    private String id;
    private String name;
    private String data;

    private String iri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIri() {
        return iri;
    }

    public void setIri(String iri) {
        this.iri = iri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ontology ontology)) return false;

        if (!getId().equals(ontology.getId())) return false;
        return Objects.equals(name, ontology.name);
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
