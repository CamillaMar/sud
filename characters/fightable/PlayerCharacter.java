package sud.characters.fightable;

import sud.enums.Answer;
import sud.enums.CardinalPoints;
import sud.exceptions.EndOfGameException;
import sud.game.GameUtil;
import sud.characters.fightable.monsters.Guard;
import sud.characters.fightable.monsters.Monster;
import sud.items.*;
import sud.rooms.RoomMap;

import java.util.HashMap;
import java.util.List;

import static sud.game.GameMap.console;
import static sud.game.GameUtil.*;

public abstract class PlayerCharacter extends Character {
    public static final int MAX_RESPAWN = 5;
    private static final int STARTING_STAM = 10;
    private static final int STARTING_STRENGTH = 3;

    private int killsCounter;
    private int respawnCounter;
    private int expCounter;
    private int stamina;
    private HashMap<String, Item> equipped;


    public PlayerCharacter(String name, int minIntelligence, int minAgility, int minStamina){
        super(name);
        setIntelligence(dice.nextInt(minIntelligence, MAX));
        setStrength(STARTING_STRENGTH);
        setAgility(dice.nextInt(minAgility, MAX));
        setStamina(STARTING_STAM);
        setActualRoom(RoomMap.getRooms().getFirst());
        killsCounter = 0;
        setHp(MAX_HP);
        equipped = new HashMap<>();
    }

    @Override
    public void changeRoom(CardinalPoints cardinal) {
        if (getActualRoom().getDirections().containsKey(cardinal)) {
            getActualRoom().leaveRoom(this);
            getActualRoom().getDirections().get(cardinal).enterInRoom(this);
            getActualRoom().printEntrance();
            //per ora lascio qui la guarigione del tempio, ma forse sta meglio nel tempio? dopo vedo
            if(getActualRoom().equals(RoomMap.getRooms().get(1))){
                System.out.println("The Temple's magical air heals you. +2 hp points.");
                delay(500);
                heal(2);
            }
            if(getActualRoom().equals(RoomMap.getRooms().get(9))){
                askIfWantToRest();
            }
        } else {
            System.out.println("The road is closed. You're still at " + getActualRoom().getName());
            delay(500);
        }
    }

    public void eat(Item item){
        if(item instanceof Food food){
            getInventory().remove(food.getName());
            if (food.getHpGiven()<0){
                hurt(-food.getHpGiven());
                System.out.printf("This %s is poisoned! You lose %d hp!%n", food.getName(), -food.getHpGiven());
                delay(500);
            } else {
                System.out.printf("You ate %s and you got +%d hp.%n", food.getName(), food.getHpGiven());
                heal(food.getHpGiven());
                delay(500);
            }
        } else {
            System.out.println("You can't eat this!");
            delay(500);
        }
    }

    public void respawn(){
        if(!checkIfAlive() && respawnCounter < MAX_RESPAWN){
            setHp(MAX_HP);
            setIsAlive(true);
            respawnCounter++;
            setActualRoom(RoomMap.getRooms().getFirst());
            System.out.printf("Welcome back! You are now in %s and you have %d respawn possibilities left!%n",
                    RoomMap.getRooms().getFirst().getName(),MAX_RESPAWN-respawnCounter);
            delay(500);
        }
    }

    public void askForRespawn() throws EndOfGameException {
        String ans = null;
        if(getRespawnCounter() < MAX_RESPAWN){
            do{
                System.out.printf("You have %d possibilities to respawn left. Do you want to respawn? Answer Y or N :%n",
                        MAX_RESPAWN- getRespawnCounter());
                ans = console.readLine().toUpperCase();

            }while(!ans.equals(Answer.N.toString()) && !ans.equals(Answer.Y.toString()));
            if(ans.equals(Answer.Y.toString())){
                respawn();
            } else if(ans.equals(Answer.N.toString())){
                System.out.println("Thanks for playing. Goodbye!");
                throw new EndOfGameException("");
            }
        } else{
            System.out.println("You can't respawn anymore. End of the game. Goodbye!");
            throw new EndOfGameException("");
        }
    }

    //ogni 5 punti esperienza gli do un punto stamina
    public void addExp(int exp){
        setExp(getExp() + exp);
        if(getExp()/5>expCounter){
            setStamina(getStamina() + 1);
            expCounter++;
        }
    }

    public void printStats(){
        System.out.printf("Here are %s's statistics:%n", getName());
        delay(800);
        System.out.printf(YELLOW + "Exp: %d%n", getExp());
        delay(300);
        System.out.println("HP: " + getHp() + "/" + MAX_HP);
        delay(300);
        System.out.println("Intelligence: " + getIntelligence() + "/" + MAX);
        delay(300);
        System.out.println("Strength: " + getStrength() + "/" + MAX);
        delay(300);
        System.out.println("Agility: " + getAgility() + "/" + MAX);
        delay(300);
        System.out.println("Stamina: " + stamina + "/" + MAX);
        delay(300);
        System.out.printf("Respawns Left: %d%n", (MAX_RESPAWN-respawnCounter));
        System.out.printf(RESET);
    }
    @Override
    public void pickItem(Item item){
            getInventory().put(item.getName(), item);
            if(!(item instanceof Tree)) {
                System.out.println("You have picked " + item.getName());
                delay(800);
            }
            if(item instanceof Ring){
                System.out.printf(BLUE + "The One Ring! You did it! Bring it to Elrond to finish your quest%n" + RESET);
            }
    }

    public void useItemInInventory(){
        System.out.println("Select an item or press enter to exit the inventory:");
        String ans = null;
        ans = console.readLine();
        if(!(ans.isEmpty())){
            ans = toTitleCase(ans);
        }
        if(getInventory().containsKey(ans)){
            if(getInventory().get(ans) instanceof Food food){
                if(food.checkFood().equals("Y")){
                    eat(food);
                }
            } else if(getInventory().get(ans) instanceof Weapon weapon){
                if(weapon.checkWeapon().equals("Y")){
                    getInventory().remove(weapon.getName());
                    equipped.put(weapon.getName(), weapon);
                    System.out.printf("You have equipped this %s.%n", weapon.getName());
                    addStrength(weapon.getStrengthGiven());
                }
            } else if(getInventory().get(ans) instanceof IntelligenceItem intelligenceItem){

            } else if(getInventory().get(ans) instanceof AgilityItem agilityItem){

            } else if(getInventory().get(ans) instanceof Map map){
                map.checkMap();
            }
        }
    }


    public void addStrength(int addedStrength){
        if((getStrength() + addedStrength) <= MAX && addedStrength > 0){
            setStrength(getStrength() + addedStrength);
            System.out.printf(YELLOW + "You have gained %d strength points%n", addedStrength);
            System.out.printf(RESET);
            delay(800);
        } else if ((getStrength() + addedStrength) > MAX){
            setStrength(MAX);;
            System.out.println("You are at MAX strength.");
            delay(800);
        }
    }
    public void addIntelligence(int addedIntel){
        if((getIntelligence() + addedIntel) <= MAX && addedIntel > 0){
            setIntelligence(getIntelligence() + addedIntel);
            System.out.printf(YELLOW + "You have gained %d intelligence points%n", addedIntel);
            System.out.printf(RESET);
            delay(800);
        } else if ((getIntelligence() + addedIntel) > MAX){
            setIntelligence(MAX);;
            System.out.println("You are at MAX intelligence.");
            delay(800);
        }
    }
    public void addAgility(int addedAgil){
        if((getAgility() + addedAgil) <= MAX && addedAgil > 0){
            setAgility(getAgility() + addedAgil);
            System.out.printf(YELLOW + "You have gained %d agility points%n", addedAgil);
            System.out.printf(RESET);
            delay(800);
        } else if ((getAgility() + addedAgil) > MAX){
            setAgility(MAX);;
            System.out.println("You are at MAX agility.");
            delay(800);
        }
    }

    public void sleep(int addStam) {
        if (stamina > MIN) {
            if ((stamina + addStam) <= MAX) {
                setStamina(stamina + addStam);
            }
            setStamina(MAX_HP);
            System.out.printf(YELLOW + "You're at full stamina.%n" + RESET);
        }
    }

    public void getTired(int minusStam){
        if (stamina - minusStam > 0) {
                setStamina(stamina - minusStam);
            }
        System.out.println("You're too tired! Go to the inn and have some sleep");
    }

    //METODI PER COMBATTERE


    public void checkForMonsters() throws EndOfGameException {
        String ans;
        List<String> presentClasses = this.getActualRoom().getPresentMonsters().stream()
                .map(monster -> monster.getClass().getSimpleName()).distinct().toList();
        if (!this.getActualRoom().getPresentMonsters().isEmpty()) {
            do {
                System.out.println("These are the type of monsters present here: ");
                presentClasses.forEach(System.out::println);
                System.out.printf(PURPLE + "Are you ready to fight? Answer with Y or N: %n" + RESET);
                ans = console.readLine().toUpperCase();
            } while (!ans.equals(Answer.N.toString()) && !ans.equals(Answer.Y.toString()));
            if (ans.equals(Answer.Y.toString())) {
                fightChosenMonster(chooseMonster());
            } else if (ans.equals(Answer.N.toString())) {
                System.out.println("Rest is important too!");
                delay(400);
            }
        } else{
            System.out.println("There are no monsters to fight here!");
            delay(400);
        }
    }

    public String chooseMonster() {
        String ans;
        List<String> presentClasses = this.getActualRoom().getPresentMonsters().stream()
                .map(monster -> monster.getClass().getSimpleName()).distinct().toList();
        do {
            System.out.printf(PURPLE + "What kind of monster do you want to fight?%n" + RESET);

            ans = toTitleCase(console.readLine());
        } while (!presentClasses.contains(ans));
        return ans;
    }

    public void fightChosenMonster(String monsterClass) throws EndOfGameException {
        List<Monster> guardsPresent = this.getActualRoom().getPresentMonsters()
                .stream().filter(monster -> monster instanceof Guard).toList();
        List<Monster> monstersToFight = this.getActualRoom().getPresentMonsters().stream().
                filter(monster -> monster.getClass().getSimpleName().equals(monsterClass)).toList();
        checkForCombat(monstersToFight.getFirst());
        if (monsterClass.equals("Fairy") && !guardsPresent.isEmpty()) {
            Guard guard = (Guard) guardsPresent.getFirst();
            guard.attackForTheFairy();
            this.askForRespawn();
        }
    }

    public void checkForCombat(Character char2) throws EndOfGameException {
        if(this.getStamina()>0){
            this.combat(char2);
        } else {
            System.out.println("You don't have enough stamina to fight this monster! Go rest somewhere quiet...");
            delay(500);
        }
    }

    public void combat(Character char2) throws EndOfGameException {
        while (this.checkIfAlive() && char2.checkIfAlive()){
            this.attack(char2);
            delay(500);
            if(!char2.checkIfAlive()){
                break;
            }
            char2.attack(this);
            delay(500);
        }
        if(this.checkIfAlive()){
            this.addKillsCounter();
            if(char2 instanceof Monster monster){
                this.addExp(monster.getExpGiven());
                monster.respawn();
                this.setStamina(this.getStamina()-1);
            }
        } else {
            this.askForRespawn();
        }
    }

    //METODO CHE FA FARE QUELLO CHE IL PLAYER DECIDE
    public void doAsAsked(String ans) throws EndOfGameException {
        if(ans.equals("FIGHT") || ans.equals("F")){
            this.checkForMonsters();
        } else if (ans.equals("MOVE") || ans.equals("M")){
            GameUtil.askForDirections();
        } else if (ans.equals("PICK") || ans.equals("P")){
            this.getActualRoom().printItems();
            pickChosenItem();
        } else if (ans.equals("TALK") || ans.equals("T")){
            askWhoToTalk();
        } else if (ans.equals("STATS") || ans.equals("S")){
            this.printStats();
        } else if (ans.equals("INVENTORY") || ans.equals("I")){
            this.printInventory();
            this.useItemInInventory();
        } else if (ans.equals("Q")){
            System.out.println("Thanks for playing. Goodbye!");
            throw new EndOfGameException("");
        } else {
            askWhatToDo();
        }
    }

    //FUNZIONI DEL KILL'S COUNTER
    public void addKillsCounter(){
        killsCounter++;
        if(killsCounter == 5){
            setExp(getExp() + 1);
            killsCounter = 0;
        }
    }

    public int getKillsCounter(){
        return killsCounter;
    }

    public int getRespawnCounter() {
        return respawnCounter;
    }
    public void setRespawnCounter(int respawnCounter) {
        this.respawnCounter = respawnCounter;
    }

    public int getStamina() {
        return stamina;
    }
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

}
