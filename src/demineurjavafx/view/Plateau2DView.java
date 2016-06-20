
package demineurjavafx.view;

import demineurjavafx.model.Case;
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
public class Plateau2DView extends PlateauView {

    private GridPane gridboard; // Composant propre à un grille 2D, pas valable pour une grille 3D
    
    public Plateau2DView(Plateau2D plateau)
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
        Case[][] grilleCase = ((Plateau2D)plateau).getGrille();
        for(int x = 0; x < xCases; x++)
        {
            for(int y = 0; y < yCases; y++)
            {
                Case c = grilleCase[x][y];
                
                // Setting neighbors and value of case
                c.setNeighbors(plateau.getNeighbors(c));
                if(c.getValue() != -1)
                {
                    long value = c.getNeighbors().stream().filter(t -> t.getValue() == -1).count();
                    c.setValue((int)value);
                }
                
                // Setting listeners
                CaseView cView = new CaseView(CASE_SIZE);
                cView.setOnMouseClicked((MouseEvent t) -> {
                    if(plateau.getGameState() == GameState.Playing)
                    {
                        if(t.getButton() == MouseButton.PRIMARY) {
                            plateau.propagateClick(c);
                        } else if(t.getButton() == MouseButton.SECONDARY) {
                            c.putFlag(); // WARNING : possibility to put more flag than mines, but doesn't matter.
                        }
                    }
                });
                c.addObserver(cView);
                c.addObserver(this);
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
