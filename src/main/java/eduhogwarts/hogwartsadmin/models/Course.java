package eduhogwarts.hogwartsadmin.models;


import jakarta.persistence.*;

import java.util.List;

@Entity(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String subject;
    private int schoolYear;
    private boolean current;
    @ManyToOne
    private Teacher teacher;
    @ManyToMany
    private List<Student> students;

    public Course() {
    }

    public Course(String subject, int schoolYear, boolean current, Teacher teacher, List<Student> students) {
        this.subject = subject;
        this.schoolYear = schoolYear;
        this.current = current;
        this.teacher = teacher;
        this.students = students;
    }

    public Course(String subject, int schoolYear, boolean current, List<Student> students) {
        this(subject, schoolYear, current, null, students);
    }

    public Course(String subject, int schoolYear, boolean current, Teacher teacher) {
        this(subject, schoolYear, current, teacher, null);
    }

    public Course(String subject, int schoolYear, boolean current) {
        this(subject, schoolYear, current, null, null);
    }

    public int getId() {
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", schoolYear=" + schoolYear +
                ", current=" + current +
                ", teacher=" + teacher +
                ", students=" + students +
                '}';
    }
}

