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

//    public StudentDTO() {
//    }
//
//    public StudentDTO(Long id, String name, LocalDate dateOfBirth, String house, boolean prefect, int enrollmentYear, int graduationYear, boolean graduated, int schoolYear) {
//        this.id = id;
//        this.name = name;
//        this.dateOfBirth = dateOfBirth;
//        this.house = house;
//        this.prefect = prefect;
//        this.enrollmentYear = enrollmentYear;
//        this.graduationYear = graduationYear;
//        this.graduated = graduated;
//        this.schoolYear = schoolYear;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public LocalDate getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(LocalDate dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }
//
//    public String getHouse() {
//        return house;
//    }
//
//    public void setHouse(String house) {
//        this.house = house;
//    }
//
//    public boolean isPrefect() {
//        return prefect;
//    }
//
//    public void setPrefect(boolean prefect) {
//        this.prefect = prefect;
//    }
//
//    public int getEnrollmentYear() {
//        return enrollmentYear;
//    }
//
//    public void setEnrollmentYear(int enrollmentYear) {
//        this.enrollmentYear = enrollmentYear;
//    }
//
//    public int getGraduationYear() {
//        return graduationYear;
//    }
//
//    public void setGraduationYear(int graduationYear) {
//        this.graduationYear = graduationYear;
//    }
//
//    public boolean isGraduated() {
//        return graduated;
//    }
//
//    public void setGraduated(boolean graduated) {
//        this.graduated = graduated;
//    }
//
//    public int getSchoolYear() {
//        return schoolYear;
//    }
//
//    public void setSchoolYear(int schoolYear) {
//        this.schoolYear = schoolYear;
//    }
//}
