package com.um.snownote.services.interfaces;

import com.um.snownote.model.StructuredData;


public interface IStructuredDataServices {

    StructuredData createStructuredData();

    StructuredData getStructuredData(String id);

    StructuredData updateStructuredData(StructuredData structuredData);

    Boolean deleteStructuredData(String id);


}
