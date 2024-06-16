package com.um.snownote.Initializer;

import com.um.snownote.constants.Constant;
import com.um.snownote.model.Ontology;
import com.um.snownote.repository.interfaces.IOntologyRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OntologyInitializer{


    private IOntologyRepository ontologyRepository;

    @Autowired
    public OntologyInitializer(IOntologyRepository ontologyRepository){
        this.ontologyRepository = ontologyRepository;
    }
    @PostConstruct
    public void init(){

        if (!ontologyRepository.findByName(Constant.DEFAULT_ONTOLOGY).isEmpty())
            return;

        Ontology ontology = new Ontology();
        ontology.setName(Constant.DEFAULT_ONTOLOGY);
        ontologyRepository.save(ontology);

    }


}
