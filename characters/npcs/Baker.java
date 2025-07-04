package sud.characters.npcs;

import sud.enums.Answer;
import sud.characters.Entity;
import sud.items.Food;
import sud.rooms.RoomMap;

import java.util.HashSet;
import java.util.Set;

import static sud.game.GameMap.console;
import static sud.game.GameUtil.*;

public class Baker extends Entity {
    private static final int BAKER_POSSIBLE_ROOM = 3;

    public Baker(String name) {
        super(name);
        setActualRoom(RoomMap.getRooms().get(BAKER_POSSIBLE_ROOM));
        pickItem(new Food("Candy", 1));
        pickItem(new Food("Bread", 2));
        pickItem(new Food("Sandwich", 3));
        pickItem(new Food("Apple pie", 4));
        pickItem(new Food("Elf bread", 5));
    }

    @Override
    public void greet(){
        System.out.println(getName() + " says : " + BLUE + "We have the best bread in the whole wide realm!");
        System.out.println("Would you like something to eat? Food is great for you health and for your heart!" + RESET);
        Set<String> food = new HashSet<>(getInventory().keySet());
        String ans;
        if(doesWantFood()){
            do{
                System.out.println("Choose:");
                printInventory();
                ans = toTitleCase(console.readLine());
            } while (!food.contains(ans));
            player.pickItem(getInventory().get(ans));
        }
    }

    public boolean doesWantFood(){
        String ans = null;
        do{
            System.out.println("Answer Y or N: ");
            ans = console.readLine().toUpperCase();
        }while(!ans.equals(Answer.N.toString()) && !ans.equals(Answer.Y.toString()));
        if(ans.equals(Answer.Y.toString())){
            return true;
        } else if(ans.equals(Answer.N.toString())){
            System.out.println(BLUE + "Alright! You know where to find me when you get hungry!" + RESET);
            delay(800);
        }
        return false;
    }



}
