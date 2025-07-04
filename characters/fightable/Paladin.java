package sud.characters.fightable;

import sud.characters.Entity;

import static sud.game.GameUtil.*;

public class Paladin extends PlayerCharacter {
    private static final int MIN_INTEL = 3;
    private static final int MIN_AGIL = 10;
    private boolean isVirgin = true;

    public Paladin(String name) {
        super(name, MIN_INTEL, MIN_AGIL,MIN);
    }

    //Nel paladino, l'attacco è influenzato dal suo essere vergine o meno e dalla sua intelligenza
    @Override
    public void attack(Character character) {
        int damage = checkPaladin(getStrength());
        declareAttack(getName(), character.getName(), damage);
        if(character.tryToDodge()){
            damage = 0;
            System.out.println(character.getName() + " dodged the attack!");
        }
        character.hurt(damage);
        if(character.checkIfAlive()){
            System.out.printf("%s has now %d lives%n", character.getName(), character.getHp());
        }
    }

    public int checkPaladin(int damage) {
        if (!isVirgin && (dice.nextInt(getIntelligence()) == 0)){
            System.out.println(getName() + "'s attack is weaker, because his heart is corrupted!");
            return damage/2;
        }
            return damage;
    }

    public void loseVirginity(Entity entity){
        isVirgin = false;
        System.out.println(getName() + " lost his virginity with " + entity.getName());
    }

    @Override
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
        System.out.println("Stamina: " + getStamina() + "/" + MAX);
        delay(300);
        System.out.printf("Respawns Left: %d%n", (MAX_RESPAWN-getRespawnCounter()));
        delay(300);
        if(isVirgin){
            System.out.println("You are still pure");
        } else{
            System.out.println("Your heart has already been corrupted...");
        }
        System.out.printf(RESET);
    }
}
