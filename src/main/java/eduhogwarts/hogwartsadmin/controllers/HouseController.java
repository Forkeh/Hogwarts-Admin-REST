package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/houses")
public class HouseController {

    private final HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @GetMapping
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<House> getHouseByName(@PathVariable String name) throws Exception {
        try {
            House house = houseRepository.findByNameContainingIgnoreCase(name);
            if (house != null) {
                return ResponseEntity.ok(house);
            } else {
                return ResponseEntity.notFound().build();
            }


        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
}
