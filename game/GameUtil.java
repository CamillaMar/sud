package sud.game;

import sud.enums.Answer;
import sud.enums.CardinalPoints;
import sud.exceptions.EndOfGameException;
import sud.characters.fightable.*;
import sud.characters.fightable.monsters.Guard;
import sud.characters.fightable.monsters.MonsterMap;
import sud.items.Tree;
import sud.rooms.RoomMap;

import java.io.Console;
import java.util.*;

import static sud.game.GameMap.console;
import static sud.characters.fightable.Character.dice;
import static sud.characters.fightable.monsters.Guard.GUARD_POSSIBLE_ROOM;

public class GameUtil {
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\033[0;33m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static PlayerCharacter player;
    public static List<String> choices = new ArrayList<>();
    public static List<String> directions = new ArrayList<>();
    static{
        choices.add("FIGHT");
        choices.add("MOVE");
        choices.add("PICK");
        choices.add("TALK");
        choices.add("STATS");
        choices.add("INVENTORY");
        choices.add("F");
        choices.add("M");
        choices.add("P");
        choices.add("T");
        choices.add("S");
        choices.add("I");
        choices.add("Q");
        directions.add("NORTH");
        directions.add("WEST");
        directions.add("SOUTH");
        directions.add("EAST");
    }

    public static void createNewPlayer() throws EndOfGameException {
        String answer = null;
        do{System.out.printf(PURPLE + "Hello! What kind of creature do you want to be?%n");
            System.out.printf("Choose between: Wizard, Thief, Paladin or Priest: %n");
            answer = console.readLine().toUpperCase().trim();
        } while (!answer.equals("WIZARD") && !answer.equals("PALADIN")  && !answer.equals("PRIEST")  && !answer.equals("THIEF"));
        System.out.printf("What's your name, hero?%n" + RESET);
        String answerName = console.readLine().trim();
        //chiediamo il nome
        if(answer.equals("WIZARD")){
            player = new Wizard(answerName);
        } else if (answer.equals("PALADIN")) {
            player = new Paladin(answerName);
        } else if (answer.equals("PRIEST")) {
            player = new Priest(answerName);
        } else {
            player = new Thief(answerName);
        }
        delay(800);
        System.out.printf("Initializing %s the %s...%n", player.getName(), player.getClass().getSimpleName());
        delay(800);
        player.printStats();
        delay(1000);
        System.out.println("PRESS ENTER TO CONTINUE READING.");
        delay(800);
        System.out.printf(BLUE + "Welcome %s!", player.getName());
        continueTheMessage();
        System.out.print("This city has been waiting for and adventurer like you to come to the rescue.");
        continueTheMessage();
        System.out.print("Strange monsters and ghosts coming from the dark woods have been creeping around the city entrance in the last days and the civilians are well preoccupied." );
        continueTheMessage();
        System.out.println();
        System.out.printf("Will you be the hero that's going to stop this menace?%n" +
                "If you think so, go east to the sacred Temple and talk to the wise Elf Elrond, he will show you the way to braveness%n" + RESET);
        continueTheMessage();
        System.out.println("You are now at the Temple Square.");
        player.getActualRoom().printEntities();
        System.out.println("Follow the MENU instructions to interact with this magic world, good luck!");
        askWhatToDo();
    }

    //METODO PER CHIEDERE AL GIOCATORE CHE FARE APPENA ENTRA IN UNA STANZA
    public static void askWhatToDo() throws EndOfGameException {
        String ans;
        System.out.printf(PURPLE + "What do you want to do now?%n" + RESET);
        do{
            System.out.println("Write 'fight' or 'f' if you want to fight some monsters.");
            System.out.println("Write 'move' or 'm' if you want to start moving.");
            System.out.println("Write 'pick' or 'p' if you want to pick an item.");
            System.out.println("Write 'talk' or 't' if you want to talk to someone.");
            System.out.println("Write 'stats' or 's' to see you statistics");
            System.out.println("Write 'inventory' or 'i' to see your inventory");
            //TODO aggiungere comando look
            System.out.printf("Write 'q' if you want to end the game.%n");
            ans = console.readLine().toUpperCase().trim();
        }while(!choices.contains(ans));
        player.doAsAsked(ans);
    }

    public static void askWhatToDoWithoutMenu() throws EndOfGameException {
        String ans;
        System.out.printf(PURPLE + "What do you want to do now?%n" + RESET);
        do {
            System.out.println("Write what you want to do or write MENU to see the menu again.");
            ans = console.readLine().toUpperCase().trim();
        } while (!choices.contains(ans) && !ans.equals("MENU"));
        player.doAsAsked(ans);
    }

    //METODI PER MUOVERSI

    public static void askIfWantToRest(){
        String ans = null;
        do{
            System.out.println("It's so calm in here, do you want to rest? Answer with Y or N: ");
            ans = console.readLine().toUpperCase();
        }while(!ans.equals(Answer.N.toString()) && !ans.equals(Answer.Y.toString()));
        if(ans.equals(Answer.Y.toString())){
            System.out.println("You are falling asleep...");
            delay(1000);
            System.out.println("...dreaming of scary dragons...");
            delay(1000);
            System.out.println("...the deepness of the woods...");
            delay(1000);
            System.out.println("You woke up.");
            delay(1000);
            System.out.printf(YELLOW + "Resting gives + 2 stamina points%n" + RESET);
            player.sleep(2);
        }
    }
    public static void askForDirections(){
        String ans = null;
        do{
                System.out.printf(PURPLE + "Where do you want to go? Answer with: North or N, East or E, South or S or West or W, write Stop if you want to stop here.%n"+ RESET);
                ans = console.readLine().toUpperCase();
            if(ans.equals(CardinalPoints.NORTH.toString()) || ans.equals("N")){
                player.changeRoom(CardinalPoints.NORTH);
            } else if(ans.equals(CardinalPoints.EAST.toString()) || ans.equals("E")){
                player.changeRoom(CardinalPoints.EAST);
            } else if(ans.equals(CardinalPoints.SOUTH.toString()) || ans.equals("S")){
                player.changeRoom(CardinalPoints.SOUTH);
            } else if(ans.equals(CardinalPoints.WEST.toString()) || ans.equals("W")){
                player.changeRoom(CardinalPoints.WEST);
            }
        }while(!ans.equals("STOP"));
    }


    //metodi item

    public static boolean doesWantPick() {
        String ans = null;
        if (!player.getActualRoom().getPresentItems().isEmpty()) {
            do {
                System.out.println("Answer Y or N: ");
                ans = console.readLine().toUpperCase();
            } while (!ans.equals(Answer.N.toString()) && !ans.equals(Answer.Y.toString()));
            if (ans.equals(Answer.Y.toString())) {
                return true;
            }
        }
        return false;
    }

    public static void pickChosenItem(){
        Set<String> items = new HashSet<>(player.getActualRoom().getPresentItems().keySet());
        String ans;
        System.out.printf(PURPLE + "Do you want to pick anything?%n" + RESET);
        if(doesWantPick()){
            do{
                System.out.println("What do you want to pick? You can choose a tree to pick its fruits if they have any left.");
                ans = toTitleCase(console.readLine());
            } while (!items.contains(ans));
            if(player.getActualRoom().getPresentItems().get(ans) instanceof Tree tree){
                tree.pickFruit();
            } else{
                player.pickItem((player.getActualRoom().getPresentItems().get(ans)));
                player.getActualRoom().getPresentItems().remove(ans);
            }
        }
    }

    //METODO PER FAR SPOSTARE LE GUARDIE ALLA FINE DI OGNI CICLO
    public static void randomizeTheGuards(){
        List<Guard> guards = MonsterMap.getGuards();
        guards.forEach(Guard::leaveActualRoom);
        guards.forEach(g -> g.setActualRoom(RoomMap.getRooms().get(dice.nextInt(GUARD_POSSIBLE_ROOM))));
    }

    //METODO PER INTERAGIRE
    public static void askWhoToTalk(){
        String ans = null;
        List<String> names = player.getActualRoom().getPresentEntities().keySet().stream().toList();
        System.out.printf(PURPLE + "Who do you want to talk to? Write their name: %n" + RESET);
        ans = console.readLine();
        if(!(ans.isEmpty())){
            ans = toTitleCase(ans);
        }
        if(names.contains(ans)){
            player.getActualRoom().getPresentEntities().get(ans).greet();
        } else {
            System.out.println("They're not here right now.");
        }
    }

    //METODI UTIL
    public static String toTitleCase(String s){
        char first = s.charAt(0);
        char firstUpper = java.lang.Character.toUpperCase(first);
        String subString = s.substring(1);
        return firstUpper + subString;
    }
    public static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
        }
    }
    public static void continueTheMessage(){
        Console console = System.console();
        console.readLine();
    }

    //TODO AGGIUNGERE I SOLDI PER COMPRARE LE COSE???

}


