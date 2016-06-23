
package demineurjavafx.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Plateau2D extends Plateau {

    private HashMap<Case, int[]> gridCoordinates;
    private Case[][] gridCases;
    
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
        gridCases = new Case[size.getX()][size.getY()];
        gridCoordinates = new HashMap();//new Case[size.getX()][size.getY()];
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
                Case c = new Case(value);
                gridCoordinates.put(c, new int[]{x,y});
                gridCases[x][y] = c;
            }
        }
        nbMinesLeft = nbMines;
                        
        this.setChanged();
        this.notifyObservers();
    }
    
    @Override
    public List<Case> getNeighbors(Case c)
    {
        int[] coordinates = gridCoordinates.get(c);
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
            
            int vx = coordinates[0]+dx;
            int vy = coordinates[1]+dy;
            
            if(vx >= 0 && vx < size.getX() && vy >= 0 && vy < size.getY())
            {
                neighbors.add(gridCases[vx][vy]);
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
                if(gridCases[x][y].getValue() == -1) gridCases[x][y].makeVisible();
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
                if(gridCases[x][y].isFlaged()) i++;
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
                if(gridCases[x][y].isFlaged() || gridCases[x][y].isVisible()) i++;
            }
        }
        return i;
    }
    
    public Case[][] getGrille() {
        return gridCases;
    }
}
