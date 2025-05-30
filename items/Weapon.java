package sud.items;

import sud.Answer;

import static sud.GameMap.console;

public class Weapon extends Item {

    private int strengthGiven;
    public Weapon(String name, int strengthGiven) {
        super(name);
        this.strengthGiven = strengthGiven;
    }

    public String checkWeapon() {
        String ans = null;
        System.out.println("Do you want to equip this?");
        do {
            System.out.println("Answer Y or N: ");
            ans = console.readLine().toUpperCase();
        } while (!ans.equals(Answer.N.toString()) && !ans.equals(Answer.Y.toString()));
        if (ans.equals(Answer.Y.toString())) {
            return "Y";
        }
        return "";
    }

    public int getStrengthGiven() {
        return strengthGiven;
    }
    public void setStrengthGiven(int strengthGiven) {
        this.strengthGiven = strengthGiven;
    }
}
