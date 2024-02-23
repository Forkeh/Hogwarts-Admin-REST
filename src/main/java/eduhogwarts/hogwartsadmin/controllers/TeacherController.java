package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.dto.TeacherDTO;
import eduhogwarts.hogwartsadmin.models.EmpType;
import eduhogwarts.hogwartsadmin.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// TODO: Return ResponseEntity.of instead
@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.getAllTeachers();

    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long id) {
        TeacherDTO teacher = teacherService.getTeacherById(id);

        return ResponseEntity.ok().body(teacher);
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody TeacherDTO teacher) {
        TeacherDTO createdTeacher = teacherService.createTeacher(teacher);

        return ResponseEntity.ok().body(createdTeacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherDTO updatedTeacher) {
        TeacherDTO original = teacherService.updateTeacher(id, updatedTeacher);

        if (original != null) {
            return ResponseEntity.ok().body(original);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TeacherDTO> deleteTeacher(@PathVariable Long id) {

        TeacherDTO deletedTeacher = teacherService.deleteTeacher(id);

        if (deletedTeacher != null) {
            return ResponseEntity.ok().body(deletedTeacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/headOfHouse")
    public ResponseEntity<TeacherDTO> patchTeacherHeadOfHouse(@PathVariable Long id) {
        TeacherDTO teacher = teacherService.patchTeacherHeadOfHouse(id);
        if (teacher != null) {
            return ResponseEntity.ok().body(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/employmentEnd")
    public ResponseEntity<TeacherDTO> patchTeacherEmploymentEnd(@PathVariable Long id, @RequestBody Map<String, LocalDate> employmentEnd) {
        TeacherDTO teacher = teacherService.patchTeacherEmploymentEnd(id, employmentEnd);
        if (teacher != null) {
            return ResponseEntity.ok().body(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/employment")
    public ResponseEntity<TeacherDTO> patchTeacherEmployment(@PathVariable Long id, @RequestBody Map<String, EmpType> employment) {
        TeacherDTO teacher = teacherService.patchTeacherEmployment(id, employment);
        if (teacher != null) {
            return ResponseEntity.ok().body(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
