package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.dto.CourseDTO;
import eduhogwarts.hogwartsadmin.dto.StudentDTO;
import eduhogwarts.hogwartsadmin.dto.TeacherDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import eduhogwarts.hogwartsadmin.repositories.TeacherRepository;
import eduhogwarts.hogwartsadmin.utils.ModelMapper;
import eduhogwarts.hogwartsadmin.utils.Utilities;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final Utilities utilities;
    private final ModelMapper modelMapper;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, Utilities utilities, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.utilities = utilities;
        this.modelMapper = modelMapper;
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.
                findAll().
                stream().
                map(modelMapper::courseModelToDTO).
                toList();
    }

    public CourseDTO getCourse(Long id) {

        return courseRepository.findById(id).map(modelMapper::courseModelToDTO).
                orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    public TeacherDTO getCourseTeacher(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            Course currentCourse = course.get();
            return modelMapper.teacherModelToDTO(currentCourse.getTeacher());
        } else {
            throw new IllegalArgumentException("Course not found");
        }
    }

    public Set<StudentDTO> getCourseStudents(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            return modelMapper.getStudentDTOS(course.get().getStudents());
        } else {
            throw new IllegalArgumentException("Course not found");
        }
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Teacher teacher = findTeacherById(courseDTO.getTeacher().getId());
        Set<Student> students = findStudentsByIds(courseDTO.getStudents().stream().map(StudentDTO::getId).collect(Collectors.toSet()));
        Course newCourse = new Course(courseDTO.getSubject(), courseDTO.getSchoolYear(), courseDTO.isCurrent(), teacher, students);

        courseRepository.save(newCourse);
        return modelMapper.courseModelToDTO(newCourse);
    }


    public CourseDTO updateCourse(Long id, CourseDTO updatedCourse) {
        Optional<Course> original = courseRepository.findById(id);

        if (original.isPresent()) {
            Course originalCourse = original.get();

            //Update original course
            BeanUtils.copyProperties(updatedCourse, originalCourse, "id");


            // Save and return updated course
            courseRepository.save(originalCourse);
            return modelMapper.courseModelToDTO(originalCourse);
        } else {
            throw new IllegalArgumentException("Course not found");
        }
    }

//    public CourseDTO updateCourseTeacher(Long id, TeacherDTO teacher) {
//        Optional<Course> original = courseRepository.findById(id);
//        Optional<Teacher> originalTeacher = teacherRepository.findById(teacher.getId());
//
//        if (original.isPresent() && originalTeacher.isPresent()) {
//            Course originalCourse = original.get();
//
//            //Update original course
//            originalCourse.setTeacher(originalTeacher.get());
//
//            // Save and return updated course
//            courseRepository.save(originalCourse);
//            return modelMapper.courseModelToDTO(originalCourse);
//        } else {
//            return null;
//        }
//    }

    public Set<StudentDTO> addCourseStudent(Long courseId, Long studentId) {
        Optional<Course> originalCourse = courseRepository.findById(courseId);
        Optional<Student> originalStudent = studentRepository.findById(studentId);

        // check if course and student exist
        if (originalCourse.isEmpty() | originalStudent.isEmpty())
            throw new IllegalArgumentException("Course or student not found");

        Course course = originalCourse.get();
        Student student = originalStudent.get();

        // check if student is already in course
        if (course.getStudents().contains(student))
            throw new IllegalArgumentException("Student is already in course");

        // if not, check if student is in the same school year as the course
        if (!isSchoolYearMatch(course.getSchoolYear(), student.getSchoolYear()))
            throw new IllegalArgumentException("Student is not in the same school year as the course");

        course.getStudents().add(student);
        courseRepository.save(course);

        // Return updated course list
        return modelMapper.getStudentDTOS(course.getStudents());


    }

    public CourseDTO deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            CourseDTO courseToDelete = modelMapper.courseModelToDTO(course.get());

            courseRepository.deleteById(id);

            return courseToDelete;
        } else {
            throw new IllegalArgumentException("Course not found");
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
            throw new IllegalArgumentException("Course not found");
        }
    }

    public CourseDTO updateCourseTeacher(Long id, Map<String, Long> teacherId) {
        Optional<Course> original = courseRepository.findById(id);

        if (original.isPresent()) {
            Course course = original.get();
            // Get teacher id from map
            var teacherIdValue = teacherId.get("id");

            if (teacherIdValue == null) {
                // Remove teacher from course
                course.setTeacher(null);
            } else {
                // Add teacher to course
                Optional<Teacher> originalTeacher = teacherRepository.findById(Long.parseLong(teacherIdValue.toString()));

                if (originalTeacher.isEmpty()) throw new IllegalArgumentException("Teacher not found");

                course.setTeacher(originalTeacher.get());
            }
            courseRepository.save(course);
            return modelMapper.courseModelToDTO(original.get());
        }
        throw new IllegalArgumentException("Course not found");
    }

    public Set<StudentDTO> deleteCourseStudent(Long courseId, Long studentId) {
        Optional<Course> originalCourse = courseRepository.findById(courseId);
        Optional<Student> originalStudent = studentRepository.findById(studentId);

        if (originalCourse.isPresent() & originalStudent.isPresent()) {
            Course course = originalCourse.get();
            Student student = originalStudent.get();

            if (course.getStudents().contains(student)) {
                course.getStudents().remove(student);
                courseRepository.save(course);
            }
            return modelMapper.getStudentDTOS(course.getStudents());
        } else {
            throw new IllegalArgumentException("Course or student not found");
        }
    }

    public CourseDTO addCourseStudents(Long id, List<Object> students) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) throw new IllegalArgumentException("Course not found");

        // Loop through students info
        for (Object studentInfo : students) {

            // Create a map from the student info (keys are "id" and/or "name")
            @SuppressWarnings("unchecked")
            Map<String, Object> studentMap = (Map<String, Object>) studentInfo;

            // Loop through the keys in the map
            for (String key : studentMap.keySet()) {

                // If the key is "id", find the student by id and add them to the course
                if (key.equals("id")) {
                    // Parse the id from the map and find the student by id
                    Long studentId = Long.parseLong(studentMap.get(key).toString());
                    Optional<Student> student = studentRepository.findById(studentId);
                    student.ifPresent(foundStudent -> addCourseStudent(id, foundStudent.getId()));
                }

                // If the key is "name", split the name and find the student by first or last name and add them to the course
                else if (key.equals("name")) {
                    String[] nameParts = utilities.nameSplitter((String) studentMap.get(key));
                    Optional<Student> student = studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(nameParts[0], nameParts[nameParts.length - 1]);
                    student.ifPresent(foundStudent -> addCourseStudent(id, foundStudent.getId()));
                }
            }
        }
        return modelMapper.courseModelToDTO(course.get());
    }

    private Teacher findTeacherById(Long id) {

        return teacherRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
    }

    private Set<Student> findStudentsByIds(Set<Long> studentIds) {

        return new HashSet<>(studentRepository.findAllById(studentIds));
    }

    private boolean isSchoolYearMatch(int courseYear, int studentYear) {
        return courseYear == studentYear;
    }


}
