
package demineurjavafx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Case extends Observable {
    private final int x, y;
    private int value;
    private boolean flaged;
    private boolean visible;
    
    private List<Case> neighbors;
    
    public Case(int x, int y, int value)
    {
        this.x = x;
        this.y = y;
        this.value = value;
        
        this.flaged = false; // Une case marquée ne peut être découverte ?
        this.visible = false;
        this.neighbors = new ArrayList();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFlaged() {
        return flaged;
    }

    public boolean isVisible() {
        return visible;
    }
    
    public List<Case> getNeighbors()
    {
        return neighbors;
    }

    public void setNeighbors(List<Case> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * Rend la case visible si elle ne l'était pas déjà et si elle n'a ps de drapeau.
     * @return true si la case est devenue visible, false sinon
     */
    public boolean makeVisible() {
        if(!this.flaged && !this.visible)
        {
            this.visible = true;
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }
    
    /**
     * Pose un drapeau si la case n'en a pas, enlève le drapeau si elle en a un.
     * @return true si un drapeau a été mis, false sinon
     */
    public boolean putFlag()
    {
        if(!this.visible)
        {
            this.flaged = !this.flaged;
            this.setChanged();
            this.notifyObservers();
            return true;
        }
        return false;
    }
}
