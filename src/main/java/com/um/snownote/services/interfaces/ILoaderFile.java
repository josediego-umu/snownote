package com.um.snownote.services.interfaces;

import com.um.snownote.model.StructuredData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.StringWriter;

public interface ILoaderFile {

     StructuredData load(MultipartFile file);
     StringWriter export(StructuredData structuredData);
}
