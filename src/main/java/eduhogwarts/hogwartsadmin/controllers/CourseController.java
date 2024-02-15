package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
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

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            return ResponseEntity.of(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/teacher")
    public ResponseEntity<Teacher> getCourseTeacher(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            Course currentCourse = course.get();
            Teacher courseTeacher = currentCourse.getTeacher();

            return ResponseEntity.ok(courseTeacher);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getCourseStudents(@PathVariable int id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            Course currentCourse = course.get();
            List<Student> courseStudents = currentCourse.getStudents();

            return ResponseEntity.ok(courseStudents);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@RequestBody Course updatedCourse, @PathVariable int id) {
        try {
            Optional<Course> original = courseRepository.findById(id);

            if (original.isPresent()) {
                Course existingCourse = original.get();

                BeanUtils.copyProperties(updatedCourse, existingCourse, "id");

                Course savedCourse = courseRepository.save(existingCourse);

                return ResponseEntity.ok().body(savedCourse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    @PutMapping("/{id}/teacher")
    public ResponseEntity<?> updateCourseTeacher(@RequestBody Teacher teacher, @PathVariable int id) {
        try {
            Optional<Course> original = courseRepository.findById(id);

            if (original.isPresent()) {
                Course course = original.get();
                course.setTeacher(teacher);
                courseRepository.save(course);
                return ResponseEntity.ok().body(course);
            } else {
                return ResponseEntity.notFound().build();

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    @PutMapping("/{id}/students")
    public ResponseEntity<?> addCourseStudent(@RequestBody Student student, @PathVariable int id) {
        try {
            Optional<Course> original = courseRepository.findById(id);

            if (original.isPresent()) {
                Course course = original.get();
                course.addStudent(student);
                return ResponseEntity.ok().body(course.getStudents());
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable int id) {
        try {
            Optional<Course> original = courseRepository.findById(id);

            if (original.isPresent()) {
                Course deletedCourse = original.get();

                courseRepository.deleteById(id);

                return ResponseEntity.ok().body("Deleted course: " + deletedCourse.getSubject() + ", id: " + deletedCourse.getId());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    @DeleteMapping("/{id}/teacher")
    public ResponseEntity<?> deleteCourseTeacher(@PathVariable int id) {
        try {
            Optional<Course> original = courseRepository.findById(id);

            if (original.isPresent()) {
                Course course = original.get();
                course.setTeacher(null);

                courseRepository.save(course);

                return ResponseEntity.ok().body("Removed teacher from course: " + course.getSubject() + ", id: " + course.getId());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<?> deleteCourseStudent(@PathVariable int courseId, @PathVariable int studentId) {
        try {
            Optional<Course> originalCourse = courseRepository.findById(courseId);

            if (originalCourse.isPresent()) {
                Course course = originalCourse.get();
                List<Student> students = course.getStudents();
                Optional<Student> student = students.stream().filter(s -> s.getId() == studentId).findFirst();

                if (student.isPresent()) {
                    Student studentToDelete = student.get();
                    course.removeStudent(studentToDelete);
                    courseRepository.save(course);
                    return ResponseEntity.ok().body("Removed student from course: " + course.getSubject() + ", id: " + course.getId());
                } else {
                    return ResponseEntity.notFound().build();

                }
            } else {
                return ResponseEntity.notFound().build();

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the server");
        }
    }
}
