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
import eduhogwarts.hogwartsadmin.utils.DTOMapper;
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
    private final DTOMapper DTOMapper;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, TeacherRepository teacherRepository, Utilities utilities, DTOMapper DTOMapper) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.utilities = utilities;
        this.DTOMapper = DTOMapper;
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.
                findAll().
                stream().
                map(DTOMapper::courseModelToDTO).
                toList();
    }

    public CourseDTO getCourse(Long id) {

        return courseRepository.findById(id).map(DTOMapper::courseModelToDTO).
                orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    public TeacherDTO getCourseTeacher(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) throw new IllegalArgumentException("Course not found");

        return DTOMapper.teacherModelToDTO(course.get().getTeacher());

    }

    public Set<StudentDTO> getCourseStudents(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) throw new IllegalArgumentException("Course not found");

        return DTOMapper.getStudentDTOS(course.get().getStudents());

    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Teacher teacher = findTeacherById(courseDTO.teacher().id()).orElse(null);
        Set<Student> students = findStudentsByIds(courseDTO.students().stream().map(StudentDTO::id).collect(Collectors.toSet()));
        Course newCourse = new Course(courseDTO.subject(), courseDTO.schoolYear(), courseDTO.current(), teacher, students);

        courseRepository.save(newCourse);
        return DTOMapper.courseModelToDTO(newCourse);
    }


    public CourseDTO updateCourse(Long id, CourseDTO updatedCourse) {
        Optional<Course> original = courseRepository.findById(id);

        if (original.isEmpty()) throw new IllegalArgumentException("Course not found");
        Course originalCourse = original.get();

        //Update original course
        BeanUtils.copyProperties(updatedCourse, originalCourse, "id");

        // Save and return updated course
        courseRepository.save(originalCourse);
        return DTOMapper.courseModelToDTO(originalCourse);
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
            throw new IllegalArgumentException("Student " + student.getFirstName() + " is already in course");

        // if not, check if student is in the same school year as the course
        if (!isSchoolYearMatch(course.getSchoolYear(), student.getSchoolYear()))
            throw new IllegalArgumentException("Student " + student.getFirstName() + " is not in the same school year as the course");

        course.getStudents().add(student);
        courseRepository.save(course);

        // Return updated course list
        return DTOMapper.getStudentDTOS(course.getStudents());


    }

    public CourseDTO deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) throw new IllegalArgumentException("Course not found");

        CourseDTO courseToDelete = DTOMapper.courseModelToDTO(course.get());

        courseRepository.deleteById(id);

        return courseToDelete;
    }

    public Course deleteCourseTeacher(Long id) {
        Optional<Course> original = courseRepository.findById(id);

        if (original.isEmpty()) throw new IllegalArgumentException("Course not found");

        Course course = original.get();
        course.setTeacher(null);

        courseRepository.save(course);
        return course;

    }

    public CourseDTO updateCourseTeacher(Long id, Map<String, Long> teacherId) {
        Optional<Course> original = courseRepository.findById(id);

        if (original.isEmpty()) throw new IllegalArgumentException("Course not found");

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
        return DTOMapper.courseModelToDTO(original.get());


    }

    public Set<StudentDTO> deleteCourseStudent(Long courseId, Long studentId) {
        Optional<Course> originalCourse = courseRepository.findById(courseId);
        Optional<Student> originalStudent = studentRepository.findById(studentId);

        if (originalCourse.isEmpty() | originalStudent.isEmpty())
            throw new IllegalArgumentException("Course or student not found");
        Course course = originalCourse.get();
        Student student = originalStudent.get();

        // Remove student from course if they are in the course
        if (course.getStudents().contains(student)) {
            course.getStudents().remove(student);
            courseRepository.save(course);
        }
        return DTOMapper.getStudentDTOS(course.getStudents());
    }

    @Transactional
    public CourseDTO addCourseStudents(Long id, List<Map<String, Object>> students) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Iterate through students and add them to course
        for (Map<String, Object> studentInfo : students) {
            // Add student to course by id
            if (studentInfo.containsKey("id")) {

                Long studentId = Long.parseLong(studentInfo.get("id").toString());
                addStudentToCourseById(id, studentId);

                // Add student to course by name
            } else if (studentInfo.containsKey("name")) {

                String[] nameParts = utilities.nameSplitter((String) studentInfo.get("name"));
                addStudentToCourseByName(id, nameParts);
            }
        }

        return DTOMapper.courseModelToDTO(course);
    }

    private void addStudentToCourseById(Long courseId, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        addCourseStudent(courseId, student.getId());
    }

    private void addStudentToCourseByName(Long courseId, String[] nameParts) {
        Student student = studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(nameParts[0], nameParts[nameParts.length - 1])
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        addCourseStudent(courseId, student.getId());
    }

    private Optional<Teacher> findTeacherById(Long id) {
        return teacherRepository.findById(id);

    }

    private Set<Student> findStudentsByIds(Set<Long> studentIds) {
        return new HashSet<>(studentRepository.findAllById(studentIds));
    }

    private boolean isSchoolYearMatch(int courseYear, int studentYear) {
        return courseYear == studentYear;
    }


}
