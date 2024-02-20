package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.models.Course;
import eduhogwarts.hogwartsadmin.models.Teacher;
import eduhogwarts.hogwartsadmin.repositories.CourseRepository;
import eduhogwarts.hogwartsadmin.repositories.TeacherRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacher(Long id, Teacher updatedTeacher) {
        Teacher original = teacherRepository.findById(id).orElse(null);

        if (original != null) {
            BeanUtils.copyProperties(updatedTeacher, original, "id");
            return teacherRepository.save(original);

        } else {
            return null;
        }
    }

    public Teacher deleteTeacher(Long id) {
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
            return teacher;
        } else {
            return null;
        }
    }
}
