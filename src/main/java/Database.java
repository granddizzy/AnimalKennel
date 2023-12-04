import abstractAnimals.Animal;
import animals.*;

import java.io.*;
import java.util.ArrayList;

public class Database implements DatabaseInterface {

    private final String DBFilePath;
    private boolean initOk;

    public Database() {
        initOk = false;
        DBFilePath = "./DB.txt";
        File file = new File(DBFilePath);

        if (!file.exists()) {
            try {
                boolean res = file.createNewFile();
                initOk = true;
            } catch (IOException ignored) {

            }
        } else {
            initOk = true;
        }
    }

    @Override
    public void addAnimal(Animal animal) {
        int lastId = getLastID();
        lastId++;
        animal.setId(lastId);

        try (FileWriter fw = new FileWriter(DBFilePath, true)) {
            fw.write(createDBLine(animal));
        } catch (IOException ignored) {

        }
    }

    @Override
    public void delAnimal(int id) {
        try {
            File file = new File(DBFilePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.split(";");
                if (Integer.parseInt(arrLine[0]) != id) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
            }
            reader.close();

            FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(sb.toString().getBytes());
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении или записи файла.");
            e.printStackTrace();
        }
    }

    @Override
    public Animal getAnimal(int id) {
        try {
            File file = new File(DBFilePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] arrLine = line.replace("\n", "").split(";");
                if (Integer.parseInt(arrLine[0]) == id) {
                    return createAnimal(arrLine);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении или записи файла.");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateAnimal(Animal animal) {

    }

    @Override
    public ArrayList<Animal> getAnimalsList() {
        ArrayList<Animal> animalList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DBFilePath))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] animalArray = parseDatabaseLine(line.replace("\n", ""));

                Animal animal = createAnimal(animalArray);

                animalList.add((Animal) animal);
            }

        } catch (IOException ignored) {

        }

        return animalList;
    }

    public Integer getLastID() {
        String result = null;

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
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла.");
            e.printStackTrace();
        }

        if (result != null) return Integer.parseInt(result.split(";")[0]);

        return 0;
    }

    @Override
    public boolean getInitOk() {
        return initOk;
    }

    private String[] parseDatabaseLine(String line) {
        return line.split(";");

    }

    private Animal createAnimal(String[] animalArray) {
        Animal animal = null;

        int birthday = 0;
        int birthmonth = 0;
        int birthyear = 0;

        ArrayList<String> skills = new ArrayList<>();

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
            case "Hourse" -> {
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
        sb.append(animal.getBirthyear()).append(".").append(animal.getBirthmonth()).append(".").append(animal.getBirthday()).append("\n");

        return sb.toString();
    }
}
