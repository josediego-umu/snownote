package com.um.snownote;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.um.snownote.model.Ontology;
import com.um.snownote.services.interfaces.IFileService;
import com.um.snownote.services.interfaces.IOntologyService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@SpringBootTest
public class Test {

    @Autowired
    IFileService fileService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IOntologyService ontologyService;
    @org.junit.jupiter.api.Test
    public void testFileService() throws Exception {
        IRI.create("http://www.semanticweb.org/ontologies/2021/0/untitled-ontology-1#");

        // Crear un administrador de ontologías
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory dataFactory = manager.getOWLDataFactory();

        // Crear una nueva ontología
        IRI ontologyIRI = IRI.create("http://example.com/snomed");
        OWLOntology ontology = manager.createOntology(ontologyIRI);

        // Crear clases para conceptos de SNOMED CT
        OWLClass diseaseClass = dataFactory.getOWLClass(IRI.create(ontologyIRI + "#Disease"));
        OWLClass heartDiseaseClass = dataFactory.getOWLClass(IRI.create(ontologyIRI + "#HeartDisease"));
        OWLClass myocardialInfarctionClass = dataFactory.getOWLClass(IRI.create(ontologyIRI + "#MyocardialInfarction"));

        // Crear axiomas de declaración para las clases
        OWLAxiom declareDisease = dataFactory.getOWLDeclarationAxiom(diseaseClass);
        OWLAxiom declareHeartDisease = dataFactory.getOWLDeclarationAxiom(heartDiseaseClass);
        OWLAxiom declareMyocardialInfarction = dataFactory.getOWLDeclarationAxiom(myocardialInfarctionClass);

        // Agregar los axiomas a la ontología
        manager.addAxiom(ontology, declareDisease);
        manager.addAxiom(ontology, declareHeartDisease);
        manager.addAxiom(ontology, declareMyocardialInfarction);

        // Crear relaciones "is-a" entre las clases
        OWLAxiom heartDiseaseIsADisease = dataFactory.getOWLSubClassOfAxiom(heartDiseaseClass, diseaseClass);
        OWLAxiom myocardialInfarctionIsAHeartDisease = dataFactory.getOWLSubClassOfAxiom(myocardialInfarctionClass, heartDiseaseClass);

        // Agregar los axiomas de relaciones a la ontología
        manager.addAxiom(ontology, heartDiseaseIsADisease);
        manager.addAxiom(ontology, myocardialInfarctionIsAHeartDisease);

        // Crear una propiedad de objeto para la relación "causedBy"
        OWLObjectProperty causedBy = dataFactory.getOWLObjectProperty(IRI.create(ontologyIRI + "#causedBy"));

        // Declarar la propiedad de objeto
        OWLAxiom declareCausedBy = dataFactory.getOWLDeclarationAxiom(causedBy);
        manager.addAxiom(ontology, declareCausedBy);

        // Crear una restricción existencial: "MyocardialInfarction" causedBy "HeartDisease"
        OWLClassExpression causedByHeartDisease = dataFactory.getOWLObjectSomeValuesFrom(causedBy, heartDiseaseClass);
        OWLAxiom myocardialInfarctionCausedByHeartDisease = dataFactory.getOWLSubClassOfAxiom(myocardialInfarctionClass, causedByHeartDisease);

        // Agregar el axioma de restricción existencial a la ontología
        manager.addAxiom(ontology, myocardialInfarctionCausedByHeartDisease);
        // Serializar la ontología
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        manager.saveOntology(ontology, new RDFJsonLDDocumentFormat(), outputStream);




        // Almacenar la ontología en la base de datos
        String jsonLd = outputStream.toString();

        Document document = new Document("ontologies",jsonLd);
        System.out.println(jsonLd);
        System.out.println(document);
        System.out.println(mongoTemplate.getCollectionNames());
        mongoTemplate.save(document,"ontologies");


    }

    @org.junit.jupiter.api.Test
    public void loadOntology() throws IOException, OWLOntologyCreationException {

        File file = new File("C:\\UM\\TFG\\examples\\koala.owl");
        FileInputStream input = new FileInputStream(file);

        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/xml", input);

        Ontology ontology = this.ontologyService.loadOntology(multipartFile, "koala", "http://example.org/ontologias/koala.owl");

        Assertions.assertNotNull(ontology);
        Assertions.assertNotNull(ontology.getId());
        Assertions.assertEquals("koala", ontology.getName());
        Assertions.assertNotNull(ontology.getData());

        OWLOntologyManager manager =  this.ontologyService.getOntologyManager(ontology);

        Assertions.assertNotNull(manager);


    }

}
