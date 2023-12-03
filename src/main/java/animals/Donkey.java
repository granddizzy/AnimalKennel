package animals;

import abstractAnimals.PackAnimal;

import java.util.ArrayList;

public class Donkey extends PackAnimal {
    public Donkey(String name, int birthday, int birthmonth, int birthyear) {
        super(name, birthday, birthmonth, birthyear);
    }

    public Donkey(int id, String name, int birthday, int birthmonth, int birthyear, ArrayList<String> skills) {
        super(id, name, birthday, birthmonth, birthyear, skills);
    }
}
