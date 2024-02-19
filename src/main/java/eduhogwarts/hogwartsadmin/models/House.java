package eduhogwarts.hogwartsadmin.models;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "house")
public class House {
    @Id
    private String name;
    private String founder;
    private @ElementCollection
    List<String> colors;

    // Constructors
    public House() {
    }

    public House(String name, String founder, List<String> colors) {
        this.name = name;
        this.founder = founder;
        this.colors = colors;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "House{" +
                ", name='" + name + '\'' +
                ", founder='" + founder + '\'' +
                ", colors=" + colors +
                '}';
    }
}
