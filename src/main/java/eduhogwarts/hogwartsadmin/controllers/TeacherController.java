package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.dto.TeacherDTO;
import eduhogwarts.hogwartsadmin.models.EmpType;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.services.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// TODO: Refactor to use TeacherDTO through every layer
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

        if (teacher != null) {
            return ResponseEntity.ok().body(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        Teacher original = teacherService.updateTeacher(id, updatedTeacher);

        if (original != null) {
            return ResponseEntity.ok().body(original);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable Long id) {

        Teacher deletedTeacher = teacherService.deleteTeacher(id);

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
