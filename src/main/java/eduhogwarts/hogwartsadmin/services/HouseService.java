package eduhogwarts.hogwartsadmin.services;

import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {
    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    public House getHouseByName(String name) {
        return houseRepository.findByNameContainingIgnoreCase(name);
    }


}
