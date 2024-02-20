package eduhogwarts.hogwartsadmin.repositories;

import eduhogwarts.hogwartsadmin.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByStudentsId(Long id);
}
