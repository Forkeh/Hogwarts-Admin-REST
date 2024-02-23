package eduhogwarts.hogwartsadmin.utils;

import eduhogwarts.hogwartsadmin.dto.CourseDTO;
import eduhogwarts.hogwartsadmin.dto.StudentDTO;
import eduhogwarts.hogwartsadmin.dto.TeacherDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.Student;
import eduhogwarts.hogwartsadmin.models.Teacher;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DTOMapper {

    public StudentDTO studentModelToDTO(Student student) {
        return new StudentDTO(student.getId(), student.getFullName(), student.getDateOfBirth(), student.getHouse().getName(), student.isPrefect(), student.getEnrollmentYear(), student.getGraduationYear(), student.isGraduated(), student.getSchoolYear());
    }

    public TeacherDTO teacherModelToDTO(Teacher teacher) {
        return new TeacherDTO(teacher.getId(), teacher.getFullName(), teacher.getDateOfBirth(), teacher.getHouse().getName(), teacher.isHeadOfHouse(), teacher.getEmployment(), teacher.getEmploymentStart(), teacher.getEmploymentEnd());
    }

    public Set<StudentDTO> getStudentDTOS(Set<Student> students) {
        return students.
                stream().
                map(this::studentModelToDTO).
                collect(Collectors.toSet());
    }

    public CourseDTO courseModelToDTO(Course course) {
        Teacher teacher = course.getTeacher();
        if (teacher != null) {
            return new CourseDTO(course.getId(), course.getSubject(), course.getSchoolYear(), course.isCurrent(), teacherModelToDTO(course.getTeacher()), getStudentDTOS(course.getStudents()));
        }
        return new CourseDTO(course.getId(), course.getSubject(), course.getSchoolYear(), course.isCurrent(), null, getStudentDTOS(course.getStudents()));
    }
}
