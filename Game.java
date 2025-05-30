package sud;

import sud.characters.fightable.monsters.MonsterMap;
import sud.characters.npcs.NPCMap;
import sud.rooms.RoomMap;

public class Game {
    public static void main(String[] args){
        //prove di gioco, la struttura narrativa principale poi sarà in GameMap

        RoomMap mappa = new RoomMap();
        NPCMap npcs = new NPCMap();
        MonsterMap monsters = new MonsterMap();
        GameMap story = new GameMap();
        story.start();


    }
}
