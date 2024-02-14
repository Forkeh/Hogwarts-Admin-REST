package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);

        return ResponseEntity.of(student);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        Optional<Student> original = studentRepository.findById(id);

        if (original.isPresent()) {
            Student originalStudent = original.get();

            //Update original student
            originalStudent.setFirstName(student.getFirstName());
            originalStudent.setMiddleName(student.getMiddleName());
            originalStudent.setLastName(student.getLastName());
            originalStudent.setDateOfBirth(student.getDateOfBirth());
            originalStudent.setHouse(student.getHouse());
            originalStudent.setPrefect(student.isPrefect());
            originalStudent.setEnrollmentYear(student.getEnrollmentYear());
            originalStudent.setGraduationYear(student.getGraduationYear());
            originalStudent.setGraduated(student.isGraduated());

            // Save and return updated student
            Student updatedStudent = studentRepository.save(originalStudent);
            return ResponseEntity.ok().body(updatedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable int id) {
        Optional<Student> student = studentRepository.findById(id);

        studentRepository.deleteById(id);

        return ResponseEntity.of(student);
    }
}
