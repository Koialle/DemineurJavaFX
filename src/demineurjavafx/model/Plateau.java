
package demineurjavafx.model;

import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Ophélie EOUZAN
 */
abstract public class Plateau extends Observable implements PlateauCroix {
    
    private boolean caseCroix = false;
    
    protected int secondesEcoulees = 0;
    protected int nbMines = 0;
    protected int nbMinesLeft = 0;
    
    protected GameState gameState = GameState.Playing;
    protected Difficulty difficulty = Difficulty.Easy;
    protected Size size = Size.Small;
    
    protected Timer timer;

    public static enum GameState
    {
        None,
        Playing,
        Lost,
        Win
    }
    
    public static enum Difficulty
    {
        Easy(0.1),
        Medium(0.2),
        Difficult(0.3),
        Hard(0.4);

        private final double percentage;
        Difficulty(double percentage) {
            this.percentage = percentage;
        }
        public double getPercentage() {
            return percentage;
        }
    }
    
    public static enum Size
    {
        Small(10,10),
        Medium(15,10),
        Big(15,15),
        Large(20,20);

        private final int x;
        private final int y;
        Size(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX()
        {
            return x;
        }
        public int getY()
        {
            return y;
        }
    }
    
    abstract public void initializePlateau();
    abstract public List<Case> getNeighbors(Case c);
    abstract public void propagateExplosion();
    abstract public void updateNbMinesLeft();
    abstract public int getNbCaseVisibleOrFlaged();

    public int getSecondesExoulees() {
        return secondesEcoulees;
    }

    public int getNbMines() {
        return nbMines;
    }

    public int getNbMinesLeft() {
        return nbMinesLeft;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Size getSize() {
        return size;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        this.setChanged();
        this.notifyObservers();
    }
    
    public void startTimer()
    {
        timer = new Timer(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(gameState == GameState.Playing)
                {
                    secondesEcoulees++;
//                    setChanged();
//                    notifyObservers();
                }
                else timer.cancel();
            }
        }, 0, 1000);
    }

    /**
     * Permet de récupérer le temps écoulé depuis le start au format MM:SS (ex : 00:06 | 01:42 | 12:21)
     * @return le temps écoulé au format string
     */
    public String getMinuteSecondeFormat()
    {
        long minute = secondesEcoulees / 60;
        long seconde = secondesEcoulees % 60;
        
        String min = "", sec = "";

        if (minute < 10) min += "0";
        if (seconde < 10) sec += "0";
        
        return min+minute + ":" + sec+seconde;	
    }
    
    @Override
    public boolean getCaseCroix() {
        return this.caseCroix;
    }
    
    @Override
    public void setCaseCroix(boolean value) {
        this.caseCroix = value;
    }
}
