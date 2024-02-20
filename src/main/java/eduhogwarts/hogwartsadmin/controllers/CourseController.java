package eduhogwarts.hogwartsadmin.controllers;

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

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CourseService courseService;

    public CourseController(CourseRepository courseRepository, StudentRepository studentRepository, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        Course course = courseService.getCourse(id);

        if (course != null) {
            return ResponseEntity.ok().body(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/teacher")
    public ResponseEntity<Teacher> getCourseTeacher(@PathVariable Long id) {
        Teacher teacher = courseService.getCourseTeacher(id);

        if (teacher != null) {
            return ResponseEntity.ok(teacher);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getCourseStudents(@PathVariable Long id) {
        List<Student> students = courseService.getCourseStudents(id);

        if (students != null) {
            return ResponseEntity.ok(students);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
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
    public ResponseEntity<?> updateCourseTeacher(@RequestBody Teacher teacher, @PathVariable Long id) {
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
            List<Student> students = courseService.addCourseStudent(courseId, studentId);

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
            List<Student> students = courseService.deleteCourseStudent(courseId, studentId);

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
