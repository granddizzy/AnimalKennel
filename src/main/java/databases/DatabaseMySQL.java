package databases;

import abstractAnimals.Animal;
import animals.*;
import logs.Log;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

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

            String sql = "SELECT COUNT(*) AS table_count FROM information_schema.tables WHERE table_schema = '" + database + "' AND table_name = 'animalTypes'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                int tableCount = resultSet.getInt("table_count");
                if (tableCount == 0) {
                    statement.executeUpdate("""
                            CREATE TABLE IF NOT EXISTS animalTypes (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                type VARCHAR(50)
                            );""");

                    statement.executeUpdate("""
                            INSERT INTO animalTypes (type) VALUES ('Dog');
                            """);
                    statement.executeUpdate("""
                            INSERT INTO animalTypes (type) VALUES ('Cat');
                            """);
                    statement.executeUpdate("""
                            INSERT INTO animalTypes (type) VALUES ('Hamster');
                            """);
                    statement.executeUpdate("""
                            INSERT INTO animalTypes (type) VALUES ('Horse');
                            """);
                    statement.executeUpdate("""
                            INSERT INTO animalTypes (type) VALUES ('Donkey');
                            """);
                    statement.executeUpdate("""
                            INSERT INTO animalTypes (type) VALUES ('Camel');
                            """);
                }
            }

            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS animals (
                        id INT AUTO_INCREMENT PRIMARY KEY,    
                        animalType INT,             
                        name VARCHAR(50),
                        birthday DATE,
                        skills TEXT,
                        FOREIGN KEY (animalType) REFERENCES animalTypes(id)
                    );""");

            statement.close();
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
            String sql = ("""
                    INSERT INTO animals
                    (animalType, name, birthday)
                    VALUES (?, ?, ?);
                    """);

            PreparedStatement statement = myConnect.prepareStatement(sql);
            statement.setInt(1, getAnimalTypeID(getAnimalType(animal)));
            statement.setString(2, animal.getName());

            Date date = Date.valueOf(animal.getBirthyear() + "-" + animal.getBirthmonth() + "-" + animal.getBirthday());
            statement.setDate(3, date);

            int rows = statement.executeUpdate();
            if (rows == 0) {
                log.append("Данные не добавлены!");
            }
        } catch (SQLException e) {
            log.append(e.getMessage());
        }
    }

    private int getAnimalTypeID(String typeName) {
        try {
            String sql = ("""
                    SELECT id
                    FROM animalTypes
                    WHERE type = ?;
                    """);

            PreparedStatement statement = myConnect.prepareStatement(sql);
            statement.setString(1, typeName);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                return set.getInt("id");
            }
        } catch (SQLException e) {
            log.append(e.getMessage());
        }

        return 0;
    }

    @Override
    public void delAnimal(int id) {
        try {
            String sql = ("""
                    DELETE 
                    FROM animals
                    WHERE id = ?;
                    """);

            PreparedStatement statement = myConnect.prepareStatement(sql);
            statement.setInt(1, id);

            int rows = statement.executeUpdate();
            if (rows == 0) {
                log.append("Данные не удалены!");
            }
        } catch (SQLException e) {
            log.append(e.getMessage());
        }
    }

    @Override
    public Animal getAnimal(int id) {
        try {
            String sql = ("""
                    SELECT *
                    FROM animals
                    LEFT JOIN animalTypes
                    ON animals.animalType = animalTypes.id
                    WHERE animals.id = ?;
                    """);

            PreparedStatement statement = myConnect.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int animalId = set.getInt("id");
                String name = set.getString("name");
                String skills = set.getString("skills");
                Date birth = set.getDate("birthday");
                String type = set.getString("type");

                return createAnimal(animalId, name, skills, birth, type);
            }
        } catch (SQLException e) {
            log.append(e.getMessage());
        }

        return null;
    }

    private Animal createAnimal(int id, String name, String skillsString, Date birth, String type) {
        Animal animal = null;

        LocalDate localDate = birth.toLocalDate();

        ArrayList<String> skills = new ArrayList<>();

        if (skillsString != null && !skillsString.equals("")) {
            String[] arrSkills = skillsString.split("\\|", -1);
            skills = new ArrayList<>(Arrays.asList(arrSkills));
        }

        switch (type) {
            case "Dog" -> {
                animal = new Dog(id, name, localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear(), skills);
            }
            case "Cat" -> {
                animal = new Cat(id, name, localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear(), skills);
            }
            case "Hamster" -> {
                animal = new Hamster(id, name, localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear(), skills);
            }
            case "Camel" -> {
                animal = new Camel(id, name, localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear(), skills);
            }
            case "Horse" -> {
                animal = new Horse(id, name, localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear(), skills);
            }
            case "Donkey" -> {
                animal = new Donkey(id, name, localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear(), skills);
            }
        }

        return animal;
    }

    @Override
    public void updateAnimal(Animal animal) {
        try {
            String sql = ("""
                    UPDATE animals
                    SET
                    name = ?,
                    birthday = ?,
                    skills = ?
                    WHERE id = ?;
                    """);

            PreparedStatement statement = myConnect.prepareStatement(sql);
            statement.setString(1, animal.getName());

            Date date = Date.valueOf(animal.getBirthyear() + "-" + animal.getBirthmonth() + "-" + animal.getBirthday());
            statement.setDate(2, date);

            StringBuilder skills = new StringBuilder();
            for (String skill : animal.getSkills()) {
                skills.append(skill).append("|");
            }
            if (animal.getSkills().size() > 0) {
                skills.delete(skills.length() - 1, skills.length());
            }

            statement.setString(3, skills.toString());

            statement.setInt(4, animal.getId());

            int rows = statement.executeUpdate();
            if (rows == 0) {
                log.append("Данные не обновлены!");
            }
        } catch (SQLException e) {
            log.append(e.getMessage());
        }
    }

    @Override
    public ArrayList<Animal> getAnimalsList() {
        ArrayList<Animal> animalList = new ArrayList<>();

        try {
            String sql = ("""
                    SELECT *
                    FROM animals
                    LEFT JOIN animalTypes
                    ON animals.animalType = animalTypes.id;
                    """);

            PreparedStatement statement = myConnect.prepareStatement(sql);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                int animalId = set.getInt("id");
                String name = set.getString("name");
                String skills = set.getString("skills");
                Date birth = set.getDate("birthday");
                String type = set.getString("type");

                animalList.add(createAnimal(animalId, name, skills, birth, type));
            }
        } catch (SQLException e) {
            log.append(e.getMessage());
        }

        return animalList;
    }

    @Override
    public boolean getInitOk() {
        return initOk;
    }

    private String getAnimalType(Animal animal) {
        if (animal instanceof Dog) {
            return "Dog";
        } else if (animal instanceof Cat) {
            return "Cat";
        } else if (animal instanceof Hamster) {
            return "Hamster";
        } else if (animal instanceof Horse) {
            return "Horse";
        } else if (animal instanceof Camel) {
            return "Camel";
        } else if (animal instanceof Donkey) {
            return "Donkey";
        }

        return null;
    }
}
