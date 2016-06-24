
package demineurjavafx.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Plateau2D extends Plateau implements PlateauCroix {

    private HashMap<Case, int[]> gridCoordinates;
    private Case[][] gridCases;
    
    private boolean caseCroix = false;
    
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
        // Initialisation des propriétés
        gridCases = new Case[size.getX()][size.getY()];
        gridCoordinates = new HashMap();

        for(int x = 0; x < size.getX(); x++)
        {
            for(int y = 0; y < size.getY(); y++)
            {
                boolean trapped = Math.random() < difficulty.getPercentage();
                if(trapped) nbMines++;
                
                Case c = new Case(trapped);
                gridCoordinates.put(c, new int[]{x,y});
                gridCases[x][y] = c;
            }
        }
        if(caseCroix) this.initializeCaseCroix();
        nbMinesLeft = nbMines;
                        
        this.setChanged();
        this.notifyObservers();
    }
    
    @Override
    public List<Case> getNeighbors(Case c)
    {
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
        return getNeighbors(c, v);
    }
    
    @Override
    public void propagateExplosion()
    {
        for(int x = 0; x < size.getX(); x++)
        {
            for(int y = 0; y < size.getY(); y++)
            {
                if(gridCases[x][y].isTrapped()) gridCases[x][y].makeVisible();
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

    @Override
    public List<Case> getCrossNeighbors(Case c) {
        
        int[] coordinates = gridCoordinates.get(c);
        List<Case> neighbors = new ArrayList();
        
        int[] indices = new int[]{
            0, 1, // haut
            -1, 0, // gauche
            1, 0, // droite
            0, -1 // bas
        };
        
        for(int i = 0; i < indices.length; i++)
        {
            int dx = indices[i];
            int dy = indices[++i];
            
            int vx = coordinates[0]+dx;
            int vy = coordinates[1]+dy;
            
            while(vx >= 0 && vx < size.getX() && vy >= 0 && vy < size.getY())
            {
                neighbors.add(gridCases[vx][vy]);
                vx = coordinates[0]+dx;
                vy = coordinates[1]+dy;
            }
        }
        return neighbors;
    }
    
    /**
     * Retourne les voisins d'une case en respectant un certain pattern
     * Utiliser seulement avec Récursivité !!
     * @param c Case
     * @param indices Indices relatifs des voisins que l'on souhaite récupérer
     * @return Listes des voisins correspondant au pattern d'indice
     */
    private List<Case> getNeighbors(Case c, int[] indices)
    {
        int[] coordinates = gridCoordinates.get(c);
        
        List<Case> neighbors = new ArrayList();
        for(int i = 0; i < indices.length; i++)
        {
            gridCoordinates.values();
            int dx = indices[i];
            int dy = indices[++i];
            
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
    public void setCaseCroix(boolean value) {
        this.caseCroix = value;
    }
    
    // On renvoie un entier aléatoire entre une valeur min (incluse)
    // et une valeur max (exclue).
    // Attention : si on utilisait Math.round(), on aurait une distribution
    // non uniforme !
    private int getRandomIntExclusive(int min, int max) {
        double value = Math.floor(Math.random() * (max - min)) + min;
        return (int)value;
    }

    @Override
    public void initializeCaseCroix() {
//        List<Case> cases = new ArrayList<>();
//        for(gridCoordinates.)
//        {
//
//        }
        int a, b, x = size.getX();

//        {
            a = getRandomIntExclusive(0, x);
            b = getRandomIntExclusive(0, x);
//        } 


        // Si la case choisie pour être une croix est une piégée,
        // on décrémente le nombre de mines du jeu
        if(gridCases[a][b].isTrapped()) nbMines -= 1;
        CaseCroix cc = new CaseCroix();

        // Remplacement des références
        gridCoordinates.remove(gridCases[a][b]);
        gridCoordinates.put(cc, new int[]{a,b});
        gridCases[a][b] = cc;
    }
}
