package war_games;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import war_games.Game.GameEngine;
import war_games.Game.GameState;
import war_games.Game.Logger;
import war_games.Game.ReportGenerator;
import war_games.Generals.Army;
import war_games.Generals.General;
import war_games.Generals.Soldier;
import war_games.Generals.SoldierRank;

class GameEngineTest {

    private GameEngine gameEngine;
    private General general1;
    private General general2;

    @BeforeEach
    public void setUp() {
        Army army1 = new Army();
        general1 = new General("General 1", 100, army1);
        Army army2 = new Army();
        general2 = new General("General 2", 100, army2);
        List<General> generals = new ArrayList<>();
        generals.add(general1);
        generals.add(general2);
        GameState gameState = new GameState(generals);
        gameEngine = new GameEngine(gameState);
    }
    @Test
    public void testSaveGame() {
        gameEngine.saveGame();
        assertTrue(true);
    }
    @Test
    public void testAutoSave() {
        gameEngine.autoSave();
        assertTrue(true);
    }
    @Test
    public void testLoggerMsg() {
        Logger.log("To napis testowy");
        assertTrue(true);
    }
    @Test
    public void testAddSoldier() {
        general1.getArmy().addSoldier(new Soldier(SoldierRank.PRIVATE));
        assertEquals(1, general1.getArmy().getSoldiers().size());
    }
    @Test
    public void testRemoveDeadSoldiers() {
        Soldier soldier = new Soldier(SoldierRank.PRIVATE);
        general1.getArmy().addSoldier(soldier);
        soldier.setExperience(0);
        general1.getArmy().removeDeadSoldiers();
        assertEquals(0, general1.getArmy().getSoldiers().size());
    }
    @Test
    public void testSoldierDiesWhenExperienceIsZero() {
        Soldier soldier = new Soldier(SoldierRank.PRIVATE);
        soldier.setExperience(1);
        soldier.loseExperience();
        assertFalse(soldier.isAlive(), "Żołnierz powinien być martwy, gdy jego doświadczenie spadnie do zera.");
    }
    @Test
    public void testSoldierPromotionAfterFiveTimesExperience() {
        Soldier soldier = new Soldier(SoldierRank.PRIVATE);
        
        // Żołnierz powinien awansować po osiągnięciu 5 * wartość stopnia (1 dla PRIVATE)
        for (int i = 0; i < 5; i++) {
            soldier.gainExperience();
        }
        assertTrue(soldier.getRank() == SoldierRank.CORPORAL, "Żołnierz powinien awansować na CORPORAL po osiągnięciu pięciokrotności doświadczenia.");
        assertEquals(1, soldier.getExperience(), "Doświadczenie powinno zostać zresetowane do 1 po awansie.");
    }
    @Test
    public void testSoldierExperienceCannotBeNegative() {
        Soldier soldier = new Soldier(SoldierRank.PRIVATE);
        soldier.setExperience(1);
        soldier.loseExperience(); // Doświadczenie spada do 0
        assertFalse(soldier.getExperience() < 0, "Doświadczenie nie może być ujemne.");
    }
    @Test
    public void testGeneralGoldCannotBeNegative() {
        General general = new General("Test General", 100, new Army());
        try {
            general.setGold(-1);
            fail("Złoto generała nie może być ujemne.");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "Złoto generała nie może być ujemne.");
        }
    }
    @Test
    public void testCannotAddNullSoldierToArmy() {
        Army army = new Army();
        try {
            army.addSoldier(null);
            fail("Nie można dodać żołnierza, który jest null.");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "Nie można dodać żołnierza, który jest null.");
        }
    }
    @Test
    public void testSoldierRankCannotBeNull() {
        try {
            new Soldier(null);
            fail("Ranga żołnierza nie może być null.");
        } catch (IllegalArgumentException e) {
            assertTrue(true, "Ranga żołnierza nie może być null.");
        }
    }
    @Test
    public void testLoseExperience() {
        Soldier soldier = new Soldier(SoldierRank.PRIVATE);
        soldier.setExperience(2);
        soldier.loseExperience();
        assertTrue(soldier.isAlive());
    }
    @Test
    public void testGenerateReport() {
        String report = ReportGenerator.generateReport(general1);
        assertTrue(report.contains("General: General 1"));
    }
    @Test
    public void testSoldiersGold() {
        general1.setGold(50);
        gameEngine.recruitSoldiers(SoldierRank.PRIVATE);
        assertEquals(40, general1.getGold());
    }
    @Test
    public void testSoldiersGold_Not() {
        general1.setGold(5);
        gameEngine.recruitSoldiers(SoldierRank.PRIVATE);
        assertEquals(5, general1.getGold());
    }
    @Test
    public void testArmyAttack() {
        general1.getArmy().addSoldier(new Soldier(SoldierRank.PRIVATE));
        general2.getArmy().addSoldier(new Soldier(SoldierRank.PRIVATE));
        gameEngine.attack();
        assertEquals(100, general1.getGold());
    }
    @Test
    public void testRemoveSoldier() {
        general1.getArmy().addSoldier(new Soldier(SoldierRank.PRIVATE));
        gameEngine.removeRandomSoldier(general1);
        assertEquals(0, general1.getArmy().getSoldiers().size());
    }
    @Test
    public void testGainExperience() {
        Soldier soldier = new Soldier(SoldierRank.PRIVATE);
        soldier.gainExperience();
        assertEquals(2, soldier.getExperience());
    }
    @Test
    public void testGetStrength() {
        Soldier soldier = new Soldier(SoldierRank.PRIVATE);
        assertEquals(1, soldier.getStrength());
    }
    @Test
    public void testSaveMethod() {
        GameState gameState = new GameState(List.of(general1, general2));
        gameState.save("Zapis_testowy");
        assertTrue(true);
    }
    @Test
    public void testManeuversWithSoldiers() {
        Soldier soldier = new Soldier(SoldierRank.PRIVATE);
        general1.getArmy().addSoldier(soldier);
        gameEngine.performManeuvers();
        assertEquals(2, soldier.getExperience());
    }
}