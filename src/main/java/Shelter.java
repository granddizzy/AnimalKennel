import abstractAnimals.Animal;
import animals.*;
import logs.Log;

import java.util.ArrayList;

public class Shelter {
    private final View view;
    private Log log;
    private final Database db;

    public Shelter(View view, Log log, Database db) {
        this.log = log;
        this.view = view;
        this.db = db;
    }

    public void start() {
        if (log.getInitOk()) {
            log.clear();
        }

        int choice = -1;
        while (choice != 0) {
            view.showMainMenu();
            choice = view.inputNumber("Выберите пункт меню");

            switch (choice) {
                case 1:
                    view.showAnimalList(db.getAnimalsList());
                    break;
                case 2:
                    String type = view.selectAnimalType();
                    String name = view.inputString("Введите имя животного");
                    int birthyear = view.inputYear("Введите год рождения животного");
                    int birthmonth = view.inputMonth("Введите месяц рождения животного");
                    int birthday = view.inputDay("Введите день рождения животного");

                    if (!checkDate(birthday, birthmonth, birthyear)) {
                        view.showMessage("Такой даты не существует.");
                        break;
                    }

                    Animal animal = createNewAnimal(type, name, birthyear, birthmonth, birthday);
                    db.addAnimal(animal);
                    view.showAnimalList(db.getAnimalsList());
                    break;
                case 3:
                    view.showAnimalList(db.getAnimalsList());
                    int id = view.inputNumber("Введите ID животного для удаления (0 - отмена)");

                    if (id == 0) break;

                    Animal delAnimal = db.getAnimal(id);
                    if (delAnimal != null) {
                        db.delAnimal(id);
                        view.showMessage("Удалено животное - " + delAnimal);
                        view.showAnimalList(db.getAnimalsList());
                    } else {
                        view.showMessage("Не найдено животное с ID:" + id);
                    }

                    break;
                case 4:
                    view.showAnimalList(db.getAnimalsList());
                    int curr_id = view.inputNumber("Введите ID животного для просмотра данных (0 - отмена)");

                    if (curr_id == 0) break;

                    Animal currentAnimal = db.getAnimal(curr_id);

                    if (currentAnimal != null) {
                        int choice2 = -1;
                        while (choice2 != 0) {
                            view.showAnimalParams(currentAnimal);
                            view.showAnimalMenu();
                            choice2 = view.inputNumber("Выберите пункт меню");

                            switch (choice2) {
                                case 1:
                                    String skill = view.inputString("Введите новое умение");
                                    currentAnimal.addAnimalSkill(skill);
                                    db.updateAnimal(currentAnimal);
                                    break;
                                case 2:
                                    view.showAnimalParams(currentAnimal);
                                    int id_skill = view.inputNumber("Введите ID умения для удаления (0 - отмена)");

                                    if (id_skill == 0) break;

                                    currentAnimal.delAnimalSkill(id_skill - 1);
                                    db.updateAnimal(currentAnimal);
                                    view.showMessage("Умение было удалено.");

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

    public static String formatDate(int day, int month, int year) {

        return "" + day + "." + month + "." + year;
    }

    public static boolean checkDate(int day, int month, int year) {
        return true;
    }
}
