import abstractAnimals.Animal;

import java.util.ArrayList;

public interface DatabaseInterface {
    void addAnimal(Animal animal);

    void delAnimal(Animal animal);

    void updateAnimal(Animal animal);

    ArrayList<Animal> getAnimalsList();

    Integer getLastID();

    boolean getInitOk();
}
