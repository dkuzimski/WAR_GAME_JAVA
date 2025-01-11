package war_games.Game;

import war_games.Generals.General;
import war_games.Generals.Soldier;

public class ReportGenerator {

    public static String generateReport(General general) {
        if (general == null) {
            throw new IllegalArgumentException("General nie moze miec wartosci null.");
        }

        StringBuilder report = new StringBuilder();
        report.append("General: ").append(general.getName()).append("\n");
        report.append("Zloto: ").append(general.getGold()).append("\n");
        report.append("Armia:\n");

        for (Soldier soldier : general.getArmy().getSoldiers()) {
            report.append(" - Ranga: ").append(soldier.getRank())
                  .append(", Doswiadcznie: ").append(soldier.getExperience())
                  .append(", Sila: ").append(soldier.getStrength())
                  .append("\n");
        }

        return report.toString();
    }

    public static String generateOverallReport(GameState gameState) {
        if (gameState == null) {
            throw new IllegalArgumentException("Status gry nie moze byc null.");
        }

        StringBuilder report = new StringBuilder();
        report.append("===== Raport Gry =====\n");
        for (General general : gameState.getGenerals()) {
            report.append(generateReport(general)).append("\n");
        }
        return report.toString();
    }

    public static String generateReport_All(GameState gameState) {
        throw new UnsupportedOperationException("Niezainplementowana metoda...'");
    }
}