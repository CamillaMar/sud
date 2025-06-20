package sud.rooms;

import sud.enums.CardinalPoints;
import sud.characters.Entity;
import sud.characters.fightable.monsters.Monster;
import sud.items.Item;

import java.util.*;

import static sud.game.GameUtil.*;

public class Room {
    //field
    private String name;
    private HashMap<CardinalPoints, Room> directions;
    private HashMap<String, Entity> presentEntities;
    private List<Monster> presentMonsters;
    private HashMap<String, Item> presentItems;
    private String message;

    //costruttore
    public Room(String name, String message){
        this.name = name;
        this.message = message;
        directions = new HashMap<>();
        presentEntities = new HashMap<>();
        presentItems = new HashMap<>();
        presentMonsters = new ArrayList<>();
    }

    //funzioni principali
    public void enterInRoom(Entity entity) {
        entity.setActualRoom(this);
    }
    public void leaveRoom(Entity entity) {
            presentEntities.remove(name);
            if(entity instanceof Monster){
                presentMonsters.remove((Monster)entity);
            }
    }

    public void changeRoomNPC(CardinalPoints cardinal, Entity entity){
        if(directions.containsKey(cardinal)) {
            leaveRoom(entity);
            directions.get(cardinal).enterInRoom(entity);
        }
    }

    //funziona ma era meglio metterlo in player direttamente
//    public void changeRoomPlayer(CardinalPoints cardinal, Entity entity){
//        if(directions.containsKey(cardinal)) {
//            leaveRoom(entity);
//            directions.get(cardinal).enterInRoom(entity);
//            this.printEntrance();
//        } else {
//            System.out.println("The road is closed. You're still at " + name);
//        }
//    }

    public void printEntrance(){
        System.out.println(message);
        printEntities();
    }

    public void printEntities(){
        Set<String> printable = new HashSet<>(presentEntities.keySet());
        printable.remove(player.getName());
        if(printable.isEmpty()){
            System.out.println("Looks like you're the only one around here right now");
        } else{
            System.out.println("In here there are some other creatures: ");
            printable.forEach(e -> System.out.printf(CYAN + e + " the " + presentEntities.get(e).getClass().getSimpleName() + "%n" + RESET));
        }
    }

    public void printItems(){
        Set<String> printable = new HashSet<>(presentItems.keySet());
        if(printable.isEmpty()){
            System.out.println("Looks like there's nothing laying around here.");
        } else{
            System.out.println("You look around and you see: ");
            printable.forEach(System.out::println);
        }
    }

    public void addPath(CardinalPoints cardinal, Room room){
        directions.put(cardinal, room);
    }


    //getter e setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public HashMap<CardinalPoints, Room> getDirections() {
        return directions;
    }
    public void setDirections(HashMap<CardinalPoints, Room> directions) {
        this.directions = directions;
    }

    public HashMap<String, Entity> getPresentEntities() {
        return presentEntities;
    }
    public void setPresentEntities(HashMap<String, Entity> presentEntities) {
        this.presentEntities = presentEntities;
    }

    public HashMap<String,Item> getPresentItems() {
        return presentItems;
    }
    public void setPresentItems(HashMap<String,Item> presentItems) {
        this.presentItems = presentItems;
    }

    public List<Monster> getPresentMonsters() {
        return presentMonsters;
    }
    public void setPresentMonsters(List<Monster> presentMonsters) {
        this.presentMonsters = presentMonsters;
    }


}
