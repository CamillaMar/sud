package sud.characters.npcs;

import sud.enums.Answer;
import sud.characters.Entity;
import sud.items.Food;
import sud.items.Weapon;
import sud.rooms.RoomMap;

import java.util.HashSet;
import java.util.Set;

import static sud.game.GameMap.console;
import static sud.game.GameUtil.*;

public class Merchant extends Entity {
    private static final int MERCHANT_POSSIBLE_ROOM = 4;

    public Merchant(String name) {
        super(name);
        setActualRoom(RoomMap.getRooms().get(MERCHANT_POSSIBLE_ROOM));
        pickItem(new Food("Bread", 4));
        pickItem(new Weapon("Long sword", 3));
        pickItem(new Weapon("Dagger", 1));
        pickItem(new Weapon("Hammer", 4));
        pickItem(new Weapon("Axe", 4));
    }

    public void greet(){
        System.out.println(getName() + " says :" + BLUE + " Oy there! Looking a bit lost! I think I got what you need...");
        System.out.println("The best equipment in the whole realm... Wanna take a look?" + RESET);
        Set<String> equip = new HashSet<>(getInventory().keySet());
        String ans;
        if(doesWantEquip()){
            do{
                System.out.println("Choose:");
                getInventory().keySet().forEach(System.out::println);
                ans = toTitleCase(console.readLine());
            } while (!equip.contains(ans));
            player.pickItem(getInventory().get(ans));
        }
    }

    public boolean doesWantEquip(){
        String ans = null;
        do{
            System.out.println("Answer Y or N: ");
            ans = console.readLine().toUpperCase();
        }while(!ans.equals(Answer.N.toString()) && !ans.equals(Answer.Y.toString()));
        if(ans.equals(Answer.Y.toString())){
            return true;
        } else if(ans.equals(Answer.N.toString())){
            System.out.println(BLUE + "Oh well, be careful out there then..." + RESET);
            delay(800);
        }
        return false;
    }

    @Override
    public void printInventory() {
    }
}
