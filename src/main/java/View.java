import abstractAnimals.Animal;

import java.util.ArrayList;
import java.util.Scanner;

import java.time.Year;

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

    public String inputString(String query) {
        System.out.print(query + ": ");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println();
        return str;
    }

    public int inputYear(String query) {
        int year = 2000;
        int currentYear = Year.now().getValue();

        boolean flag = true;
        while (flag) {
            flag = false;
            year = inputNumber(query);

            if (year > currentYear) {
                showMessage("Год больше текущего!");
                flag = true;
            }

            if (currentYear - year > 100) {
                showMessage("К сожалению столько не живут!");
                flag = true;
            }
        }

        return year;
    }

    public int inputMonth(String query) {
        int month = 1;
        boolean flag = true;
        while (flag) {
            flag = false;
            month = inputNumber(query);

            if (month < 1 || month > 12) {
                showMessage("Месяц должен быть 1 - 12");
                flag = true;
            }
        }

        return month;
    }

    public int inputDay(String query) {
        int day = 1;
        boolean flag = true;
        while (flag) {
            flag = false;
            day = inputNumber(query);

            if (day < 1 || day > 31) {
                showMessage("День должен быть 1 - 31");
                flag = true;
            }
        }

        return day;
    }

    public int inputNumber(String query) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(query + ": ");
            String str = scanner.nextLine();
            System.out.println();

            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException ignored) {

            }
        }
    }

    public void showAnimalList(ArrayList<Animal> animals) {
        if (animals == null || animals.size() == 0) {
            System.out.println("В приюте нет ни одного животного.");
            System.out.println();
        } else {
            System.out.println("Список животных в приюте:");
            for (Animal animal : animals) {
                if (animal != null) System.out.println(animal);
            }
            System.out.println();
        }
    }

    public String selectAnimalType() {
        String choice = "";
        while (true) {
            showAnimalTypes();
            choice = inputString("Выберите тип животного" +
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
        System.out.println("id: " + animal.getId());
        System.out.println("Животное: " + animal.getTypeName());
        System.out.println("Имя: " + animal.getName());
        System.out.println("Дата рождения: " + Shelter.formatDate(animal.getBirthday(), animal.getBirthmonth(), animal.getBirthyear()));
        System.out.println("Возраст: " + Animal.calcAge(animal.getBirthday(), animal.getBirthmonth(), animal.getBirthyear()));
        System.out.println();
        showSkills(animal);
    }

    void showSkills(Animal animal) {
        System.out.println("Навыки:");

        if (animal.getSkills().size() == 0) {
            System.out.println("Навыков еще нет.");
        } else {
            int i = 1;
            for (String skill : animal.getSkills()) {
                System.out.println("" + i + "." + skill);
                i++;
            }
        }
        System.out.println();
    }
}
