import abstractAnimals.Animal;

import java.util.ArrayList;

public class Kennel {
    private int lastAnimalId = 0;
    private View view;
    private Log log;

    private ArrayList<Animal> animals;

    public Kennel(View view, Log log) {
        this.log = log;
        this.view = view;
        this.animals = new ArrayList<>();
    }

    public void start() {
        String choice = "";
        while (!choice.equals("0")) {
            view.showMainMenu();
            choice = view.inputParameter("Выберите пункт меню: ");

            switch (choice) {
                case "1":
                    break;
                case "2":
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
        return animals;
    }

    public void addAnimalSkill() {

    }

    public void delAnimalSkill() {

    }
}
