package eduhogwarts.hogwartsadmin.dto;

import eduhogwarts.hogwartsadmin.models.Teacher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CourseDTO (
    Long id,
    @NotBlank(message = "Subject is required")
    String subject,
    @NotNull(message = "School year is required")
    int schoolYear,
    @NotNull(message = "Current is required")
    boolean current,
    TeacherDTO teacher,
    Set<StudentDTO> students
    ) {}