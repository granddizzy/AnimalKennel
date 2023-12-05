package databases;

import abstractAnimals.Animal;

import java.util.ArrayList;

public interface DatabaseInterface {
    void addAnimal(Animal animal);

    void delAnimal(int id);

    Animal getAnimal(int id);

    void updateAnimal(Animal animal);

    ArrayList<Animal> getAnimalsList();

    boolean getInitOk();

    void disconnect();
}
