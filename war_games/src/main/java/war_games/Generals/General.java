package war_games.Generals;

import java.util.List;

import war_games.Utils.InputValidator;

public class General {
    private String name;
    private int gold;
    private Army army;

    public General(String name, int gold, Army army) {
        this.name = name;
        this.gold = gold;
        this.army = army;
    }

    public String getName() {
        return name;
    }

    public Army getArmy() {
        return army;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        InputValidator.validatePositiveNumber(gold, "Zloto Generala");
        this.gold = gold;
    }

    public boolean performManeuvers(List<Soldier> soldiers) {
    int totalCost = soldiers.stream().mapToInt(s -> s.getRank().getValue()).sum();
    if (gold >= totalCost) {
        soldiers.forEach(Soldier::gainExperience);
        gold -= totalCost;
        return true;
    } 
    else {
        System.out.println("Niewystarczajaco zlota na manewry");
        return false;
    }
    }
    @Override
    public String toString() {
        return "General{nazwa='" + name + "', zloto=" + gold + ", armia=" + army + '}';
    }
}