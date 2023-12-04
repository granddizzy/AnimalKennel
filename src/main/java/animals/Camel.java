package animals;

import abstractAnimals.PackAnimal;

import java.util.ArrayList;

public class Camel extends PackAnimal {
    public Camel(String name, int birthday, int birthmonth, int birthyear) {
        super(name, birthday, birthmonth, birthyear);
    }

    public Camel(int id, String name, int birthday, int birthmonth, int birthyear, ArrayList<String> skills) {
        super(id, name, birthday, birthmonth, birthyear, skills);
    }

    @Override
    public String toString() {
        return "id:" + super.getId() + " Верблюд: " + super.getName() + " Дата рождения: "  + this.getBirthday() + "." + this.getBirthmonth() + "." + this.getBirthyear();
    }
}
