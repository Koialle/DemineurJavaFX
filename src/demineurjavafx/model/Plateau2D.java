
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

    /* Implémentation de PlateauCroix (extension) */

    @Override
    public void initializeCaseCroix() {
        
        CaseCroix cc = new CaseCroix();
        Case[][] possibleCaseCroix;
        int a, b, xSize = size.getX(), ySize = size.getY();
        
        // Réccupération des cases non minées et non vides
        possibleCaseCroix = new Case[xSize][ySize];
        gridCoordinates.entrySet().stream().filter((entry) -> (!entry.getKey().isTrapped() && !entry.getKey().isEmpty())).forEach((entry) -> {
            int x = entry.getValue()[0];
            int y = entry.getValue()[1];
            possibleCaseCroix[x][y] = entry.getKey();
        });
        
        // Génération d'index aléatoires
        a = getRandomIntExclusive(0, xSize);
        b = getRandomIntExclusive(0, ySize);

        // Remplacement des références
        gridCoordinates.remove(possibleCaseCroix[a][b]);
        gridCoordinates.put(cc, new int[]{a,b});
        gridCases[a][b] = cc;
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
    
    // On renvoie un entier aléatoire entre une valeur min (incluse)
    // et une valeur max (exclue).
    // Attention : si on utilisait Math.round(), on aurait une distribution
    // non uniforme !
    private int getRandomIntExclusive(int min, int max) {
        double value = Math.floor(Math.random() * (max - min)) + min;
        return (int)value;
    }
}
