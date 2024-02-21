package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.dto.StudentDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import eduhogwarts.hogwartsadmin.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final HouseRepository houseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository, HouseRepository houseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.houseRepository = houseRepository;
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

    public StudentDTO createStudent(StudentDTO student) {
        House house = getHouseFromString(student.getHouse());

        if (house == null) return null;

        Student newStudent;

        if (student.getFullName() != null) {
            newStudent = new Student(student.getFullName(), student.getDateOfBirth(), house, student.isPrefect(), student.getEnrollmentYear(), student.getEnrollmentYear(), student.isGraduated(), student.getSchoolYear());
        } else {
            newStudent = new Student(student.getFirstName(), student.getMiddleName(), student.getLastName(), student.getDateOfBirth(), house, student.isPrefect(), student.getEnrollmentYear(), student.getEnrollmentYear(), student.isGraduated(), student.getSchoolYear());
        }
        studentRepository.save(newStudent);
        return fromModelToDTO(newStudent);

    }

    public StudentDTO updateStudent(Long id, StudentDTO student) {
        Optional<Student> original = studentRepository.findById(id);
        House house = getHouseFromString(student.getHouse());

        if (original.isPresent() && house != null) {
            Student originalStudent = original.get();

            //Update original student
            originalStudent.setFirstName(student.getFirstName());
            originalStudent.setMiddleName(student.getMiddleName());
            originalStudent.setLastName(student.getLastName());
            originalStudent.setDateOfBirth(student.getDateOfBirth());
            originalStudent.setHouse(house);
            originalStudent.setPrefect(student.isPrefect());
            originalStudent.setEnrollmentYear(student.getEnrollmentYear());
            originalStudent.setGraduationYear(student.getGraduationYear());
            originalStudent.setGraduated(student.isGraduated());
            originalStudent.setSchoolYear(student.getSchoolYear());


            // Save and return updated student
            studentRepository.save(originalStudent);
            return fromModelToDTO(originalStudent);
        } else {
            return null;
        }
    }

    public StudentDTO deleteStudent(Long id) {
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
            return fromModelToDTO(studentToDelete);
        } else {
            return null;
        }
    }

    public StudentDTO patchStudentFields(Long id, Map<String, Object> fields) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            // Loop through all fields
            fields.forEach((key, value) -> {
                // Use reflection to get field from student and set it to the new value
                Field field = ReflectionUtils.findField(Student.class, key);

                if (field != null) {
                    // If the field is house, get the house from the string
                    if (key.equals("house")) value = getHouseFromString((String) value);

                    // Set the field to accessible, set the value, and set the field back to not accessible
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, student.get(), value);
                    field.setAccessible(false);
                }
            });
            studentRepository.save(student.get());
            return fromModelToDTO(student.get());
        } else {
            return null;
        }
    }

    private StudentDTO fromModelToDTO(Student student) {
        return new StudentDTO(student.getId(), student.getFirstName(), student.getMiddleName(), student.getLastName(), student.getFullName(), student.getDateOfBirth(), student.getHouse().getName(), student.isPrefect(), student.getEnrollmentYear(), student.getGraduationYear(), student.isGraduated(), student.getSchoolYear());
    }

    private House getHouseFromString(String houseName) {
        return houseRepository.findByNameContainingIgnoreCase(houseName);
    }

}
