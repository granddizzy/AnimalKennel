package databases;

import abstractAnimals.Animal;
import animals.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseFiles extends Database {
    private final String DBFilePath;

    public DatabaseFiles(String path) throws IOException {
        if (path.equals("")) throw new IOException("The path to the database file is not specified in config.txt");

        DBFilePath = path;
        File file = new File(DBFilePath);

        if (!file.exists()) {
            try {
                boolean res = file.createNewFile();
            } catch (IOException ignored) {
                throw new IOException("Could not create a database file!");
            }
        }
    }

    @Override
    public void addAnimal(Animal animal) throws IOException {
        int lastId = getLastID();
        lastId++;
        animal.setId(lastId);

        try (FileWriter fw = new FileWriter(DBFilePath, true)) {
            fw.write(createDBLine(animal));
        }
    }

    @Override
    public void delAnimal(int id) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(DBFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.split(";");
                if (Integer.parseInt(arrLine[0]) != id) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(DBFilePath)) {
            fileOut.write(sb.toString().getBytes());
        }
    }

    @Override
    public Animal getAnimal(int id) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(DBFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.replace("\n", "").split(";", -1);
                if (Integer.parseInt(arrLine[0]) == id) {
                    return createAnimal(arrLine);
                }
            }
        }

        return null;
    }

    @Override
    public void updateAnimal(Animal animal) throws IOException {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(DBFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.split(";");
                if (Integer.parseInt(arrLine[0]) != animal.getId()) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                } else {
                    sb.append(createDBLine(animal));
                }
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(DBFilePath)) {
            fileOut.write(sb.toString().getBytes());
        }
    }

    @Override
    public ArrayList<Animal> getAnimalsList() throws IOException {
        ArrayList<Animal> animalList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DBFilePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] animalArray = parseDatabaseLine(line.replace("\n", ""));
                Animal animal = createAnimal(animalArray);
                animalList.add(animal);
            }
        }

        return animalList;
    }

    public Integer getLastID() throws IOException {
        String result;

        try (RandomAccessFile file = new RandomAccessFile(DBFilePath, "r")) {
            long fileLength = file.length();

            StringBuilder lastLine = new StringBuilder();
            long pointer = fileLength - 1;

            while (pointer >= 0) {
                file.seek(pointer);
                char currentChar = (char) file.readByte();

                if (currentChar == '\n' && lastLine.length() > 0) {
                    break;
                }

                lastLine.insert(0, currentChar);
                pointer--;
            }

            result = lastLine.toString().replace("\n", "");
        }

        if (!result.equals("")) return Integer.parseInt(result.split(";")[0]);

        return 0;
    }

    private String[] parseDatabaseLine(String line) {
        return line.split(";", -1);
    }

    private Animal createAnimal(String[] animalArray) {
        Animal animal = null;

        String[] birth = animalArray[3].split("-", -1);

        int birthday = Integer.parseInt(birth[2]);
        int birthmonth = Integer.parseInt(birth[1]);
        int birthyear = Integer.parseInt(birth[0]);

        ArrayList<String> skills = new ArrayList<>();

        if (!animalArray[4].equals("")) {
            String[] arrSkills = animalArray[4].split("\\|", -1);
            skills = new ArrayList<>(Arrays.asList(arrSkills));
        }

        switch (animalArray[1]) {
            case "Dog" -> {
                animal = new Dog(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Cat" -> {
                animal = new Cat(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Hamster" -> {
                animal = new Hamster(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Camel" -> {
                animal = new Camel(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Horse" -> {
                animal = new Horse(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
            case "Donkey" -> {
                animal = new Donkey(Integer.parseInt(animalArray[0]), animalArray[2], birthday, birthmonth, birthyear, skills);
            }
        }

        return animal;
    }

    private String createDBLine(Animal animal) {
        StringBuilder sb = new StringBuilder();

        //id
        sb.append(animal.getId()).append(";");

        // type
        if (animal instanceof Dog) {
            sb.append("Dog").append(";");
        } else if (animal instanceof Cat) {
            sb.append("Cat").append(";");
        } else if (animal instanceof Hamster) {
            sb.append("Hamster").append(";");
        } else if (animal instanceof Horse) {
            sb.append("Horse").append(";");
        } else if (animal instanceof Camel) {
            sb.append("Camel").append(";");
        } else if (animal instanceof Donkey) {
            sb.append("Donkey").append(";");
        }

        // name
        sb.append(animal.getName()).append(";");

        // date birthday
        sb.append(animal.getBirthyear()).append("-").append(animal.getBirthmonth()).append("-").append(animal.getBirthday()).append(";");

        // skills
        for (String skill : animal.getSkills()) {
            sb.append(skill).append("|");
        }
        if (animal.getSkills().size() > 0) {
            sb.delete(sb.length() - 1, sb.length());
        }

        sb.append("\n");

        return sb.toString();
    }
}
