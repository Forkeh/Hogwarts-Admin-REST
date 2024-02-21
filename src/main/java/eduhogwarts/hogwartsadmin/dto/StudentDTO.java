package eduhogwarts.hogwartsadmin.dto;

import java.time.LocalDate;

public class StudentDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private LocalDate dateOfBirth;
    private String house;
    private boolean prefect;
    private int enrollmentYear;
    private int graduationYear;
    private boolean graduated;
    private int schoolYear;

    public StudentDTO() {
    }

    public StudentDTO(Long id, String firstName, String middleName, String lastName, String fullName, LocalDate dateOfBirth, String house, boolean prefect, int enrollmentYear, int graduationYear, boolean graduated, int schoolYear) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.house = house;
        this.prefect = prefect;
        this.enrollmentYear = enrollmentYear;
        this.graduationYear = graduationYear;
        this.graduated = graduated;
        this.schoolYear = schoolYear;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public boolean isPrefect() {
        return prefect;
    }

    public void setPrefect(boolean prefect) {
        this.prefect = prefect;
    }

    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public boolean isGraduated() {
        return graduated;
    }

    public void setGraduated(boolean graduated) {
        this.graduated = graduated;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }
}
