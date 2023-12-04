public class Main {
    public static void main(String[] args) {
        Log log = new Log();
        View view = new View();
        Database db = new Database();

        if (db.getInitOk()) {
            Shelter shelter = new Shelter(view, log, db);
            shelter.start();
        } else {
            System.out.println("Ошибка инициализации базы данных!");
        }
    }
}
