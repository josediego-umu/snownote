package com.um.snownote.services.implementation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.um.snownote.model.Ontology;
import com.um.snownote.repository.interfaces.IOntologyRepository;
import com.um.snownote.services.interfaces.IOntologyService;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class OntologyService implements IOntologyService {

    private final IOntologyRepository ontologyRepository;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OntologyService.class);

    @Autowired
    public OntologyService(IOntologyRepository ontologyRepository) {
        this.ontologyRepository = ontologyRepository;
    }

    public Ontology loadOntology(MultipartFile file, String name, String iri) {

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
            OWLOntology owlOntology = manager.loadOntologyFromOntologyDocument(new StreamDocumentSource(inputStream));


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            manager.saveOntology(owlOntology, new RDFJsonLDDocumentFormat(), outputStream);


            Ontology ontology = new Ontology();

            ontology.setIri(iri);
            ontology.setData(outputStream.toString());

            if (name == null) {
                ontology.setName(file.getOriginalFilename());
            } else {
                ontology.setName(name);
            }


            return this.save(ontology);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }


    }

    @Override
    public Ontology save(Ontology ontology) {
        return this.ontologyRepository.save(ontology);
    }

    @Override
    public OWLOntology getOntology(Ontology ontology) throws OWLOntologyCreationException {

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        return manager.loadOntologyFromOntologyDocument(new StringDocumentSource(ontology.getData()));


    }

    @Override
    public OWLOntology getOntologyById(String id) {
        Ontology ontology = this.ontologyRepository.findById(id).orElse(null);

        if (ontology == null) {
            return null;
        }

        try {
            return getOntology(ontology);
        } catch (OWLOntologyCreationException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void deleteOntology(Ontology ontology) {
        this.ontologyRepository.delete(ontology);
    }
}
