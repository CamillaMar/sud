package sud.characters.npcs;

import sud.characters.Entity;
import sud.items.Map;
import sud.items.Weapon;
import sud.rooms.RoomMap;

import java.util.ArrayList;
import java.util.List;

public class NPCMap {

    static private List<Entity> npcs = new ArrayList<>();
    static {
        npcs.add(new Elf("Elrond", "There will soon be a time when us elves will return home...", RoomMap.getRooms().get(1), new Weapon("Short sword", 2), true));
        npcs.add(new Elf("Arwen", "I'm willing to give up my immortal life, just to live a mortal life with you...",
                RoomMap.getRooms().get(5), new Map("Map"), false));
        npcs.add(new Baker("Tom"));
        npcs.add(new Merchant("Isildur"));
        npcs.add(new Hobbit("Peregrin"));
        npcs.add(new Hobbit("Bilbo"));
        npcs.add(new Hobbit("Merry"));
        npcs.add(new Hobbit("Samwise"));
        npcs.add(new Elf("Schiarux", "Have you ever tried a Long Island Iced Tea?"));
        npcs.add(new Elf("Nicuz", "Let's go training, darling!"));
    }

    public static List<Entity> getNpcs() {
        return npcs;
    }
    public static void setNpcs(List<Entity> npcs) {
        NPCMap.npcs = npcs;
    }

}
