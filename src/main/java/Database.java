import abstractAnimals.Animal;
import animals.*;

import java.io.*;
import java.util.ArrayList;

public class Database implements DatabaseInterface {

    private final String DBFilePath;
    private boolean initOk;

    public Database() {
        initOk = false;
        DBFilePath = "./DB.txt";
        File file = new File(DBFilePath);

        if (!file.exists()) {
            try {
                boolean res = file.createNewFile();
                initOk = true;
            } catch (IOException ignored) {

            }
        } else {
            initOk = true;
        }
    }

    @Override
    public void addAnimal(Animal animal) {
        try (FileWriter fw = new FileWriter(DBFilePath, true)) {
            fw.write(createDBLine(animal));
        } catch (IOException ignored) {

        }
    }

    @Override
    public void delAnimal(Animal animal) {

    }

    @Override
    public void updateAnimal(Animal animal) {

    }

    @Override
    public ArrayList<Animal> getAnimalsList() {
        ArrayList<Animal> animalList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DBFilePath))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] animalArray = parseDatabaseLine(line.replace("\n", ""));

                Animal animal = createAnimal(animalArray);

                animalList.add((Animal) animal);
            }

        } catch (IOException ignored) {

        }

        return animalList;
    }

    @Override
    public Integer getLastID() {
        return null;
    }

    @Override
    public boolean getInitOk() {
        return initOk;
    }

    private String[] parseDatabaseLine(String line) {
        return line.split(";");

    }

    private Animal createAnimal(String[] animalArray) {
        Animal animal = null;

        int birthday = 0;
        int birthmonth = 0;
        int birthyear = 0;

        ArrayList<String> skills = new ArrayList<>();

        switch (animalArray[1]) {
            case "Dog" -> {
                animal = new Dog(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Cat" -> {
                animal = new Cat(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Hamster" -> {
                animal = new Hamster(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Camel" -> {
                animal = new Camel(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Hourse" -> {
                animal = new Horse(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Donkey" -> {
                animal = new Donkey(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
        }

        return animal;
    }

    private String createDBLine(Animal animal) {
        StringBuilder sb = new StringBuilder();

        //id
        sb.append(animal.getId()).append(";");

        // type
        if (animal instanceof Dog) {
            sb.append("Dog").append(";");
        } else if (animal instanceof Cat) {
            sb.append("Cat").append(";");
        } else if (animal instanceof Hamster) {
            sb.append("Hamster").append(";");
        } else if (animal instanceof Horse) {
            sb.append("Horse").append(";");
        } else if (animal instanceof Camel) {
            sb.append("Camel").append(";");
        } else if (animal instanceof Donkey) {
            sb.append("Donkey").append(";");
        }

        // name
        sb.append(animal.getName()).append(";");

        // date birthday
        sb.append(animal.getBirthyear()).append(".").append(animal.getBirthmonth()).append(".").append(animal.getBirthday()).append("\n");

        return sb.toString();
    }
}
