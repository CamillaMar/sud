package sud.characters.fightable.monsters;

import sud.rooms.RoomMap;

public class Dragon extends Monster {
    private static final int MIN_DRAGON = 18;
    private static final int DRAGON_EXP = 20;
    private static final int DRAGON_EXP_GIVEN = 4;
    private static final int DRAGON_POSSIBLE_ROOM = 8;

    public Dragon(String name){
        super(name);
        setStrength(dice.nextInt(MIN_DRAGON, MAX));
        setAgility(dice.nextInt(MIN_DRAGON, MAX));
        setExp(DRAGON_EXP);
        setExpGiven(DRAGON_EXP_GIVEN);
        setActualRoom(RoomMap.getRooms().get(DRAGON_POSSIBLE_ROOM));
        setHp(MAX_HP);
//        getActualRoom().getPresentMonsters().add(this);
//        getActualRoom().getPresentMonstersClasses().add("Dragon");
    }
    @Override
    public void respawn() {
        if(!checkIfAlive()) {
            setIsAlive(true);
            setHp(MAX_HP);
            setActualRoom(RoomMap.getRooms().get(DRAGON_POSSIBLE_ROOM));
        }
    }

}
