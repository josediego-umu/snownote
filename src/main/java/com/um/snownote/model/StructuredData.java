package com.um.snownote.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "structuredDatas")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StructuredData extends AuditData {
    @Id
    private String id;
    private List<List<String>> rows;

    private Map<String, String> labels;

    public StructuredData() {
        this.rows = new ArrayList<>();
        this.labels = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void setRows(List<List<String>> rows) {
        this.rows = rows;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public void addValue(List<String> value) {
        this.rows.add(value);
    }

    public void addValue(String value, int index) {

        if (this.rows.size() > index) {
            this.rows.get(index).add(value);
        }
    }

    public void addLabel(String key, String value) {
        this.labels.put(key, value);
    }

    public void removeValue(String value, int index) {
        if (this.rows.size() > index) {
            this.rows.get(index).remove(value);
        }
    }

    public void removeLabel(String key) {
        this.labels.remove(key);
    }

    public void clearValue() {
        this.rows.clear();
    }

    public void clearLabel() {
        this.labels.clear();
    }

    public boolean containsValue(String value) {
        return this.rows.stream().anyMatch(row -> row.contains(value));
    }

    public boolean containsValue(String value, int index) {
        return this.rows.size() > index && this.rows.get(index).contains(value);
    }

    public boolean containsLabel(String key) {
        return this.labels.containsKey(key);
    }

    public boolean isEmpty() {
        return this.rows.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StructuredData that)) return false;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "StructuredData{" +
                "id='" + id + '\'' +
                ", value=" + rows +
                ", label=" + labels +
                '}';
    }
}
