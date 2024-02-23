package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseService {
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {

        this.houseRepository = houseRepository;
    }

    public List<House> getAllHouses() {

        return houseRepository.findAll();
    }

    public Optional<House> getHouseByName(String name) {

        return houseRepository.findByNameContainingIgnoreCase(name);
    }

}
