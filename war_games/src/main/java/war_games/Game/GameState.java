package war_games.Game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import war_games.Generals.Army;
import war_games.Generals.General;
import war_games.Generals.Soldier;

public class GameState implements Serializable, Data {
    private static final long serialVersionUID = 1L;
    private List<General> generals;

    public GameState(List<General> generals) {
        if (generals == null || generals.size() != 2) {
            throw new IllegalArgumentException("Musi byc  Generalow.");
        }
        this.generals = generals;
    }

    public List<General> getGenerals() {
        return generals;
    }

    public static GameState load(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Object obj = ois.readObject();
            if (obj instanceof GameState) {
                GameState loadedState = (GameState) obj;
                validateGameState(loadedState);
                return loadedState;
            } 
            else {
                throw new ClassNotFoundException("Blad.");
            }
        } 
        catch (IOException | ClassNotFoundException e) {
            System.out.println("Nie znaleziono poprzednich danych. Start nowej gry.");
            return new GameState(new ArrayList<>());
        }
    }

    public static void validateGameState(GameState gameState) {
        if (gameState.getGenerals().size() != 2) {
            throw new IllegalArgumentException("Nieprawna liczba Generalow, musi byc 2.");
        }
        for (General general : gameState.getGenerals()) {
            if (general.getGold() < 0) {
                throw new IllegalArgumentException("Zloto jakie posiada General nie moze byc ujemne.");
            }
            for (Soldier soldier : general.getArmy().getSoldiers()) {
                if (soldier.getExperience() < 0) {
                    throw new IllegalArgumentException("Dosiwadczneie zolnierza nie moze byc ujemne.");
                }
                if (soldier.getRank() == null) {
                    throw new IllegalArgumentException("Zolnierz nie moze miec wartosci null.");
                }
            }
        }
    }

    public static GameState loadLastState() {
        try {
            return new GameState(List.of(new General(" General 1", 100, new Army()),
                                        new General(" General 2", 100, new Army())));
        } 
        catch (Exception e) {
            System.out.println("Nie znaleziono poprzednich danych. Start nowej gry.");
            return new GameState(List.of(new General(" General 1", 100, new Army()),
                                        new General(" General 2", 100, new Army())));
        }
    }

    @Override
    public void save(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
            System.out.println("Zapisano stan gry do: " + fileName);
        } 
        catch (IOException e) {
            System.out.println("Bledy przeslano do: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Generals: " + generals;
    }
}