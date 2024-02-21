package eduhogwarts.hogwartsadmin.utils;

import eduhogwarts.hogwartsadmin.models.House;
import eduhogwarts.hogwartsadmin.repositories.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utilities {

    @Autowired
    HouseRepository houseRepository;

    public House getHouseFromString(String houseName) {
        return houseRepository.findByNameContainingIgnoreCase(houseName);
    }

    public String[] nameSplitter(String name) {
        return name.trim().split(" ");
    }

}
