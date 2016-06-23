
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
    
    abstract void propagate();

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
    
    public void propagateClick() {
        this.propagate();
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Initialise les voisins et la valeur de la case.
     * @param neighbors 
     */
    public void initializeCaseNeighbors(List<? extends Case> neighbors)
    {
        this.neighbors = neighbors;
        this.calculateValue();
    }
    
    /**
     * Calcule le nombre de voisines minées de la case si la case elle-même n'est pas piégée.
     */
    private void calculateValue() {
        if(value != -1)
        {
            long longValue = neighbors.stream().filter(t -> t.getValue() == -1).count();
            value = (int)longValue;
        }
    }
}
