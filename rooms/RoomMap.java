package sud.rooms;

import sud.CardinalPoints;
import sud.items.Tree;

import java.util.ArrayList;
import java.util.List;

import static sud.characters.fightable.Character.dice;

public class RoomMap {
    static private List<Room> rooms = new ArrayList<>();
    static{
        //creo le room
        Room templeSquare = new Room("Temple Square", "Welcome to the Temple Square");
        Room theTemple = new Room("The Temple", "Contemplate the deepness of the soul here at the Temple");
        Room marketSquare = new Room("Market Square", "The best market in town, welcome to Market Square!");
        Room armory = new Room("The Armory", "A real adventurer must have some real weapons! You will find them here at the Armory");
        Room bakery = new Room("The Bakery", "Can you smell the fresh from the oven bread? You are at the Bakery now.");
        Room firstGarden = new Room("The Magic Garden", "Enjoy some fresh air and pick a juicy fruit from the Magic Garden's trees!");
        Room secondGarden = new Room("The Dark Garden", "Is that a ghost? The Dark Garden really is dark");
        Room cityDoor = new Room("The City Door", "This is the entrance to the city! Check beyond these walls and be prepared, adventurer!");
        Room woods = new Room("The Woods", "You are now in the woods, are you sure you're ready for the monsters that live here?");
        Room inn = new Room("The Prancing Pony Inn", "This is the Prancing Pony Inn, the perfect place to recharge after a long day of fighting!");

        templeSquare.addPath(CardinalPoints.EAST, theTemple);
        templeSquare.addPath(CardinalPoints.SOUTH, marketSquare);
        templeSquare.addPath(CardinalPoints.WEST, inn);

        theTemple.addPath(CardinalPoints.WEST, templeSquare);

        marketSquare.addPath(CardinalPoints.NORTH, templeSquare);
        marketSquare.addPath(CardinalPoints.EAST, bakery);
        marketSquare.addPath(CardinalPoints.SOUTH, firstGarden);
        marketSquare.addPath(CardinalPoints.WEST, armory);


        bakery.addPath(CardinalPoints.WEST, marketSquare);

        armory.addPath(CardinalPoints.EAST, marketSquare);

        firstGarden.addPath(CardinalPoints.NORTH, marketSquare);
        firstGarden.addPath(CardinalPoints.SOUTH, secondGarden);

        secondGarden.addPath(CardinalPoints.NORTH, firstGarden);
        secondGarden.addPath(CardinalPoints.SOUTH, cityDoor);

        cityDoor.addPath(CardinalPoints.NORTH, secondGarden);
        cityDoor.addPath(CardinalPoints.SOUTH, woods);

        woods.addPath(CardinalPoints.NORTH, cityDoor);

        inn.addPath(CardinalPoints.EAST, templeSquare);

        rooms.add(templeSquare);
        rooms.add(theTemple);
        rooms.add(marketSquare);
        rooms.add(bakery);
        rooms.add(armory);
        rooms.add(firstGarden);
        rooms.add(secondGarden);
        rooms.add(cityDoor);
        rooms.add(woods);
        rooms.add(inn);

        //aggiungo gli alberi
        firstGarden.getPresentItems().put("Apple tree", new Tree("Apple tree", "Apple", 1));
        secondGarden.getPresentItems().put("Dark apple tree", new Tree("Dark apple tree", "Apple", 2));
        int magicNum;
        do{magicNum = dice.nextInt(-3,3);
        }while(magicNum == 0);
        secondGarden.getPresentItems().put("Misterious tree", new Tree("Misterious tree", "Mistery fruit", magicNum));

        woods.getPresentItems().put("Treebeard", new Tree("Treebeard", "Magic fruit", 4));
        woods.getPresentItems().put("Willow", new Tree("Willow", "Seeds", 3));
        woods.getPresentItems().put("Apricot", new Tree("Apricot", "Apricot", 1));

    }

    public static List<Room> getRooms(){
        return rooms;
    }
}
