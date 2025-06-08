package sud.characters.fightable.monsters;

import sud.exceptions.EndOfGameException;
import sud.rooms.RoomMap;

import static sud.game.GameUtil.player;

public class Guard extends Monster {
    public static final int GUARD_POSSIBLE_ROOM = 8;

    public Guard(String name) {
        super(name);
        setStrength(dice.nextInt(MAX));
        setAgility(dice.nextInt(MAX));
        setActualRoom(RoomMap.getRooms().get(dice.nextInt(GUARD_POSSIBLE_ROOM)));
        setHp(MAX_HP);
    }

    public void attackForTheFairy() throws EndOfGameException {
        System.out.println(getName() + " the Guard says: HEY! WHAT ARE YOU DOING? I'LL SHOW YOU WHAT HAPPENS TO CRIMINALS LIKE YOU!");
        player.combat(this);
    }

    public void leaveActualRoom(){
        getActualRoom().leaveRoom(this);
    }


    @Override
    public void respawn() {
        if(!checkIfAlive()){
            setIsAlive(true);
            setHp(MAX_HP);
            setActualRoom(RoomMap.getRooms().get(dice.nextInt(GUARD_POSSIBLE_ROOM)));
        }
    }
}
