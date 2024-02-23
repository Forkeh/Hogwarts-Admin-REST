package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.services.HouseService;
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

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public ResponseEntity<List<House>> getAllHouses() {

        List<House> houses = houseService.getAllHouses();

        return ResponseEntity.ok().body(houses);
    }

    @GetMapping("/{name}")
    public ResponseEntity<House> getHouseByName(@PathVariable String name) throws Exception {

        Optional<House> house = houseService.getHouseByName(name);

        return ResponseEntity.of(house);
    }
}
