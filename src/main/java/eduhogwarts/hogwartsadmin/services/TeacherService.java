package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.dto.TeacherDTO;
import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.EmpType;
import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import eduhogwarts.hogwartsadmin.repositories.TeacherRepository;
import eduhogwarts.hogwartsadmin.utils.DTOMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// TODO: Return Optional instead?
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final HouseRepository houseRepository;
    private final DTOMapper DTOMapper;

    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository, HouseRepository houseRepository, DTOMapper DTOMapper) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.houseRepository = houseRepository;
        this.DTOMapper = DTOMapper;
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll().
                stream().
                map(DTOMapper::teacherModelToDTO).
                toList();
    }

    public TeacherDTO getTeacherById(Long id) {
        return teacherRepository.findById(id).
                map(DTOMapper::teacherModelToDTO).
                orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
    }

    public TeacherDTO createTeacher(TeacherDTO teacher) {
        House house = houseRepository.findByNameContainingIgnoreCase(teacher.house()).
                orElseThrow(() -> new IllegalArgumentException("House not found"));

        Teacher newTeacher = new Teacher(teacher.name(), teacher.dateOfBirth(), house, teacher.headOfHouse(), teacher.employment(), teacher.employmentStart(), teacher.employmentEnd());

        teacherRepository.save(newTeacher);
        return DTOMapper.teacherModelToDTO(newTeacher);
    }

    public TeacherDTO updateTeacher(Long id, TeacherDTO updatedTeacher) {
        Teacher teacher = teacherRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        House house = houseRepository.findByNameContainingIgnoreCase(updatedTeacher.house()).
                orElseThrow(() -> new IllegalArgumentException("House not found"));

        // Copy all properties except id and house
        BeanUtils.copyProperties(updatedTeacher, teacher, "id", "house", "name");
        // Have to set house and name separately
        teacher.setHouse(house);
        teacher.setFullName(updatedTeacher.name());

        teacherRepository.save(teacher);
        return DTOMapper.teacherModelToDTO(teacher);
    }

    public TeacherDTO deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        List<Course> courses = courseRepository.findAll();

        for (Course course : courses) {
            if (course.getTeacher() != null && course.getTeacher().getId().equals(id)) {
                course.setTeacher(null);
                courseRepository.save(course);
            }
        }
        teacherRepository.delete(teacher);
        return DTOMapper.teacherModelToDTO(teacher);
    }

    public TeacherDTO patchTeacherHeadOfHouse(Long id) {
        Teacher teacher = teacherRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        // toggle head of house
        teacher.setHeadOfHouse(!teacher.isHeadOfHouse());

        teacherRepository.save(teacher);
        return DTOMapper.teacherModelToDTO(teacher);
    }

    public TeacherDTO patchTeacherEmploymentEnd(Long id, Map<String, LocalDate> employmentEnd) {
        Teacher teacher = teacherRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        teacher.setEmploymentEnd(employmentEnd.get("employmentEnd"));

        teacherRepository.save(teacher);
        return DTOMapper.teacherModelToDTO(teacher);

    }

    public TeacherDTO patchTeacherEmployment(Long id, Map<String, EmpType> employment) {
        Teacher teacher = teacherRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Teacher not found"));

        teacher.setEmployment(employment.get("employment"));

        teacherRepository.save(teacher);
        return DTOMapper.teacherModelToDTO(teacher);

    }
}
