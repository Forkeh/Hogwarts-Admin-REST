package eduhogwarts.hogwartsadmin.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import eduhogwarts.hogwartsadmin.models.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}

