package com.um.snownote.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String name;
    @DBRef
    private StructuredData structuredData;
    @DBRef
    private User owner;
    @DBRef
    private List<User> readers;
    @DBRef
    private List<User> writers;
    private Date createDate;
    @DBRef
    private User updateBy;
    private Date updateDate;
    private boolean status;

    public Project() {
    }

    public Project(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.structuredData = new StructuredData();
        this.readers = new ArrayList<>();
        this.writers = new ArrayList<>();
        this.createDate = new Date();
        this.updateBy = null;
        this.updateDate = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StructuredData getStructuredData() {
        return structuredData;
    }

    public void setStructuredData(StructuredData structuredData) {
        this.structuredData = structuredData;
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
        readers.add(reader);
    }

    public void removeReader(User reader) {
        readers.remove(reader);
    }

    public void addWriter(User writer) {
        writers.add(writer);
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

    public void clear() {
        readers.clear();
        writers.clear();
    }

    public void addReader(int index, User reader) {
        readers.add(index, reader);
    }

    public void addWriter(int index, User writer) {
        writers.add(index, writer);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void changeStatus() {
        this.status = !this.status;
    }
}
