package com.um.snownote;

import com.um.snownote.dto.ProjectDTO;
import com.um.snownote.filters.CompoundFilter;
import com.um.snownote.filters.Filter;
import com.um.snownote.filters.GenericFilter;
import com.um.snownote.model.Project;
import com.um.snownote.model.User;
import com.um.snownote.repository.interfaces.IProjectRepository;
import com.um.snownote.repository.interfaces.IUserRepository;
import com.um.snownote.services.implementation.ProjectServices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class SnownoteApplicationTests {
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IUserRepository userRepository;
   /* @Autowired

    @Autowired

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

    }*/

    @Test
    public void test() {

        Optional<Project> project = projectRepository.findById("6623b249343c7c70074596bd");
        User testReader = userRepository.findUserByUsername("testReader");
        User testWriter = userRepository.findUserByUsername("testWriter");

        if (project.isPresent()) {

            project.get().addReader(testReader);
            project.get().addWriter(testWriter);

            projectRepository.save(project.get());
        }


    }
}
