import java.util.Scanner;

public class View {
    public void showMainMenu() {
        System.out.println("Главное меню:");
        System.out.println("0.Выход");
        System.out.println("1.Посмотреть список животных");
        System.out.println("2.Добавить животное");
        System.out.println("3.Удалить животное");
        System.out.println("4.Открыть параметры животного");
    }

    public void showAnimalMenu() {
        System.out.println("Меню животного:");
        System.out.println("1.Добавить новое умение (команду)");
        System.out.println("2.Удалить умение (команду)");
    }

    public String inputParameter(String query) {
        System.out.print(query);
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.out.println();
        return str;
    }
}
