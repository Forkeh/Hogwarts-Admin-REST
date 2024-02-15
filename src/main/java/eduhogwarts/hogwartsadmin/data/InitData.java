package eduhogwarts.hogwartsadmin.data;

import eduhogwarts.hogwartsadmin.models.EmpType;
import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import eduhogwarts.hogwartsadmin.repositories.TeacherRepository;
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
    private TeacherRepository teacherRepository;

    @Autowired
    private HouseRepository houseRepository;

    @PostConstruct
    public void init() {
        // Create and save houses to database
        House gryffindor = new House("Gryffindor", "Godric Gryffindor", new ArrayList<>(Arrays.asList("Scarlet", "Gold")));
        House hufflepuff = new House("Hufflepuff", "Helga Hufflepuff", new ArrayList<>(Arrays.asList("Yellow", "Black")));
        House ravenclaw = new House("Ravenclaw", "Rowena Ravenclaw", new ArrayList<>(Arrays.asList("Blue", "Bronze")));
        House slytherin = new House("Slytherin", "Salazar Slytherin", new ArrayList<>(Arrays.asList("Green", "Silver")));
        houseRepository.save(gryffindor);
        houseRepository.save(hufflepuff);
        houseRepository.save(ravenclaw);
        houseRepository.save(slytherin);


        // Create and save students to database
        List<Student> students = new ArrayList<>();

        students.add(new Student("Harry", "James", "Potter", LocalDate.of(1980, Month.JULY, 31), gryffindor, false, 1991, 1998, false));
        students.add(new Student("Hermione", "Jean", "Granger", LocalDate.of(1979, Month.SEPTEMBER, 19), gryffindor, true, 1991, 1998, false));
        students.add(new Student("Ronald", "Bilius", "Weasley", LocalDate.of(1980, Month.MARCH, 1), gryffindor, false, 1991, 1998, false));
        students.add(new Student("Cedric", "Diggory", LocalDate.of(1977, Month.OCTOBER, 1), hufflepuff, true, 1990, 1997, true));
        students.add(new Student("Nymphadora", "Tonks", LocalDate.of(1973, Month.DECEMBER, 28), hufflepuff, false, 1984, 1991, true));
        students.add(new Student("Luna", "Lovegood", LocalDate.of(1981, Month.FEBRUARY, 13), ravenclaw, false, 1992, 1999, false));
        students.add(new Student("Cho", "Chang", LocalDate.of(1979, Month.AUGUST, 2), ravenclaw, false, 1990, 1997, false));
        students.add(new Student("Draco", "Lucius", "Malfoy", LocalDate.of(1980, Month.JUNE, 5), slytherin, false, 1991, 1998, false));
        students.add(new Student("Severus", "Snape", LocalDate.of(1960, Month.JANUARY, 9), slytherin, true, 1971, 1978, true));
        students.add(new Student("Ginny", "Molly", "Weasley", LocalDate.of(1981, Month.AUGUST, 11), gryffindor, false, 1992, 1999, false));
        students.add(new Student("Vincent", "Crabbe", LocalDate.of(1980, Month.JANUARY, 1), slytherin, false, 1991, 1998, false));
        studentRepository.saveAll(students);

        // Create and save students to database
        List<Teacher> teachers = new ArrayList<>();

        teachers.add(new Teacher("Severus", "Snape", LocalDate.of(1960, Month.JANUARY, 9), slytherin, true, EmpType.TENURED, LocalDate.of(1981, Month.JANUARY, 1), LocalDate.of(1998, Month.MAY, 2)));
        teachers.add(new Teacher("Minerva", "McGonagall", LocalDate.of(1935, Month.OCTOBER, 4), gryffindor, true, EmpType.TENURED, LocalDate.of(1956, Month.JULY, 12), null));
        teachers.add(new Teacher("Filius", "Flitwick", LocalDate.of(1958, Month.OCTOBER, 17), ravenclaw, false, EmpType.TEMPORARY, LocalDate.of(1975, Month.SEPTEMBER, 1), LocalDate.of(1998, Month.MAY, 2)));
        teachers.add(new Teacher("Pomona", "Sprout", LocalDate.of(1950, Month.MAY, 15), hufflepuff, true, EmpType.TENURED, LocalDate.of(1974, Month.SEPTEMBER, 1), null));
        teachers.add(new Teacher("Sybill", "Trelawney", LocalDate.of(1959, Month.MARCH, 9), ravenclaw, false, EmpType.TEMPORARY, LocalDate.of(1979, Month.SEPTEMBER, 1), LocalDate.of(1998, Month.MAY, 2)));
        teachers.add(new Teacher("Quirinus", "Quirrell", LocalDate.of(1965, Month.MAY, 26), slytherin, false, EmpType.TEMPORARY, LocalDate.of(1990, Month.SEPTEMBER, 1), LocalDate.of(1991, Month.JUNE, 25)));

        teacherRepository.saveAll(teachers);
    }
}
