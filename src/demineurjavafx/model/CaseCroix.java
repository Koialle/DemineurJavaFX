
package demineurjavafx.model;

/**
 *
 * @author Ophélie EOUZAN
 */
public class CaseCroix extends Case {

    public CaseCroix() {
        super(false);
    }
    
    @Override
    protected void propagate()
    {
        // Affichage de la case
        if(!flaged && this.makeVisible())
        {
            neighbors.stream().filter((neighbor) -> (!neighbor.isVisible())).forEach((neighbor) -> {
                if(neighbor.isTrapped()) neighbor.putFlag();
                else neighbor.makeVisible();
            });
        }
    }
    
    @Override
    public boolean makeVisible() { // Utiliser un indice de propagation pour connaitre qui est déclencheur : boolean triggerVisible
//        if(!this.flaged && !this.visible)
//        {
//            this.visible = true;
//            this.setChanged();
//            this.notifyObservers();
//            return true;
//        }
        return false;
    }
}
