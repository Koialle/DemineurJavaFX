
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
    protected boolean interrogation = false;
    
    protected List<? extends Case> neighbors = new ArrayList();
    
    /* Constructeur(s) */
    
    public Case(boolean trapped)
    {
        this.trapped = trapped;
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
        if(!this.flaged && !this.interrogation){
            // Affichage de la case si elle est piégée
            if(this.trapped){
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
     * Rend la case visible si elle ne l'était pas déjà et si elle n'a pas de drapeau.
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
    public boolean putFlagOrInterrogation()
    {
        if(!this.visible)
        {
            if(this.flaged) {
                this.interrogation = true;
                this.flaged = false;
            } else if(this.interrogation) {
                this.interrogation = false;
            } else {
                this.flaged = true;
            }
            
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

    public boolean isInterrogation() {
        return interrogation;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.setChanged();
        this.notifyObservers();
    }
    
    public void setFlaged(boolean flaged) {
        this.flaged = flaged;
        this.setChanged();
        this.notifyObservers();
    }
    
    public List<? extends Case> getNeighbors()
    {
        return neighbors;
    }

    public void setNeighbors(List<? extends Case> neighbors) {
        this.neighbors = neighbors;
    }
}
