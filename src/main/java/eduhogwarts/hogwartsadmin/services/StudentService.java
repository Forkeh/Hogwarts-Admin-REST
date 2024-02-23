package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.dto.StudentDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import eduhogwarts.hogwartsadmin.utils.DTOMapper;
import eduhogwarts.hogwartsadmin.utils.Utilities;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final Utilities utilities;
    private final DTOMapper DTOMapper;


    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository, Utilities utilities, DTOMapper DTOMapper) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.utilities = utilities;
        this.DTOMapper = DTOMapper;
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(DTOMapper::studentModelToDTO).
                toList();

    }

    public StudentDTO getStudent(Long id) {
        return studentRepository.findById(id).
                map(DTOMapper::studentModelToDTO).
                orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public StudentDTO createStudent(StudentDTO student) {
        House house = utilities.getHouseFromString(student.house()).
                orElseThrow(() -> new RuntimeException("House not found"));

        Student newStudent = new Student(student.name(), student.dateOfBirth(), house, student.prefect(), student.enrollmentYear(), student.graduationYear(), student.graduated(), student.schoolYear());

        studentRepository.save(newStudent);
        return DTOMapper.studentModelToDTO(newStudent);
    }

    public StudentDTO updateStudent(Long id, StudentDTO student) {
        Student original = studentRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Student not found"));

        House house = utilities.getHouseFromString(student.house()).
                orElseThrow(() -> new RuntimeException("House not found"));

        // Update the student fields
        Student updatedStudent = updateStudentFields(student, original, house);

        // Save and return updated student
        studentRepository.save(updatedStudent);
        return DTOMapper.studentModelToDTO(updatedStudent);
    }

    private static Student updateStudentFields(StudentDTO student, Student originalStudent, House house) {

        originalStudent.setFullName(student.name());
        originalStudent.setDateOfBirth(student.dateOfBirth());
        originalStudent.setHouse(house);
        originalStudent.setPrefect(student.prefect());
        originalStudent.setEnrollmentYear(student.enrollmentYear());
        originalStudent.setGraduationYear(student.graduationYear());
        originalStudent.setGraduated(student.graduated());
        originalStudent.setSchoolYear(student.schoolYear());
        return originalStudent;
    }

    // TODO: Use cascade on entity when deleting?
    public StudentDTO deleteStudent(Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) throw new RuntimeException("Student not found with id: " + id);

        Student studentToDelete = student.get();
        // Find all courses that the student is enrolled in
        List<Course> courses = courseRepository.findByStudentsId(id);

        // Remove the student from all courses
        for (Course course : courses) {
            course.getStudents()
                    .remove(studentToDelete);
            courseRepository.save(course);
        }

        // Delete the student
        studentRepository.deleteById(id);
        return DTOMapper.studentModelToDTO(studentToDelete);

    }

    public StudentDTO patchStudentFields(Long id, Map<String, Object> fields) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isEmpty()) throw new RuntimeException("Student not found with id: " + id);

        // Loop through all fields
        fields.forEach((key, value) -> {
            // Use reflection to get field from student and set it to the new value
            Field field = ReflectionUtils.findField(Student.class, key);

            if (field != null) {
                // If the field is house, get the house from the string
                if (key.equals("house")) value = utilities.getHouseFromString((String) value);

                // If the field is graduationYear, set the student to graduated
                if (key.equals("graduationYear")) student.get()
                        .setGraduated(true);

                // Set the field to accessible, set the value, and set the field back to not accessible
                field.setAccessible(true);
                ReflectionUtils.setField(field, student.get(), value);
                field.setAccessible(false);
            }
        });

        studentRepository.save(student.get());
        return DTOMapper.studentModelToDTO(student.get());
    }
}

