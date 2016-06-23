
package demineurjavafx.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Case2D extends Case {
    private final int x, y;
    private final List<int[]> notNeighbors; // Permet d'optimiser l'agorithme de découvrement des voisins.
        
    public Case2D(int x, int y, int value)
    {
        super(value);
        this.x = x;
        this.y = y;
        
        neighbors = new ArrayList();
        
        // NotNeighbors contient les coordonnées des voisins
        // à ignorer dans l'algorithme de découvrement des cases.
        // Spécifique au plateau2D
        notNeighbors = new ArrayList(); 
        notNeighbors.add(new int[]{-1,1});
        notNeighbors.add(new int[]{1,1});
        notNeighbors.add(new int[]{1,-1});
        notNeighbors.add(new int[]{-1,-1});
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    void propagate() 
    {
        if(!flaged)
        {
            // Affichage de la case si elle est piégée
            if(this.getValue() == -1)
            {
                value = -2;
                this.makeVisible();
            }
            // Sinon on propage seulement les cases normalement
            else if(this.makeVisible() && value == 0)
            {
                List<Case2D> caseNeighbors = (List<Case2D>)(neighbors);
                
                // NotNeighbors permet de propager seulement sur les voisines HAUT, BAS, DROITE, GAUCHE de la case
                caseNeighbors.removeIf(t -> notNeighbors.contains(new int[]{t.getX(), t.getY()}));
                caseNeighbors.stream().filter((neighbor) -> (!neighbor.isVisible() && neighbor.getValue()!= -1)).forEach((neighbor) -> {
                    neighbor.propagateClick();
                });
            }
        }
    }
}
