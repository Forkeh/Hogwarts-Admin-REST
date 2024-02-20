package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Teacher getCourseTeacher(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            Course currentCourse = course.get();
            return currentCourse.getTeacher();
        } else {
            return null;
        }
    }

    public List<Student> getCourseStudents(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            Course currentCourse = course.get();
            return currentCourse.getStudents();
        } else {
            return null;
        }
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Optional<Course> original = courseRepository.findById(id);

        if (original.isPresent()) {
            Course originalCourse = original.get();

            //Update original course
            BeanUtils.copyProperties(updatedCourse, originalCourse, "id");


            // Save and return updated course
            return courseRepository.save(originalCourse);
        } else {
            return null;
        }
    }

    public Course updateCourseTeacher(Long id, Teacher teacher) {
        Optional<Course> original = courseRepository.findById(id);

        if (original.isPresent()) {
            Course originalCourse = original.get();

            //Update original course
            originalCourse.setTeacher(teacher);

            // Save and return updated course
            return courseRepository.save(originalCourse);
        } else {
            return null;
        }
    }

    public List<Student> addCourseStudent(Long courseId, Long studentId) {
        Optional<Course> originalCourse = courseRepository.findById(courseId);
        Optional<Student> originalStudent = studentRepository.findById(studentId);

        // check if course and student exist
        if (originalCourse.isPresent() & originalStudent.isPresent()) {
            Course course = originalCourse.get();
            Student student = originalStudent.get();

            // check if student is already in course
            if (!course.getStudents().contains(student)) {

                // if not, add student to course
                course.getStudents().add(student);

                courseRepository.save(course);
            }
            return course.getStudents();
        } else {
            return null;
        }
    }

    public Course deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            Course courseToDelete = course.get();
            courseRepository.deleteById(id);
            return courseToDelete;
        } else {
            return null;
        }
    }

    public Course deleteCourseTeacher(Long id) {
        Optional<Course> original = courseRepository.findById(id);

        if (original.isPresent()) {
            Course course = original.get();
            course.setTeacher(null);

            courseRepository.save(course);
            return course;
        } else {
            return null;
        }
    }

    public List<Student> deleteCourseStudent(Long courseId, Long studentId) {
        Optional<Course> originalCourse = courseRepository.findById(courseId);
        Optional<Student> originalStudent = studentRepository.findById(studentId);

        if (originalCourse.isPresent() & originalStudent.isPresent()) {
            Course course = originalCourse.get();
            Student student = originalStudent.get();

            if (course.getStudents().contains(student)) {
                course.getStudents().remove(student);
                courseRepository.save(course);
            }
            return course.getStudents();
        } else {
            return null;
        }
    }
}
