package databases;

import abstractAnimals.Animal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DatabaseInterface {
    void addAnimal(Animal animal) throws SQLException, IOException;

    void delAnimal(int id) throws SQLException, IOException;

    Animal getAnimal(int id) throws SQLException, IOException;

    void updateAnimal(Animal animal) throws SQLException, IOException;

    ArrayList<Animal> getAnimalsList() throws SQLException, IOException;
}
