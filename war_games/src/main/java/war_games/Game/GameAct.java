package war_games.Game;

public interface GameAct {
    void startGame();
    void saveGame();
    void loadGame();
    void showStatus();
    void recruitSoldiers();
    void performManeuvers();
    void attack();

}