package abstractAnimals;

import java.util.ArrayList;

public abstract class Animal {

    private final int birthday;
    private final int birthmonth;
    private final int birthyear;

    private ArrayList<String> Skills;

    protected Animal(int birthday, int birthmonth, int birthyear) {
        this.birthday = birthday;
        this.birthmonth = birthmonth;
        this.birthyear = birthyear;
        this.Skills = new ArrayList<String>();
    }

    public void addAnimalSkill() {

    }

    public void delAnimalSkill() {

    }

    public ArrayList<String> getSkills() {
        return Skills;
    }
}
