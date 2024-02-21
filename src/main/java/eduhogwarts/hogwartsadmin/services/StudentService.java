package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.dto.StudentDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().
                map(this::fromModelToDTO).
                collect(Collectors.toList());
    }

    public StudentDTO getStudent(Long id) {
        return studentRepository.findById(id).
                map(this::fromModelToDTO).
                orElse(null);
    }

    public Student createStudent(Student student) {
        // TODO: Create student where house field is a string?
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        Optional<Student> original = studentRepository.findById(id);

        if (original.isPresent()) {
            Student originalStudent = original.get();

            //Update original student
            originalStudent.setFirstName(student.getFirstName());
            originalStudent.setMiddleName(student.getMiddleName());
            originalStudent.setLastName(student.getLastName());
            originalStudent.setDateOfBirth(student.getDateOfBirth());
            originalStudent.setHouse(student.getHouse());
            originalStudent.setPrefect(student.isPrefect());
            originalStudent.setEnrollmentYear(student.getEnrollmentYear());
            originalStudent.setGraduationYear(student.getGraduationYear());
            originalStudent.setGraduated(student.isGraduated());

            // Save and return updated student
            return studentRepository.save(originalStudent);
        } else {
            return null;
        }
    }

    public Student deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            Student studentToDelete = student.get();
            // Find all courses that the student is enrolled in
            List<Course> courses = courseRepository.findByStudentsId(id);

            // Remove the student from all courses
            for (Course course : courses) {
                course.getStudents().remove(studentToDelete);
                courseRepository.save(course);
            }

            // Delete the student
            studentRepository.deleteById(id);
            return studentToDelete;
        } else {
            return null;
        }
    }

    private StudentDTO fromModelToDTO(Student student) {
        return new StudentDTO(student.getId(), student.getFirstName(), student.getMiddleName(), student.getLastName(), student.getHouse().getName(), student.getSchoolYear());
    }
}

