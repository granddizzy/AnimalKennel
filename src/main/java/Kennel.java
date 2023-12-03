import abstractAnimals.Animal;
import animals.*;

import java.security.interfaces.DSAKey;
import java.util.ArrayList;

public class Kennel {
    private int lastAnimalId = 0;
    private View view;
    private Log log;

    private Database db;

    private ArrayList<Animal> animals;

    public Kennel(View view, Log log, Database db) {
        this.log = log;
        this.view = view;
        this.db = db;
        this.animals = new ArrayList<>();
    }

    public void start() {
        String choice = "";
        while (!choice.equals("0")) {
            view.showMainMenu();
            choice = view.inputParameter("Выберите пункт меню");

            switch (choice) {
                case "1":
                    view.showAnimalList(db.getAnimalsList());
                    break;
                case "2":
                    String type = view.inputParameter("Введите тип животного");
                    String name = view.inputParameter("Введите имя животного");
                    String birthyear = view.inputParameter("Введите год рождения животного");
                    String birthmonth = view.inputParameter("Введите месяц рождения животного");
                    String birthday = view.inputParameter("Введите день рождения животного");

                    Animal animal = createNewAnimal(type, name, Integer.parseInt(birthyear), Integer.parseInt(birthmonth), Integer.parseInt(birthday));

                    db.addAnimal(animal);
                    break;
                case "3":
                    break;
                case "4":
                    break;
            }
        }
    }

    public int getNewAnimalId() {
        lastAnimalId = lastAnimalId + 1;
        return lastAnimalId;
    }

    public ArrayList<Animal> getAnimals() {
        return db.getAnimalsList();
    }

    public void addAnimalSkill() {

    }

    public void delAnimalSkill() {

    }

    private Animal createNewAnimal(String type, String name, int birthyear, int birthmonth, int birthday) {
        Animal animal = null;

        switch (type) {
            case "Dog" -> {
                animal = new Dog(name, birthday, birthmonth, birthyear);
            }
            case "Cat" -> {
                animal = new Cat(name, birthday, birthmonth, birthyear);
            }
            case "Hamster" -> {
                animal = new Hamster(name, birthday, birthmonth, birthyear);
            }
            case "Camel" -> {
                animal = new Camel(name, birthday, birthmonth, birthyear);
            }
            case "Hourse" -> {
                animal = new Horse(name, birthday, birthmonth, birthyear);
            }
            case "Donkey" -> {
                animal = new Donkey(name, birthday, birthmonth, birthyear);
            }
        }

        return animal;
    }
}
