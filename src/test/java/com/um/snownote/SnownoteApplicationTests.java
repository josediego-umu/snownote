package com.um.snownote;

import com.um.snownote.model.Project;
import com.um.snownote.model.StructuredData;
import com.um.snownote.model.User;
import com.um.snownote.repository.interfaces.IProjectRepository;
import com.um.snownote.repository.interfaces.IStructuredDataRepository;
import com.um.snownote.repository.interfaces.IUserRepository;
import com.um.snownote.services.implementation.LoaderFileCsv;
import com.um.snownote.services.interfaces.ILoaderFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SnownoteApplicationTests {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IStructuredDataRepository structuredDataRepository;
    private final String FILEPATHCSV = "C:\\UM\\TFG\\examples\\patient_hospital_data-RESQformat.csv";
    private final String FILEPATHJSON = "C:\\UM\\TFG\\examples\\patient_hospital_data-RESQformat.json";

    @Test
    public void insertUser() {

        User user = new User();
        user.setName("test");
        user.setUsername("test");
        user.setPassword("test");
        user.setEmail("test");
        user.setDateOfBirth(LocalDateTime.now());
        userRepository.save(user);

        Optional<User> userRecover = userRepository.findById(user.getId());
        Assertions.assertTrue(userRecover.isPresent());
        Assertions.assertNotNull(userRecover.get());
    }

    @Test
    public void findByName() {

        List<User> users = userRepository.findUserByName("test");

        Assertions.assertNotNull(users);
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    public void SaveProjectTest() {
        ILoaderFile loaderFile = new LoaderFileCsv();
        StructuredData structuredData = loaderFile.load(FILEPATHCSV);
        structuredData = structuredDataRepository.save(structuredData);
        Project project = new Project();
        project.setName("test");
        project.setStructuredData(structuredData);

        projectRepository.save(project);

    }

    @Test
    public void findProjectById() {

        Optional<Project> projects = projectRepository.findById("65297376db008c56fcdb134d");

        Assertions.assertTrue(projects.isPresent());
        Assertions.assertNotNull(projects.get());
        Assertions.assertNotNull(projects.get().getStructuredData());
        Assertions.assertNotNull(projects.get().getStructuredData().getRows());
        Assertions.assertFalse(projects.get().getStructuredData().getRows().isEmpty());

    }

}
