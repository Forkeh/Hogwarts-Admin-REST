package eduhogwarts.hogwartsadmin.repositories;


import eduhogwarts.hogwartsadmin.models.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, String> {
    House findByNameContainingIgnoreCase(String name);
}

