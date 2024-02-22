package eduhogwarts.hogwartsadmin.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import eduhogwarts.hogwartsadmin.dto.CourseDTO;
import eduhogwarts.hogwartsadmin.dto.StudentDTO;
import eduhogwarts.hogwartsadmin.dto.TeacherDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import eduhogwarts.hogwartsadmin.services.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

// TODO: Make all Course layers use Course DTO
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();

        if (courses != null) {
            return ResponseEntity.ok().body(courses);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourse(@PathVariable Long id) {
        CourseDTO course = courseService.getCourse(id);

        if (course != null) {
            return ResponseEntity.ok().body(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/teacher")
    public ResponseEntity<TeacherDTO> getCourseTeacher(@PathVariable Long id) {
        TeacherDTO teacher = courseService.getCourseTeacher(id);

        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Set<StudentDTO>> getCourseStudents(@PathVariable Long id) {
        Set<StudentDTO> students = courseService.getCourseStudents(id);

        if (students != null) {
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CourseDTO> createCourse(@RequestBody CourseDTO course) {

        CourseDTO newCourse = courseService.createCourse(course);
        if (newCourse != null) {
            return ResponseEntity.ok().body(newCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/students")
    public ResponseEntity<Course> addCourseStudents(@PathVariable Long id, @RequestBody List<Object> students) {

        Course course = courseService.addCourseStudents(id, students);

        if (course != null) {
            return ResponseEntity.ok().body(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody Course updatedCourse, @PathVariable Long id) {
        try {
            Course updateCourse = courseService.updateCourse(id, updatedCourse);

            if (updateCourse != null) {
                return ResponseEntity.ok().body(updateCourse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    @PutMapping("/{id}/teacher")
    public ResponseEntity<?> updateCourseTeacher(@RequestBody TeacherDTO teacher, @PathVariable Long id) {
        try {
            Course original = courseService.updateCourseTeacher(id, teacher);

            if (original != null) {
                return ResponseEntity.ok().body(original);
            } else {
                return ResponseEntity.notFound().build();

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    @PutMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<?> addCourseStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        try {
            Set<StudentDTO> students = courseService.addCourseStudent(courseId, studentId);

            if (students != null) {
                return ResponseEntity.ok().body(students);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        try {
            Course courseToDelete = courseService.deleteCourse(id);

            if (courseToDelete != null) {
                return ResponseEntity.ok().body("Deleted course: " + courseToDelete.getSubject() + ", id: " + courseToDelete.getId());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    // TODO: Make into PUT instead of DELETE (changeCourseTeacher)
    @DeleteMapping("/{id}/teacher")
    public ResponseEntity<?> deleteCourseTeacher(@PathVariable Long id) {
        try {
            Course course = courseService.deleteCourseTeacher(id);

            if (course != null) {
                return ResponseEntity.ok().body("Removed teacher from course: " + course.getSubject() + ", id: " + course.getId());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<?> deleteCourseStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        try {
            Set<StudentDTO> students = courseService.deleteCourseStudent(courseId, studentId);

            if (students != null) {
                return ResponseEntity.ok().body(students);
            } else {
                return ResponseEntity.notFound().build();

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }
}
