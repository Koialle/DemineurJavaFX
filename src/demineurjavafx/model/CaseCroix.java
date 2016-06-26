
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
        if(!flaged && !visible)
        {
            this.makeVisible();
            neighbors.stream().filter((neighbor) -> (!neighbor.isVisible())).forEach((neighbor) -> {
                if(neighbor.isTrapped()) {
                    neighbor.setFlaged(true);
                } else neighbor.makeVisible();
            });
            this.setChanged();
            this.notifyObservers();
        }
    }
}
