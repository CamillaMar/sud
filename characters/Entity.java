package sud.characters;

import sud.characters.fightable.monsters.Monster;
import sud.items.Item;
import sud.rooms.Room;

import java.util.HashMap;

import static sud.GameUtil.*;

public abstract class Entity {
    private static int lastId = 0;
    private int characterId;
    private String name;
    private HashMap<String, Item> inventory;
    private Room actualRoom;
    private boolean hasQuest;
    private String greetMessage;

    public Entity(String name){
        this.name = name;
        inventory = new HashMap<>();
        lastId++;
        this.characterId = lastId;
    }

    public void greet(){
        System.out.println(name + " says : " + BLUE + greetMessage + RESET);
        delay(800);
    }
    public void printInventory(){
        if(inventory.isEmpty()){
            System.out.printf("%s's inventory is empty.%n", name);
            delay(300);
        }
        inventory.keySet().forEach(System.out::println);
    }

    public void pickItem(Item item){
        inventory.put(item.getName(), item);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Room getActualRoom() {
        return actualRoom;
    }
    public void setActualRoom(Room actualRoom) {
        this.actualRoom = actualRoom;
        actualRoom.getPresentEntities().put(name, this);
        if(this instanceof Monster){
            Monster monster = (Monster)this;
            actualRoom.getPresentMonsters().add(monster);
        }
    }

    public HashMap<String, Item> getInventory() {
        return inventory;
    }
    public void setInventory(HashMap<String, Item> inventory) {
        this.inventory = inventory;
    }

    public int getCharacterId() {
        return characterId;
    }
    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Entity){
            Entity ent = (Entity)obj;
            return this.characterId == ent.characterId;
        }
        return false;
    }

    public boolean doesHaveQuest() {
        return hasQuest;
    }
    public void setHasQuest(boolean hasQuest) {
        this.hasQuest = hasQuest;
    }

    public String getGreetMessage() {
        return greetMessage;
    }

    public void setGreetMessage(String greetMessage) {
        this.greetMessage = greetMessage;
    }
}
