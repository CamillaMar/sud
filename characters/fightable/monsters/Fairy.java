package sud.characters.fightable.monsters;

import sud.rooms.RoomMap;

public class Fairy extends Monster {
    private static final int MAX_FAIRY = 2;
    private static final int FAIRY_EXP_GIVEN = 2;
    private static final int FAIRY_POSSIBLE_ROOM = 4;
    private static final int FAIRY_HP = 9;


    public Fairy(String name){
        super(name);
        setStrength(dice.nextInt(MIN, MAX_FAIRY) + 1);
        setAgility(dice.nextInt(MIN, MAX_FAIRY));
        setActualRoom(RoomMap.getRooms().get(dice.nextInt(FAIRY_POSSIBLE_ROOM)));
        setHp(FAIRY_HP);
        setExpGiven(FAIRY_EXP_GIVEN);
        setGreetMessage("Hey there!");
    }

    @Override
    public void respawn() {
        if(!checkIfAlive()) {
            setIsAlive(true);
            setHp(FAIRY_HP);
            setActualRoom(RoomMap.getRooms().get(dice.nextInt(FAIRY_POSSIBLE_ROOM)));
//            getActualRoom().getPresentMonsters().add(this);
//            getActualRoom().getPresentMonstersClasses().add("Cat");
        }
    }
}

