package eduhogwarts.hogwartsadmin.dto;

import eduhogwarts.hogwartsadmin.models.EmpType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record TeacherDTO (

    Long id,
    @NotBlank(message = "Name is required")
    String name,
    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth,
    @NotBlank(message = "House is required")
    String house,
    boolean headOfHouse,
    EmpType employment,
    LocalDate employmentStart,
    LocalDate employmentEnd
    ) {}
