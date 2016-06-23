
package demineurjavafx.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Plateau2D extends Plateau {

    // HashMap
    private Case2D[][] grille;
    
    public Plateau2D() {
        super();
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
