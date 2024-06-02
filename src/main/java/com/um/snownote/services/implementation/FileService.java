package com.um.snownote.services.implementation;

import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.um.snownote.services.interfaces.IFileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.mongodb.client.model.Filters.eq;
@Service
public class FileService implements IFileService {

    private final MongoDatabaseFactory mongoDatabaseFactory;
    private final String BUCKET = "files";

    @Autowired
    public FileService(MongoDatabaseFactory mongoDatabaseFactory) {
        this.mongoDatabaseFactory = mongoDatabaseFactory;
    }


    public ObjectId store(InputStream inputStream, String fileName) throws IOException {
        var gridFSBucket = GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase(),BUCKET);
        try (GridFSUploadStream uploadStream = gridFSBucket.openUploadStream(fileName)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                uploadStream.write(buffer, 0, length);
            }
            return uploadStream.getObjectId();
        }
    }

    public void getFileByName(String fileName, OutputStream outputStream) throws IOException {
        var gridFSBucket = GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase(),BUCKET);
        gridFSBucket.downloadToStream(fileName, outputStream);
    }

    public void getFileById(String id, OutputStream outputStream) throws IOException {
        var gridFSBucket = GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase(),BUCKET);
        gridFSBucket.downloadToStream(new ObjectId(id), outputStream);
    }

    public GridFSFile findFileByName(String fileName) {
        var gridFSBucket = GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase(),BUCKET);
        return gridFSBucket.find(eq("filename", fileName)).first();
    }

    public GridFSFile findFileById(String id) {
        var gridFSBucket = GridFSBuckets.create(mongoDatabaseFactory.getMongoDatabase(),BUCKET);
        return gridFSBucket.find(eq("_id",new ObjectId(id))).first();
    }


}
