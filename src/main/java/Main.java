import databases.Database;
import databases.DatabaseFiles;
import databases.DatabaseMySQL;
import logs.Log;

public class Main {
    public static void main(String[] args) {
        Log log = new Log();
        View view = new View();

        //Database db = new DatabaseFiles(log);
        Database db = new DatabaseMySQL(log, "Shelter", "root", "ak74sx33", 3306,"localhost");

        if (db.getInitOk()) {
            Shelter shelter = new Shelter(view, log, db);
            shelter.start();
        } else {
            view.showMessage("Ошибка инициализации базы данных!");
        }
    }
}
