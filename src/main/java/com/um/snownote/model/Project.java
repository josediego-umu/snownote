package com.um.snownote.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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


}
