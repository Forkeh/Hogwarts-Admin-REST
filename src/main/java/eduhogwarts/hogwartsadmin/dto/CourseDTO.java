package eduhogwarts.hogwartsadmin.dto;

import eduhogwarts.hogwartsadmin.models.Teacher;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class CourseDTO {
    private Long id;
    @NotBlank(message = "Subject is required")
    private String subject;
    private int schoolYear;
    private boolean current;
    private TeacherDTO teacher;
    private Set<StudentDTO> students;

    public CourseDTO() {
    }

    public CourseDTO(Long id, String subject, int schoolYear, boolean current, TeacherDTO teacher, Set<StudentDTO> students) {
        this.id = id;
        this.subject = subject;
        this.schoolYear = schoolYear;
        this.current = current;
        this.teacher = teacher;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public boolean isCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }

    public TeacherDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherDTO teacher) {
        this.teacher = teacher;
    }

    public Set<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDTO> students) {
        this.students = students;
    }
}
