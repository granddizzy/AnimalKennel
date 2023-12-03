public class Main {
    public static void main(String[] args) {
        Log log = new Log();
        View view = new View();
        Database db = new Database();

        if (db.getInitOk()) {
            Kennel kennel = new Kennel(view, log, db);
            kennel.start();
        } else {
            System.out.println("Ошибка инициализации базы данных!");
        }
    }
}
