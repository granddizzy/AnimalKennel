import abstractAnimals.Animal;
import animals.*;

import java.util.ArrayList;

public class Shelter {
    private View view;
    private Log log;
    private Database db;

    public Shelter(View view, Log log, Database db) {
        this.log = log;
        this.view = view;
        this.db = db;
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
                    String type = view.selectAnimalType();
                    String name = view.inputParameter("Введите имя животного");
                    String birthyear = view.inputParameter("Введите год рождения животного");
                    String birthmonth = view.inputParameter("Введите месяц рождения животного");
                    String birthday = view.inputParameter("Введите день рождения животного");

                    Animal animal = createNewAnimal(type, name, Integer.parseInt(birthyear), Integer.parseInt(birthmonth), Integer.parseInt(birthday));

                    db.addAnimal(animal);
                    view.showAnimalList(db.getAnimalsList());
                    break;
                case "3":
                    view.showAnimalList(db.getAnimalsList());
                    String id = view.inputParameter("Введите ID животного для удаления");
                    Animal delAnimal = db.getAnimal(Integer.parseInt(id));
                    if (delAnimal != null) {
                        db.delAnimal(Integer.parseInt(id));
                        view.showAnimalList(db.getAnimalsList());
                        view.showMessage("Удалено животное - " + delAnimal.toString());
                    } else {
                        view.showMessage("Не найдено животное с ID:" + id);
                    }
                    break;
                case "4":
                    view.showAnimalList(db.getAnimalsList());
                    String curr_id = view.inputParameter("Введите ID животного для просмотра данных");

                    Animal currentAnimal = null;

                    try {
                        currentAnimal = db.getAnimal(Integer.parseInt(curr_id));
                    } catch (Exception ignored) {

                    }

                    if (currentAnimal != null) {
                        String choice2 = "";
                        while (!choice2.equals("0")) {
                            view.showAnimalParams(currentAnimal);
                            view.showAnimalMenu();
                            choice2 = view.inputParameter("Выберите пункт меню");

                            switch (choice2) {
                                case "1":
                                    String skill = view.inputParameter("Введите новое умение");
                                    currentAnimal.addAnimalSkill(skill);
                                    db.updateAnimal(currentAnimal);
                                    break;
                                case "2":
                                    view.showAnimalParams(currentAnimal);
                                    String id_skill = view.inputParameter("Введите ID умения для удаления");
                                    try {
                                        currentAnimal.delAnimalSkill(Integer.parseInt(id_skill) - 1);
                                        db.updateAnimal(currentAnimal);
                                        view.showMessage("Умение было удалено.");
                                    } catch (Exception ignored) {

                                    }

                                    break;
                            }
                        }
                    } else {
                        view.showMessage("Не найдено животное с ID:" + curr_id);
                    }
                    break;
            }
        }
    }

    public ArrayList<Animal> getAnimals() {
        return db.getAnimalsList();
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
            case "Horse" -> {
                animal = new Horse(name, birthday, birthmonth, birthyear);
            }
            case "Donkey" -> {
                animal = new Donkey(name, birthday, birthmonth, birthyear);
            }
        }

        return animal;
    }
}
