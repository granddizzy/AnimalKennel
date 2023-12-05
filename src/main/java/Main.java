import logs.Log;

public class Main {
    public static void main(String[] args) {
        Log log = new Log();
        View view = new View();
        Database db = new Database(log);

        if (db.getInitOk()) {
            Shelter shelter = new Shelter(view, log, db);
            shelter.start();
        } else {
            view.showMessage("Ошибка инициализации базы данных!");
        }
    }
}
