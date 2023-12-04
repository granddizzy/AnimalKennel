import abstractAnimals.Animal;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    public void showMainMenu() {
        System.out.println("Главное меню:");
        System.out.println("0.Выход");
        System.out.println("1.Посмотреть список животных");
        System.out.println("2.Добавить животное");
        System.out.println("3.Удалить животное");
        System.out.println("4.Открыть параметры животного (Команды)");
    }

    public void showAnimalMenu() {
        System.out.println("Меню животного:");
        System.out.println("0.Выход");
        System.out.println("1.Добавить новое умение (команду)");
        System.out.println("2.Удалить умение (команду)");
    }

    public void showMessage(String msg) {
        System.out.println(msg);
        System.out.println();
    }

    public void showAnimalTypes() {
        System.out.println("Виды животных:");
        System.out.println("1.Кошка");
        System.out.println("2.Собака");
        System.out.println("3.Хомяк");
        System.out.println("4.Лошадь");
        System.out.println("5.Осел");
        System.out.println("6.Верблюд");
    }

    public String inputParameter(String query) {
        System.out.print(query + ": ");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println();
        return str;
    }

    public void showAnimalList(ArrayList<Animal> animals) {
        if (animals.size() == 0) {
            System.out.println("В приюте нет ни одного животного.");
        } else {
            System.out.println("Список животных в приюте:");
            for (Animal animal : animals) {
                System.out.println(animal);
            }
            System.out.println();
        }
    }

    public String selectAnimalType() {
        String choice = "";
        while (true) {
            showAnimalTypes();
            choice = inputParameter("Выберите тип животного" +
                    "");
            switch (choice) {
                case "1":
                    return "Cat";
                case "2":
                    return "Dog";
                case "3":
                    return "Hamster";
                case "4":
                    return "Horse";
                case "5":
                    return "Donkey";
                case "6":
                    return "Camel";
            }
        }
    }

    void showAnimalParams(Animal animal) {
        System.out.println("Параметры: ");
        System.out.println(animal);
        System.out.println();
    }
}
