
package demineurjavafx.model;

import java.util.List;

/**
 *
 * @author Ophélie EOUZAN
 */
public interface PlateauCroix {

    /**
     * Permet d'activer ou non l'extension
     * @param value 
     */
    public void setCaseCroix(boolean value);
    
    /**
     * Permet de voir si l'extension est activée
     * @return boolean
     */
    public boolean getCaseCroix();
    
    /**
     * Génère la case spéciale croix
     */
    void initializeCaseCroix();
    
    /**
     * Retourne les voisines en croix
     * @param c
     * @return 
     */
    public List<Case> getCrossNeighbors(Case c);
}
