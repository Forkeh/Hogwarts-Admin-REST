package eduhogwarts.hogwartsadmin.dto;

public class StudentDTO {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String house;
    private int schoolYear;

    public StudentDTO() {
    }

    public StudentDTO(Long id, String firstName, String middleName, String lastName, String house, int schoolYear) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.house = house;
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

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }


    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }
}
