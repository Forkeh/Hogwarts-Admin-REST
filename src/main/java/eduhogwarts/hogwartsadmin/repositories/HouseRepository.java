package eduhogwarts.hogwartsadmin.repositories;


import eduhogwarts.hogwartsadmin.models.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Integer> {
}

