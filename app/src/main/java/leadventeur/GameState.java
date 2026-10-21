package leadventeur;

public class GameState
{
    private int hp = 15;
    private int potions = 3;
    private String weapon = "Knife";
    private final int maxHP = 15;   
    
    public int getHP() {
        return hp;
    }

    public int getPotions() {
        return potions;
    }

    public String getWeapon() {
        return weapon;
    }

    public void damage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
    }

    public void heal()
    {
        if(potions > 0 && hp < maxHP)
        {
            --potions;
            hp = Math.min(maxHP, hp + 5);
        }
    }

    public void reset()
    {
        hp = maxHP;
        potions = 3;
        weapon = "Knife";
    }

    @Override 
    public String toString()
    {
        return 
            "HP: " + hp + "/" + maxHP + "\n" +
            "Weapon: " + weapon + "\n" +
            "Potions: " + potions + "\n";
    }
}
