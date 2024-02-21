package eduhogwarts.hogwartsadmin.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate dateOfBirth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "name")
    private House house;
    private boolean prefect;
    private int enrollmentYear;
    private int graduationYear;
    private boolean graduated;
    private int schoolYear;

    // Constructors
    public Student() {
    }

    public Student(String firstName, String middleName, String lastName, LocalDate dateOfBirth, House house, boolean prefect, int enrollmentYear, int graduationYear, boolean graduated, int schoolYear) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.house = house;
        this.prefect = prefect;
        this.enrollmentYear = enrollmentYear;
        this.graduationYear = graduationYear;
        this.graduated = graduated;
        this.schoolYear = schoolYear;
    }

    public Student(String firstName, String lastName, LocalDate dateOfBirth, House house, boolean prefect, int enrollmentYear, int graduationYear, boolean graduated, int schoolYear) {
        this(firstName, null, lastName, dateOfBirth, house, prefect, enrollmentYear, graduationYear, graduated, schoolYear);
    }

    public Student(String fullName, LocalDate dateOfBirth, House house, boolean prefect, int enrollmentYear, int graduationYear, boolean graduated, int schoolYear) {
        this(null, null, null, dateOfBirth, house, prefect, enrollmentYear, graduationYear, graduated, schoolYear);
        this.setFullName(fullName);
    }

    // Getters and Setters
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

    public void setFullName(String fullName) {
        int firstSpace = fullName.indexOf(" ");
        int lastSpace = fullName.lastIndexOf(" ");

        String firstName = fullName.substring(0, firstSpace);
        String lastName = fullName.substring(lastSpace + 1);

        if (firstSpace != lastSpace) {
            String middleName = fullName.substring(firstSpace + 1, lastSpace);
            setMiddleName(middleName);
        }

        setFirstName(firstName);
        setLastName(lastName);
    }

    public String getFullName() {
        return this.getMiddleName() != null ?
                getFirstName() + " " + getMiddleName() + " " + getMiddleName() :
                getFirstName() + " " + getLastName();
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", house=" + house +
                ", prefect=" + prefect +
                ", enrollmentYear=" + enrollmentYear +
                ", graduationYear=" + graduationYear +
                ", graduated=" + graduated +
                '}';
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }
}
