
package demineurjavafx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Ophélie EOUZAN
 */
public abstract class Case extends Observable {
    protected int value;
    protected boolean flaged;
    protected boolean visible;
    
    protected List<? extends Case> neighbors;
    
    public Case(int value)
    {
        this.value = value;
        
        this.flaged = false; // Une case marquée ne peut être découverte ?
        this.visible = false;
        this.neighbors = new ArrayList();
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

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    public List<? extends Case> getNeighbors()
    {
        return neighbors;
    }

    public void setNeighbors(List<? extends Case> neighbors) {
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
