package war_games.Generals;

public enum SoldierRank {
    PRIVATE(1),
    CORPORAL(2),
    CAPTAIN(3),
    MAJOR(4);

    private final int value;

    SoldierRank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}