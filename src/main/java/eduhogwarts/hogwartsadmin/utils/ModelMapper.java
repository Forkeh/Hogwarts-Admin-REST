package eduhogwarts.hogwartsadmin.utils;

import eduhogwarts.hogwartsadmin.dto.StudentDTO;
import eduhogwarts.hogwartsadmin.dto.TeacherDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.models.Teacher;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ModelMapper {

    public StudentDTO studentModelToDTO(Student student) {
        return new StudentDTO(student.getId(), student.getFullName(), student.getDateOfBirth(), student.getHouse().getName(), student.isPrefect(), student.getEnrollmentYear(), student.getGraduationYear(), student.isGraduated(), student.getSchoolYear());
    }

    public TeacherDTO teacherModelToDTO(Teacher teacher) {
        return new TeacherDTO(teacher.getId(), teacher.getFirstName(), teacher.getMiddleName(), teacher.getLastName(), teacher.getDateOfBirth(), teacher.getHouse().getName(), teacher.isHeadOfHouse(), teacher.getEmployment(), teacher.getEmploymentStart(), teacher.getEmploymentEnd());
    }

    public Set<StudentDTO> getStudentDTOS(Set<Student> students) {
        return students.
                stream().
                map(this::studentModelToDTO).
                collect(Collectors.toSet());
    }
}
