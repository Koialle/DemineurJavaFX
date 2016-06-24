
package demineurjavafx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Case extends Observable 
{    
    protected boolean trapped;
    protected int value = 0;
    protected boolean flaged = false;
    protected boolean visible = false;
    protected boolean trigger = false;
    
    protected List<? extends Case> neighbors;
    
    /* Constructeur(s) */
    
    public Case(boolean trapped)
    {
        this.trapped = trapped;
        
        this.value = 0;
        this.flaged = false; // Une case marquée ne peut être découverte ?
        this.visible = false;
        this.neighbors = new ArrayList();
    }
    
    /* Méthodes */
    
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
    protected void calculateValue() {
        if(!this.trapped)
        {
            long longValue = neighbors.stream().filter(t -> t.isTrapped()).count();
            value = (int)longValue;
        }
    }
    
    /**
     * Propage le clic récursivement puis notification aux observers.
     */
    public void propagateClick() {
        this.propagate();
        this.setChanged();
        this.notifyObservers();
    }
    
    /**
     * Propagation récursive du dévoilement des cases voisines.
     */
    protected void propagate()
    {
        if(!this.flaged)
        {
            // Affichage de la case si elle est piégée
            if(this.trapped)
            {
                this.trigger = true; // Case déclencheuse
                this.makeVisible();
            }
            // Sinon on propage seulement les cases normalement
            else if(this.makeVisible() && this.value == 0)
            {
                neighbors.stream().filter((neighbor) -> (!neighbor.isVisible() && !neighbor.isTrapped())).forEach((neighbor) -> {
                    neighbor.propagate();
                });
            }
        }
    }
    
    /**
     * Rend la case visible si elle ne l'était pas déjà et si elle n'a ps de drapeau.
     * @return true si la case est devenue visible, false sinon
     */
    protected boolean makeVisible() {
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
    
    /* Getters - Setters */
    
    public boolean isEmpty()
    {
        return (value == 0);
    }
    
    public boolean isTrapped()
    {
        return this.trapped;
    }
    
    public boolean isTrigger()
    {
        return this.trigger;
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
}
