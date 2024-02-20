package eduhogwarts.hogwartsadmin.controllers;

import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import eduhogwarts.hogwartsadmin.services.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/houses")
public class HouseController {

    private final HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping
    public List<House> getAllHouses() {
        return houseService.getAllHouses();
    }

    @GetMapping("/{name}")
    public ResponseEntity<House> getHouseByName(@PathVariable String name) throws Exception {
        try {
            House house = houseService.getHouseByName(name);

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
