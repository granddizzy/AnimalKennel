package databases;

import abstractAnimals.Animal;
import logs.Log;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseMySQL extends Database {
    private final String url;
    private final String user;
    private final String password;
    private final Log log;
    private final String database;
    private Connection myConnect;

    private boolean initOk;

    public DatabaseMySQL(Log log, String database, String user, String password, int port, String host) {
        this.log = log;
        this.url = "jdbc:mysql://" + host + ":" + port + "/";
        this.user = user;
        this.password = password;
        this.database = database;

        myConnect = mysqlConnect();

        if (myConnect != null) initDB();

        initOk = myConnect != null;
    }

    private void initDB() {
        Statement statement;
        try {
            statement = myConnect.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + database + ";");
            statement.execute("USE " + database);
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS animals (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50),
                        birthday DATE
                    );""");
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS animalTypes (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        type VARCHAR(50)
                    );""");
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS skills (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        skill VARCHAR(100)
                    );""");

        } catch (SQLException e) {
            log.append(e.getMessage());
        }
    }

    public Connection mysqlConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException | ClassNotFoundException e) {
            log.append(e.getMessage());
        }
        return null;
    }

    @Override
    public void disconnect() {
        try {
            myConnect.close();
        } catch (SQLException e) {
            log.append(e.getMessage());
        }
    }

    @Override
    public void addAnimal(Animal animal) {
        try {
            Statement statement = myConnect.createStatement();
            ResultSet resultSet = statement.executeQuery("INSERT * FROM таблица");
        } catch (SQLException e) {
            log.append(e.getMessage());
        }
    }

    @Override
    public void delAnimal(int id) {

    }

    @Override
    public Animal getAnimal(int id) {
        return null;
    }

    @Override
    public void updateAnimal(Animal animal) {

    }

    @Override
    public ArrayList<Animal> getAnimalsList() {
        return null;
    }

    @Override
    public boolean getInitOk() {
        return initOk;
    }
}
