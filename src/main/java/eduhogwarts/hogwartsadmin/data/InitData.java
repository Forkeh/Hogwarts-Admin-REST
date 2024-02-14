package eduhogwarts.hogwartsadmin.data;

import eduhogwarts.hogwartsadmin.controllers.StudentController;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitData {

    @Autowired
    private StudentRepository studentRepository;

    @PostConstruct
    public void init() {
        System.out.println("Hello there!");
        List<Student> students = new ArrayList<>();

        students.add("Harry", "James", "Potter", LocalDate.of(1980, Month.JULY, 31), gryffindor, false, 1991, 1998, false));

    }
}
