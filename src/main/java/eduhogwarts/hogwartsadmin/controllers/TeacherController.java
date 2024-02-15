package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.TeacherRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable int id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);

        return ResponseEntity.of(teacher);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable int id, @RequestBody Teacher teacher) {
        Optional<Teacher> original = teacherRepository.findById(id);

        if(original.isPresent()) {
        // Update original teacher
        Teacher originalTeacher = original.get();
        originalTeacher.setFirstName(teacher.getFirstName());
        originalTeacher.setMiddleName(teacher.getMiddleName());
        originalTeacher.setLastName(teacher.getLastName());
        originalTeacher.setDateOfBirth(teacher.getDateOfBirth());
        originalTeacher.setHouse(teacher.getHouse());
        originalTeacher.setHeadOfHouse(teacher.isHeadOfHouse());
        originalTeacher.setEmployment(teacher.getEmployment());
        originalTeacher.setEmploymentStart(teacher.getEmploymentStart());
        originalTeacher.setEmploymentEnd(teacher.getEmploymentEnd());

        // Save updated teacher
        Teacher updatedTeacher = teacherRepository.save(originalTeacher);

        return ResponseEntity.ok().body(updatedTeacher);

        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable int id) {

        Optional<Teacher> deletedTeacher = teacherRepository.findById(id);

        teacherRepository.deleteById(id);

        return ResponseEntity.of(deletedTeacher);
    }
}
