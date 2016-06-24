
package demineurjavafx.model;

import java.util.List;

/**
 *
 * @author Ophélie EOUZAN
 */
public interface PlateauCroix {

    /**
     * Retourne les voisines en croix
     * @param c
     * @return 
     */
    public List<Case> getCrossNeighbors(Case c);
    
    public void setCaseCroix(boolean value);
    
    void initializeCaseCroix();
}
