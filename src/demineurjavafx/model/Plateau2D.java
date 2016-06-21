
package demineurjavafx.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Plateau2D extends Plateau {

    private Case2D[][] grille;
    
    private List<int[]> notNeighbors; // Permet d'optimiser l'agorithme de découvrement des voisins.
    
    public Plateau2D() {
        super();
        notNeighbors = new ArrayList();
        notNeighbors.add(new int[]{-1,1});
        notNeighbors.add(new int[]{1,1});
        notNeighbors.add(new int[]{1,-1});
        notNeighbors.add(new int[]{-1,-1});
//        timer = new Timer(true);
    }
    
    public Plateau2D(Size size, Difficulty difficulty)
    {
        this();
        this.size = size;
        this.difficulty = difficulty;
    }
    
    @Override
    public void initializePlateau() {
        grille = new Case2D[size.getX()][size.getY()];
        for(int x = 0; x < size.getX(); x++)
        {
            for(int y = 0; y < size.getY(); y++)
            {
                int value = 0;
                boolean hasBomb = Math.random() < difficulty.getPercentage();
                
                if(hasBomb){
                    value = -1;
                    nbMines++;
                }
                Case2D c = new Case2D(x, y, value);
                grille[x][y] = c;
            }
        }
        nbMinesLeft = nbMines;
                        
        this.setChanged();
        this.notifyObservers();
    }
    
    @Override
    public List<Case> getNeighbors(Case c)
    {
        int x = ((Case2D)c).getX(), y = ((Case2D)c).getY();
        List<Case> neighbors = new ArrayList();
        int[] v = new int[]{
            -1, 1,
            0, 1,
            1, 1,
            -1, 0,
            0, 0,
            1, 0,
            -1, -1,
            0, -1,
            1, -1
        };
        
        for(int i = 0; i < v.length; i++)
        {
            int dx = v[i];
            int dy = v[++i];
            
            int vx = x+dx;
            int vy = y+dy;
            
            if(vx >= 0 && vx < size.getX() && vy >= 0 && vy < size.getY())
            {
                neighbors.add(grille[vx][vy]);
            }
        }
        return neighbors;
    }

    @Override
    public void propagateClick(Case c) {
        // Si la case est piégée, l'explosion est propagée
        Case2D c2D = (Case2D)c;
        if(!c2D.isFlaged())
        {
            if(c2D.getValue() == -1)
            {
                c2D.setValue(-2);
                c2D.makeVisible();
                this.propagateExplosion();
            }
            // Sinon on propage seulement les cases normalement
            else if(c2D.makeVisible() && c2D.getValue() == 0)
            {
                List<Case2D> neighbors = (List<Case2D>)c2D.getNeighbors();
                neighbors.removeIf(t -> notNeighbors.contains(new int[]{t.getX(), t.getY()}));
                neighbors.stream().filter((neighbor) -> (!neighbor.isVisible() && neighbor.getValue()!= -1)).forEach((neighbor) -> {
                    this.propagateClick(neighbor);
                });
            }
        }
    }
    
    @Override
    public void propagateExplosion()
    {
        for(int x = 0; x < size.getX(); x++)
        {
            for(int y = 0; y < size.getY(); y++)
            {
                if(grille[x][y].getValue() == -1) grille[x][y].makeVisible();
            }
        }
    }

    @Override
    public void updateNbMinesLeft()
    {
        int i = 0;
        for(int x = 0; x < size.getX(); x++)
        {
            for(int y = 0; y < size.getY(); y++)
            {
                if(grille[x][y].isFlaged()) i++;
            }
        }
        nbMinesLeft = nbMines - i;
        
//        this.setChanged();
//        this.notifyObservers();
    }
    
    @Override
    public int getNbCaseVisibleOrFlaged()
    {
        int i = 0;
        for(int x = 0; x < size.getX(); x++)
        {
            for(int y = 0; y < size.getY(); y++)
            {
                if(grille[x][y].isFlaged() || grille[x][y].isVisible()) i++;
            }
        }
        return i;
    }
    
    public Case[][] getGrille() {
        return grille;
    }
}
