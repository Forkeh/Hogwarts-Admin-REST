package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.TeacherRepository;
import org.springframework.beans.BeanUtils;
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
    public ResponseEntity<Teacher> updateTeacher(@PathVariable int id, @RequestBody Teacher updatedTeacher) {
        Optional<Teacher> original = teacherRepository.findById(id);

        if (original.isPresent()) {
            // Update original teacher
            Teacher existingTeacher = original.get();
            BeanUtils.copyProperties(updatedTeacher, existingTeacher, "id");

            // Save updated teacher
            Teacher savedTeacher = teacherRepository.save(existingTeacher);

            return ResponseEntity.ok().body(savedTeacher);

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
