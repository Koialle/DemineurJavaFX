
package demineurjavafx.model;

/**
 *
 * @author Ophélie EOUZAN
 */
public class CaseCroix extends Case {

    public CaseCroix() {
        super(0); // Pas de valeur
    }
    
    @Override
    protected void propagate()
    {
        // Affichage de la case
        if(!flaged && this.makeVisible())
        {
            neighbors.stream().filter((neighbor) -> (!neighbor.isVisible() && neighbor.getValue()!= -1)).forEach((neighbor) -> {
                neighbor.propagate();
            });
        }
    }
    
}
