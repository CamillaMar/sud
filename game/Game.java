package sud.game;

import sud.characters.fightable.monsters.MonsterMap;
import sud.characters.npcs.NPCMap;
import sud.rooms.RoomMap;

public class Game {
    public static void main(String[] args){

        RoomMap mappa = new RoomMap();
        NPCMap npcs = new NPCMap();
        MonsterMap monsters = new MonsterMap();
        GameMap story = new GameMap();
        story.start();

    }
}
