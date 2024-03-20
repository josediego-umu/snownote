package com.um.snownote.services.interfaces;

import com.um.snownote.model.StructuredData;

import java.io.StringWriter;

public interface ILoaderFile {

     StructuredData load(String path);
     StringWriter export(StructuredData structuredData);
}
