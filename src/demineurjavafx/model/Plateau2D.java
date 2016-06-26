
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
    private int sizeX, sizeY;
    
    public Plateau2D() {
        super();
    }
    
    public Plateau2D(Size size, Difficulty difficulty)
    {
        this();
        this.size = size;
        this.initializePlateauSize();
        this.difficulty = difficulty;
    }
    
    @Override
    public void initializePlateau() {
        // Initialisation des propriétés
        gridCases = new Case[sizeX][sizeY];
        gridCoordinates = new HashMap();

        for(int x = 0; x < sizeX; x++)
        {
            for(int y = 0; y < sizeY; y++)
            {
                boolean trapped = Math.random() < difficulty.getPercentage();
                if(trapped) nbMines++;
                
                Case c = new Case(trapped);
                gridCoordinates.put(c, new int[]{x,y});
                gridCases[x][y] = c;
            }
        }

        if(this.caseCroix) this.initializeCaseCroix();
        nbMinesLeft = nbMines; // updateNbMinesLeft
        
        this.setChanged();
        this.notifyObservers(this);
    }
    
    @Override
    public List<Case> getNeighbors(Case c)
    {
        if(c instanceof CaseCroix) 
            return this.getCrossNeighbors(c);
        
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
        for(int x = 0; x < sizeX; x++)
        {
            for(int y = 0; y < sizeY; y++)
            {
                if(gridCases[x][y].isTrapped() || (caseCroix && gridCases[x][y] instanceof CaseCroix)) gridCases[x][y].makeVisible(); // setChanged() et notify() dans Case.makeVisible, et plateau observe les cases
            }
        }
        this.gameState = GameState.Lost;
        this.setChanged();
        this.notifyObservers();
    }

    @Override
    public void updateNbMinesLeft()
    {
        int i = 0;
        for(int x = 0; x < sizeX; x++)
        {
            for(int y = 0; y < sizeY; y++)
            {
                if(gridCases[x][y].isFlaged()) i++;
            }
        }
        nbMinesLeft = nbMines - i;
        
        this.setChanged();
        this.notifyObservers(null);
    }
    
    @Override
    protected int getNbCaseVisibleOrFlaged()
    {
        int i = 0;
        for(int x = 0; x < sizeX; x++)
        {
            for(int y = 0; y < sizeY; y++)
            {
                if(gridCases[x][y].isFlaged() || gridCases[x][y].isVisible()) i++;
            }
        }
        return i;
    }
    
    public Case[][] getGrille() {
        return gridCases;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
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
            
            if(vx >= 0 && vx < sizeX && vy >= 0 && vy < sizeY)
            {
                neighbors.add(gridCases[vx][vy]);
            }
        }
        return neighbors;
    }
    
    @Override
    public void updateGameStateIfWin(){
        // Vérification que la partie est gagnée.
        int nbCasesPlateau = sizeX * sizeY;
        if(this.getNbCaseVisibleOrFlaged() == nbCasesPlateau && this.getNbMinesLeft() == 0) this.gameState = GameState.Win;
        this.setChanged();
        this.notifyObservers(null);
    }

    /* Implémentation de PlateauCroix (extension) */

    @Override
    public void initializeCaseCroix() {
        
        CaseCroix cc = new CaseCroix();
//        Case[][] possibleCaseCroix;
        int a, b;
        
//        // Réccupération des cases non minées et non vides
//        possibleCaseCroix = new Case[sizeX][sizeY];
//        gridCoordinates.entrySet().stream().filter((entry) -> (!entry.getKey().isTrapped() && !entry.getKey().isEmpty())).forEach((entry) -> {
//            int x = entry.getValue()[0];
//            int y = entry.getValue()[1];
//            possibleCaseCroix[x][y] = entry.getKey();
//        });
        
        // Génération d'index aléatoires
        a = getRandomIntExclusive(0, sizeX);
        b = getRandomIntExclusive(0, sizeY);

        // Remplacement des références
//        gridCoordinates.remove(possibleCaseCroix[a][b]);
        if(gridCases[a][b].isTrapped()) nbMines--;
        gridCoordinates.remove(gridCases[a][b]);
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
            
//            if(vx >= 0 && vx < sizeX && vy >= 0 && vy < sizeY)
//            {
//                neighbors.add(gridCases[vx][vy]);
//            }
            
            while(vx >= 0 && vx < sizeX && vy >= 0 && vy < sizeY)
            {
                neighbors.add(gridCases[vx][vy]);
                vx += dx;
                vy += dy;
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

    private void initializePlateauSize() {
        switch(this.size)
        {
            case Medium:
                sizeX = 15;
                sizeY = 10;
                break;
            case Big:
                sizeX = 15;
                sizeY = 15;
                break;
            case Large:
                sizeX = 20;
                sizeY = 20;
                break;
            default:
                sizeX = 10;
                sizeY = 10;
                break;
        }
    }
}
