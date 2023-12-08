package databases;

import abstractAnimals.Animal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Database implements DatabaseInterface, AutoCloseable {
    private boolean resourceUsed = false;

    @Override
    public void addAnimal(Animal animal) throws SQLException, IOException {

    }

    @Override
    public void delAnimal(int id) throws SQLException, IOException {

    }

    @Override
    public Animal getAnimal(int id) throws SQLException, IOException {
        return null;
    }

    @Override
    public void updateAnimal(Animal animal) throws SQLException, IOException {

    }

    @Override
    public ArrayList<Animal> getAnimalsList() throws SQLException, IOException {
        return null;
    }

    public void useResource() {
        resourceUsed = true;
    }

    @Override
    public void close() throws IllegalStateException {
        if (!resourceUsed) {
            throw new IllegalStateException("Resource not used in try-with-resources block");
        }
        resourceUsed = false;
    }
}
