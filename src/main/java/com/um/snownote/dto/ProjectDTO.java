package com.um.snownote.dto;

import com.um.snownote.model.AuditData;
import com.um.snownote.model.User;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "projects")
public class ProjectDTO extends AuditData {
    @Id
    private String id;
    private String name;
    private String description;
    @DBRef
    private User owner;
    @DBRef
    private List<User> readers;
    @DBRef
    private List<User> writers;

    private int length;

    private String visibility;

    public ProjectDTO() {
    }

    public ProjectDTO(String name, User owner, String description) {
        this.name = name;
        this.owner = owner;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getReaders() {
        return readers;
    }

    public void setReaders(List<User> readers) {
        this.readers = readers;
    }

    public List<User> getWriters() {
        return writers;
    }

    public void setWriters(List<User> writers) {
        this.writers = writers;
    }

    public void addReader(User reader) {
        if (this.readers == null)
            this.readers = new ArrayList<>();

        if (!this.readers.contains(reader))
            readers.add(reader);
    }

    public void removeReader(User reader) {
        if (this.readers == null)
            this.readers = new ArrayList<>();
        readers.remove(reader);
    }

    public void addWriter(User writer) {
        if (this.writers == null)
            this.writers = new ArrayList<>();

        if (!this.writers.contains(writer))
            this.writers.add(writer);
    }

    public void removeWriter(User writer) {
        writers.remove(writer);
    }

    public void clearReaders() {
        readers.clear();
    }

    public void clearWriters() {
        writers.clear();
    }

    public int readersSize() {
        return readers.size();
    }

    public int writersSize() {
        return writers.size();
    }

    public boolean readersIsEmpty() {
        return readers.isEmpty();
    }

    public boolean writersIsEmpty() {
        return writers.isEmpty();
    }

    public User getReader(int index) {
        return readers.get(index);
    }

    public User getWriter(int index) {
        return writers.get(index);
    }

    public void removeReader(int index) {
        readers.remove(index);
    }

    public void removeWriter(int index) {
        writers.remove(index);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void clear() {
        readers.clear();
        writers.clear();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addReader(int index, User reader) {
        readers.add(index, reader);
    }

    public void addWriter(int index, User writer) {
        writers.add(index, writer);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", readers=" + readers +
                ", writers=" + writers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectDTO project)) return false;
        return Objects.equals(getName(), project.getName()) && Objects.equals(getOwner(), project.getOwner()) && Objects.equals(getReaders(), project.getReaders()) && Objects.equals(getWriters(), project.getWriters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getOwner(), getReaders(), getWriters());
    }
}
