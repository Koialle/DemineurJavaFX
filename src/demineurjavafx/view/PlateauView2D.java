
package demineurjavafx.view;

import demineurjavafx.model.Case;
import demineurjavafx.model.Case2D;
import demineurjavafx.model.Plateau.GameState;
import demineurjavafx.model.Plateau2D;
import java.util.Observable;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Ophélie EOUZAN
 */
public class PlateauView2D extends PlateauView {

    private GridPane gridboard; // Composant propre à un grille 2D, pas valable pour une grille 3D
    
    public PlateauView2D(Plateau2D plateau)
    {
        super(plateau);
        width = plateau.getSize().getX() * CASE_SIZE;
        height = plateau.getSize().getY() * CASE_SIZE;
    }
    
    @Override
    protected void createView()
    {
        super.createView();
        gridboard = new GridPane();
        gridboard.setAlignment(Pos.CENTER);
        gridboard.setPrefSize(width, height);
        
        int xCases = plateau.getSize().getX();
        int yCases = plateau.getSize().getY();
        Case2D[][] grilleCase = (Case2D[][]) ((Plateau2D)plateau).getGrille();
        for(int x = 0; x < xCases; x++)
        {
            for(int y = 0; y < yCases; y++)
            {
                Case c = grilleCase[x][y];
                
                // Setting neighbors and value of case
                c.initializeCaseNeighbors(plateau.getNeighbors(c));
                
                // Setting listeners
                CaseView2D cView = new CaseView2D(CASE_SIZE);
                cView.setOnMouseClicked((MouseEvent t) -> {
                    // Jeu en cours
                    if(plateau.getGameState() == GameState.Playing)
                    {
                        // Clic gauche : propagation du clic
                        if(t.getButton() == MouseButton.PRIMARY) {
                            c.propagateClick();
                        // Clic droit : ajout d'un drapeau ou suppression d'un drapeau
                        } else if(t.getButton() == MouseButton.SECONDARY) {
                            c.putFlag(); // WARNING : possibility to put more flag than mines, but doesn't matter in Game play.
                        }
                    }
                });
                c.addObserver(cView); // Case observe CaseView en cas de clic et se met à jour
                c.addObserver(this); // Plateau observe la case car si une case se met à jour, il va vérifier qu'il ne reste pas des cases
                gridboard.add(cView, x, y);
            }
        }
        playboard.getChildren().add(gridboard);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        super.update(o, arg); // Ainsi le plateau 3D pourra surcharger
    }
}
