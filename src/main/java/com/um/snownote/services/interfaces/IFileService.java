package com.um.snownote.services.interfaces;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IFileService {

    public ObjectId store(InputStream inputStream, String fileName) throws IOException;
    public void getFileByName(String fileName, OutputStream outputStream) throws IOException;
    public void getFileById(String id, OutputStream outputStream) throws IOException;
    public GridFSFile findFileByName(String fileName);

    public GridFSFile findFileById(String id);

}
