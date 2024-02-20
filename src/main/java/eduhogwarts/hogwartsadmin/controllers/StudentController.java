package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.dto.StudentDTO;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import eduhogwarts.hogwartsadmin.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
        StudentDTO student = studentService.getStudent(id);

        if (student != null) {
            return ResponseEntity.ok().body(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(id, student);

        if (updateStudent != null) {
            return ResponseEntity.ok().body(updateStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student deletedStudent = studentService.deleteStudent(id);

        if (deletedStudent != null) {
            return ResponseEntity.ok().body(deletedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
