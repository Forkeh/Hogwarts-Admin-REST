package eduhogwarts.hogwartsadmin.data;

import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InitData {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private HouseRepository houseRepository;

    @PostConstruct
    public void init() {
        System.out.println("Hello there!");

        House gryffindor = new House("Gryffindor", "Godric Gryffindor", new ArrayList<>(Arrays.asList("Scarlet", "Gold")));
        houseRepository.save(gryffindor);


        List<Student> students = new ArrayList<>();

        students.add(new Student("Harry", "James", "Potter", LocalDate.of(1980, Month.JULY, 31), gryffindor, false, 1991, 1998, false));
        studentRepository.saveAll(students);

    }
}
