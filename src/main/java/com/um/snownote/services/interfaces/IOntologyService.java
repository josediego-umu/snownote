package com.um.snownote.services.interfaces;

import com.um.snownote.model.Ontology;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IOntologyService {

    public Ontology loadOntology(MultipartFile file, String name, String iri);
    public Ontology save(Ontology ontology);
    public OWLOntology getOntology(Ontology ontology) throws OWLOntologyCreationException;
    public OWLOntology getOntologyById(String id);
    public void deleteOntology(Ontology ontology);


}
