import databases.Database;
import databases.DatabaseFiles;
import databases.DatabaseMySQL;
import logs.Log;

import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Log log = new Log();
        View view = new View();

        Database db;
        if (getConfigParam("dbtype").equals("mysql")) {

            int mysql_port = 3306;
            try {
                mysql_port = Integer.parseInt(getConfigParam("mysql_port"));
            } catch (NumberFormatException ignored) {

            }

            db = new DatabaseMySQL(log, "Shelter", getConfigParam("mysql_user"), getConfigParam("mysql_pass"), mysql_port, getConfigParam("mysql_host"));
        } else {
            db = new DatabaseFiles(log);
        }

        if (db.getInitOk()) {
            Shelter shelter = new Shelter(view, log, db);
            shelter.start();
        } else {
            view.showMessage("Ошибка инициализации базы данных!");
        }
    }

    private static ArrayList<String[]> readConfig() {
        ArrayList<String[]> params = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.replace("\n", "").split("=", -1);
                params.add(arrLine);
            }
        } catch (IOException ignored) {

        }

        return params;
    }

    private static String getConfigParam(String param) {
        ArrayList<String[]> config = readConfig();

        for (String[] tmpArr : config) {
            if (tmpArr.length == 2 && tmpArr[0].equals(param)) {
                return tmpArr[1];
            }
        }

        return "";
    }
}

