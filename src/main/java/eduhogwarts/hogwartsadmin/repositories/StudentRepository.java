package eduhogwarts.hogwartsadmin.repositories;


import eduhogwarts.hogwartsadmin.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}

