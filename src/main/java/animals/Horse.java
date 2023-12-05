package animals;

import abstractAnimals.Animal;
import abstractAnimals.PackAnimal;

import java.util.ArrayList;

public class Horse extends PackAnimal {

    public Horse(String name, int birthday, int birthmonth, int birthyear) {
        super(name, birthday, birthmonth, birthyear);
    }

    public Horse(int id, String name, int birthday, int birthmonth, int birthyear, ArrayList<String> skills) {
        super(id, name, birthday, birthmonth, birthyear, skills);
    }

    @Override
    public String toString() {
        return "id:" + super.getId() + " Лошадь: " + super.getName() + " Возраст: "+ Animal.calcAge(super.getBirthday(), super.getBirthmonth(), super.getBirthyear());
    }

    @Override
    public String getTypeName() {
        return "Лошадь";
    }
}
