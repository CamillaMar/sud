package sud.characters.fightable;

public class Wizard extends PlayerCharacter {
    private static final int MIN_INTEL = 15;
    private static final int MIN_AGIL = 5;

    public Wizard(String name){
        super(name, MIN_INTEL, MIN_AGIL, MIN);
    }

    //l'attacco del mago dipenderà dalla stamina e dall'intelligenza
    @Override
    public void attack(Character character) {
        int damage = checkMagic(getStrength());
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

    public int checkMagic(int damage){
        if(getStamina() > dice.nextInt(MAX - getIntelligence())){
            return (int)(damage*1.5);
        }
        return damage;
    }
}
