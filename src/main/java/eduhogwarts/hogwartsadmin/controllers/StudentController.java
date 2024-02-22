package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.dto.StudentDTO;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import eduhogwarts.hogwartsadmin.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

        return ResponseEntity.ok().body(student);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO student) {

        StudentDTO createdStudent = studentService.createStudent(student);

        return ResponseEntity.ok().body(createdStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO student) {
        StudentDTO updateStudent = studentService.updateStudent(id, student);

        return ResponseEntity.ok().body(updateStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> deleteStudent(@PathVariable Long id) {
        StudentDTO deletedStudent = studentService.deleteStudent(id);

        return ResponseEntity.ok().body(deletedStudent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StudentDTO> patchStudentFields(@PathVariable Long id, @RequestBody Map<String, Object> fields) {
        StudentDTO patchedStudent = studentService.patchStudentFields(id, fields);

        return ResponseEntity.ok().body(patchedStudent);
    }
}
