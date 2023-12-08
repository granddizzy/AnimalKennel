import abstractAnimals.Animal;
import animals.*;
import databases.Database;
import logs.Log;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Shelter implements ShelterInterface {
    private final View view;
    private Log log;
    private final Database db;

    public Shelter(View view, Log log, Database db) {
        this.log = log;
        this.view = view;
        this.db = db;
    }

    public void start() {
        int choice = -1;
        while (choice != 0) {
            view.showMainMenu();
            choice = view.inputNumber("Выберите пункт меню");

            switch (choice) {
                case 0:
                    view.showMessage("До новых встреч!");
                    break;
                case 1:
                    view.showAnimalList(getAnimals());
                    break;
                case 2:
                    String type = view.selectAnimalType();
                    String name = view.inputString("Введите имя животного");
                    int birthyear = view.inputYear("Введите год рождения животного");
                    int birthmonth = view.inputMonth("Введите номер месяца рождения животного (1-12)");
                    int birthday = view.inputDay("Введите день рождения животного");

                    if (!checkDate(birthday, birthmonth, birthyear)) {
                        view.showMessage("Такой даты не существует.");
                        break;
                    }

                    addAnimal(createNewAnimal(type, name, birthyear, birthmonth, birthday));
                    view.showAnimalList(getAnimals());
                    break;
                case 3:
                    view.showAnimalList(getAnimals());

                    int id = view.inputNumber("Введите ID животного для удаления (0 - отмена)");

                    if (id == 0) break;

                    Animal delAnimal = getAnimal(id);

                    if (delAnimal != null) {
                        delAnimal(id);
                        view.showMessage("Удалено животное - " + delAnimal);
                        view.showAnimalList(getAnimals());
                    } else {
                        view.showMessage("Не найдено животное с ID:" + id);
                    }

                    break;
                case 4:
                    view.showAnimalList(getAnimals());

                    int curr_id = view.inputNumber("Введите ID животного для просмотра данных (0 - отмена)");
                    if (curr_id == 0) break;

                    Animal currentAnimal = getAnimal(curr_id);
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
                                    updateAnimal(currentAnimal);
                                    break;
                                case 2:
                                    view.showAnimalParams(currentAnimal);
                                    int id_skill = view.inputNumber("Введите ID умения для удаления (0 - отмена)");

                                    if (id_skill == 0) break;

                                    currentAnimal.delAnimalSkill(id_skill - 1);

                                    try {
                                        db.updateAnimal(currentAnimal);
                                    } catch (Exception e) {
                                        log.append(e.getMessage());
                                    }

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
        try {
            return db.getAnimalsList();
        } catch (Exception e) {
            log.append(e.getMessage());
            view.showMessage(e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void addAnimal(Animal animal) {
        try {
            db.addAnimal(animal);
        } catch (Exception e) {
            log.append(e.getMessage());
            view.showMessage(e.getMessage());
        }
    }

    @Override
    public void delAnimal(int id) {
        try {
            db.delAnimal(id);
        } catch (Exception e) {
            log.append(e.getMessage());
            view.showMessage(e.getMessage());
        }
    }

    @Override
    public Animal getAnimal(int id) {
        Animal animal = null;
        try {
            animal = db.getAnimal(id);
        } catch (Exception e) {
            log.append(e.getMessage());
            view.showMessage(e.getMessage());
        }
        return animal;
    }

    @Override
    public void updateAnimal(Animal animal) {
        try {
            db.updateAnimal(animal);
        } catch (Exception e) {
            log.append(e.getMessage());
            view.showMessage(e.getMessage());
        }
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
        StringBuilder sb = new StringBuilder();

        sb.append(year);
        sb.append("-");
        if (month < 10) sb.append("0");
        sb.append(month);
        sb.append("-");
        if (day < 10) sb.append("0");
        sb.append(day);

        return sb.toString();
    }

    public static boolean checkDate(int day, int month, int year) {
        try {
            LocalDate date = LocalDate.parse(formatDate(day, month, year));
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }
}
