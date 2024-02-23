package eduhogwarts.hogwartsadmin.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record StudentDTO (
    Long id,
    @NotBlank(message = "Name is required")
    String name,
    @Past(message = "Date of birth must be in the past")
    LocalDate dateOfBirth,
    @NotBlank(message = "House is required")
    String house,
    boolean prefect,
    int enrollmentYear,
    int graduationYear,
    boolean graduated,
    @Min(1)
    @Max(7)
    int schoolYear
    ) {}