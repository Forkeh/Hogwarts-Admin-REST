package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.dto.TeacherDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import eduhogwarts.hogwartsadmin.repositories.TeacherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

// TODO Make all students returns into DTOs
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
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


    public Set<Student> getCourseStudents(Long id) {
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

    public Course updateCourseTeacher(Long id, TeacherDTO teacher) {
        Optional<Course> original = courseRepository.findById(id);
        Optional<Teacher> originalTeacher = teacherRepository.findById(teacher.getId());

        if (original.isPresent() && originalTeacher.isPresent()) {
            Course originalCourse = original.get();

            //Update original course
            originalCourse.setTeacher(originalTeacher.get());

            // Save and return updated course
            return courseRepository.save(originalCourse);
        } else {
            return null;
        }
    }

    public Set<Student> addCourseStudent(Long courseId, Long studentId) {
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

    public Set<Student> deleteCourseStudent(Long courseId, Long studentId) {
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

    public Course addCourseStudents(Long id, List<Object> students) {
        Optional<Course> original = courseRepository.findById(id);

        if (original.isEmpty()) return null;

        // Loop through students info
        for (Object studentInfo : students) {

            // Create a map from the student info
            @SuppressWarnings("unchecked")
            Map<String, Object> studentMap = (Map<String, Object>) studentInfo;

            // Loop through the keys in the map
            for (String key : studentMap.keySet()) {

                // If the key is "id", find the student by id and add them to the course
                if (key.equals("id")) {
                    Optional<Student> student = studentRepository.findById(Long.parseLong(studentMap.get(key).toString()));
                    student.ifPresent(foundStudent -> addCourseStudent(id, foundStudent.getId()));
                }

                // If the key is "name", split the name and find the student by first name and add them to the course
                else if (key.equals("name")) {
                    String[] nameParts = nameSplitter((String) studentMap.get(key));
                    Student student = studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(nameParts[0], nameParts[nameParts.length - 1]);
                    if (student != null) {
                        addCourseStudent(id, student.getId());
                    }
                }
            }
        }
        return null;
    }

    private String[] nameSplitter(String name) {
        return name.split(" ");
    }
}
