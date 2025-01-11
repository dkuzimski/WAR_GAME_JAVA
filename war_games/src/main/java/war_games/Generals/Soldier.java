package war_games.Generals;

public class Soldier {
    private SoldierRank rank;
    private int experience;

    public Soldier(SoldierRank rank) {
        this.rank = rank;
        this.experience = 1;
        validateState();
    }

    public SoldierRank getRank() {
        return rank;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Doswiadczenie nie moze byc ujemne");
        }
        this.experience = experience;
    }   

    public int getStrength() {
        return rank.getValue() * experience;
    }

 

    public void gainExperience() {
        if (experience >= 5 * rank.getValue()) {
            promote();
        } else {
            experience++;
        }
        validateState();
    }

    public void loseExperience() {
        if (this.experience > 0) {
            this.experience--;
        }
        if (this.experience == 0) {
            this.experience = 0;
        }
        validateState();
    }

    private void promote() {
        switch (rank) {
            case PRIVATE -> rank = SoldierRank.CORPORAL;
            case CORPORAL -> rank = SoldierRank.CAPTAIN;
            case CAPTAIN -> rank = SoldierRank.MAJOR;
            default -> {}
        }
        experience = 1;
    }

    public boolean isAlive() {
        return experience > 0;
    }

    private void validateState() {
        if (experience < 0) {
            throw new IllegalArgumentException("Doswiaczenie zolnierza nie moze byc ujemne.");
        }
        if (rank == null) {
            throw new IllegalArgumentException("Ranga zolnierza nie moze wynosic null.");
        }
    }
}