package eduhogwarts.hogwartsadmin.repositories;

import eduhogwarts.hogwartsadmin.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
