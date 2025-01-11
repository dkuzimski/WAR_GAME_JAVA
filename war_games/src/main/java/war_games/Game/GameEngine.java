package war_games.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import war_games.Generals.General;
import war_games.Generals.Soldier;
import war_games.Generals.SoldierRank;

public class GameEngine implements GameAct {
    private GameState gameState;
    private final Scanner scanner;

    public GameEngine(GameState gameState) {
        this.gameState = gameState != null ? gameState : GameState.loadLastState();
        this.scanner = new Scanner(System.in);
    }    

    public void logGameState() {
        String report = ReportGenerator.generateOverallReport(gameState);
        Logger.log(report, true);
    }

    @Override
    public void startGame() {
        System.out.println("Welcome to War Games!");
        while (true) {
            displayMenu();
            int choice = getValidatedChoice();
            switch (choice) {
                case 1 -> attack();
                case 2 -> recruitSoldiers();
                case 3 -> performManeuvers();
                case 4 -> saveGame();
                case 5 -> loadGame();
                case 6 -> showStatus();
                case 7 -> {
                    System.out.println("Exiting game...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
            logGameState();
            autoSave();
        }
    }

    private void displayMenu() {
        System.out.println("===================================");
        System.out.println("1. Attack");
        System.out.println("2. Recruit Soldiers");
        System.out.println("3. Perform Maneuvers");
        System.out.println("4. Save Game");
        System.out.println("5. Load Game");
        System.out.println("6. Show Status");
        System.out.println("7. Exit");
        System.out.println("===================================");
        System.out.print("Choose an action: ");
    }    

    public int getValidatedChoice() {
        int choice = scanner.nextInt();
        if (choice < 1 || choice > 6) {
            throw new IllegalArgumentException("Invalid choice.");
        }
        return choice;
    }

    @Override
    public void performManeuvers() {
        General currentGeneral = gameState.getGenerals().get(0);
        System.out.println("Performing maneuvers for " + currentGeneral.getName());
        boolean success = currentGeneral.performManeuvers(currentGeneral.getArmy().getSoldiers());
        if (success) {
            System.out.println("Maneuvers completed successfully.");
        } 
        else {
            System.out.println("Maneuvers failed. Not enough gold.");
        }
    }     

    @Override
    public void attack() {
        General general1 = gameState.getGenerals().get(0);
        General general2 = gameState.getGenerals().get(1);
    
        int strength1 = general1.getArmy().calculateTotalStrength();
        int strength2 = general2.getArmy().calculateTotalStrength();
    
        if (strength1 > strength2) {
            processBattleOutcome(general1, general2);
        } 
        else if (strength1 < strength2) {
            processBattleOutcome(general2, general1);
        } 
        else {
            processTie(general1, general2);
        }
    }
    
    public void processBattleOutcome(General winner, General loser) {
        int goldTransfer = loser.getGold() / 10;
        winner.setGold(winner.getGold() + goldTransfer);
        loser.setGold(loser.getGold() - goldTransfer);
    
        winner.getArmy().getSoldiers().forEach(Soldier::gainExperience);
        loser.getArmy().getSoldiers().forEach(Soldier::loseExperience);
    
        loser.getArmy().removeDeadSoldiers();
    
        System.out.println(winner.getName() + " wygrana bitwa!");
    }
    
    public void processTie(General general1, General general2) {
        System.out.println("To jest remis, obaj generalowie tracja po jednym zolnierzu.");
        removeRandomSoldier(general1);
        removeRandomSoldier(general2);
    }    

    public void removeRandomSoldier(General general) {
        List<Soldier> soldiers = general.getArmy().getSoldiers();
        if (!soldiers.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(soldiers.size());
            soldiers.remove(randomIndex);
        }
    }

    public void recruitSoldiers(SoldierRank rank) {
        General general = gameState.getGenerals().get(0);

        int cost = 10 * rank.getValue();
        if (general.getGold() >= cost) {
            general.getArmy().addSoldier(new Soldier(rank));
            general.setGold(general.getGold() - cost);
            System.out.println("Zolniez zostal zrekrutowany!");
        } else {
            System.out.println("Niewystarczajaco zlota!");
        }
    }

    @Override
    public void recruitSoldiers() {
        System.out.println("Wybierz range zolnierza: 1.PRIVATE, 2.CORPORAL, 3.CAPTAIN, 4.MAJOR");
        int choice = getValidatedChoice();
        if (choice < 1 || choice > 4) {
            System.out.println("Niepoprawny wybór, wybierz ponownie 1,2,3 lub 4.");
            return;
        }

        SoldierRank rank = switch (choice) {
            case 1 -> SoldierRank.PRIVATE;
            case 2 -> SoldierRank.CORPORAL;
            case 3 -> SoldierRank.CAPTAIN;
            case 4 -> SoldierRank.MAJOR;
            default -> throw new IllegalArgumentException("Niewlasciwy wybor.");
        };

        recruitSoldiers(rank);
    }

    @Override
    public void saveGame() {
        System.out.println("zapis gry...");
        gameState.save("StatusGry.txt");
    }

    @Override
    public void loadGame() {
        System.out.println("Wczytywanie gry...");
        this.gameState = new GameState(new ArrayList<>()).load("StatusGry.txt");
    }

    @Override
    public void showStatus() {
        System.out.println("Status gry:");
        System.out.println(gameState);
    }

    public void autoSave() {
        saveGame();
    }
}