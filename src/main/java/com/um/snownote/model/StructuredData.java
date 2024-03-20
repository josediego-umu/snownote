package com.um.snownote.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "structuredDatas")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StructuredData extends AuditData{
    @Id
    private String id;
    private List<Row> rows;

    public StructuredData() {
        rows = new ArrayList<>();
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void addRow(Row row) {
        rows.add(row);
    }

    public void removeRow(Row row) {
        rows.remove(row);
    }

    public Row getRow(int index) {
        return rows.get(index);
    }

    public void removeRow(int index) {
        rows.remove(index);
    }

    public void clearRows() {
        rows.clear();
    }

    public int size() {
        return rows.size();
    }

    public boolean isEmpty() {
        return rows.isEmpty();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void updateRow(int index, Row row) {
        rows.set(index, row);
    }

    public void addRow(int index, Row row) {
        rows.add(index, row);
    }

    @Override
    public String toString() {
        return "StructuredData{" +
                "id='" + id + '\'' +
                ", rows=" + rows +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StructuredData that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getRows(), that.getRows());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRows());
    }
}
