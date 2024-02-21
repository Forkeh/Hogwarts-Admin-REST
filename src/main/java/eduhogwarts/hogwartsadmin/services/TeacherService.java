package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.dto.TeacherDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.EmpType;
import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import eduhogwarts.hogwartsadmin.repositories.TeacherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final HouseRepository houseRepository;

    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository, HouseRepository houseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.houseRepository = houseRepository;
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().
                stream().map(this::fromModelToDTO).
                collect(Collectors.toList());
    }

    public TeacherDTO getTeacherById(Long id) {
        return teacherRepository.findById(id).
                map(this::fromModelToDTO).
                orElse(null);
    }

    public TeacherDTO createTeacher(TeacherDTO teacher) {
        House house = houseRepository.findByNameContainingIgnoreCase(teacher.getHouse());

        if (house == null) return null;

        Teacher newTeacher = new Teacher(teacher.getFirstName(), teacher.getMiddleName(), teacher.getLastName(), teacher.getDateOfBirth(), house, teacher.isHeadOfHouse(), teacher.getEmployment(), teacher.getEmploymentStart(), teacher.getEmploymentEnd());
        teacherRepository.save(newTeacher);
        return fromModelToDTO(newTeacher);
    }

    public TeacherDTO updateTeacher(Long id, TeacherDTO updatedTeacher) {
        Teacher original = teacherRepository.findById(id).orElse(null);
        House house = houseRepository.findByNameContainingIgnoreCase(updatedTeacher.getHouse());

        if (original != null && house != null) {
            BeanUtils.copyProperties(updatedTeacher, original, "id", "house");
            original.setHouse(house);
            teacherRepository.save(original);
            return fromModelToDTO(original);
        } else {
            return null;
        }
    }

    public TeacherDTO deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher != null) {
            List<Course> courses = courseRepository.findAll();

            for (Course course : courses) {
                if (course.getTeacher() != null && course.getTeacher().getId().equals(id)) {
                    course.setTeacher(null);
                    courseRepository.save(course);
                }
            }
            teacherRepository.delete(teacher);
            return fromModelToDTO(teacher);
        } else {
            return null;
        }
    }

    public TeacherDTO patchTeacherHeadOfHouse(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);

        if (teacher != null) {
            // toggle head of house
            teacher.setHeadOfHouse(!teacher.isHeadOfHouse());
            teacherRepository.save(teacher);
            return fromModelToDTO(teacher);
        } else {
            return null;
        }
    }

    public TeacherDTO patchTeacherEmploymentEnd(Long id, Map<String, LocalDate> employmentEnd) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);

        if (teacher != null) {
            teacher.setEmploymentEnd(employmentEnd.get("employmentEnd"));
            teacherRepository.save(teacher);
            return fromModelToDTO(teacher);
        } else {
            return null;
        }
    }

    public TeacherDTO patchTeacherEmployment(Long id, Map<String, EmpType> employment) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);

        if (teacher != null) {
            teacher.setEmployment(employment.get("employment"));
            teacherRepository.save(teacher);
            return fromModelToDTO(teacher);
        } else {
            return null;
        }
    }

    private TeacherDTO fromModelToDTO(Teacher teacher) {
        return new TeacherDTO(teacher.getId(), teacher.getFirstName(), teacher.getMiddleName(), teacher.getLastName(), teacher.getDateOfBirth(), teacher.getHouse().getName(), teacher.isHeadOfHouse(), teacher.getEmployment(), teacher.getEmploymentStart(), teacher.getEmploymentEnd());
    }

}
