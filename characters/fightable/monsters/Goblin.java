package sud.characters.fightable.monsters;

import sud.rooms.RoomMap;

public class Goblin extends Monster {
    private static final int MIN_GOB = 2;
    private static final int MAX_GOB = 7;
    private static final int GOB_EXP_GIVEN = 2;
    private static final int GOB_POSSIBLE_ROOM_START= 5;
    private static final int GOB_POSSIBLE_ROOM_END= 7;
    private static final int GOB_HP = 15;

    public Goblin(String name) {
        super(name);
        setStrength(dice.nextInt(MIN_GOB, MAX_GOB));
        setAgility(dice.nextInt(MIN_GOB, MAX_GOB));
        setActualRoom(RoomMap.getRooms().get(dice.nextInt(GOB_POSSIBLE_ROOM_START,GOB_POSSIBLE_ROOM_END)));
        setExp(GOB_EXP_GIVEN);
        setHp(GOB_HP);
        setGreetMessage("ARRGHRG!");
//        getActualRoom().getPresentMonsters().add(this);
//        getActualRoom().getPresentMonstersClasses().add("Goblin");
    }
    @Override
    public void respawn() {
        if(!checkIfAlive()) {
            setIsAlive(true);
            setHp(GOB_HP);
            setActualRoom(RoomMap.getRooms().get(dice.nextInt(GOB_POSSIBLE_ROOM_START, GOB_POSSIBLE_ROOM_END)));
        }
    }
}
