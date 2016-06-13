
package demineurjavafx.view;

import demineurjavafx.model.Case;
import demineurjavafx.model.Plateau;
import demineurjavafx.model.Plateau.GameState;
import demineurjavafx.model.Plateau2D;
import java.util.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Plateau2DView extends PlateauView {

    protected GridPane gridboard; // Composant de grille 2D, pas valable pour une grille 3D
    
    
    public Plateau2DView(Plateau2D plateau)
    {
        super(plateau);
        width = plateau.getSize().getX() * CASE_SIZE + 20;
        height = plateau.getSize().getY() * CASE_SIZE + 120;
    }
    
    @Override
    protected void createView()
    {
        super.createView();
        gridboard = new GridPane();
        gridboard.setPadding(new Insets(5,10,5,10));
        gridboard.setAlignment(Pos.CENTER);
        
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
                            c.putFlag(); // WARNING : possibility to put more flag than mines
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
        if(o instanceof Case)
        {
            Case c = (Case)o;
            if(c.isVisible() && c.getValue() < 0) {
                plateau.setGameState(GameState.Lost);
            } else {
                plateau.updateNbMinesLeft();
                this.flagStatut.setText(String.valueOf(plateau.getNbMinesLeft()));
                
                int nbCasesPlateau = plateau.getSize().getX() * plateau.getSize().getY();
                if(plateau.getNbCaseVisibleOrFlaged() == nbCasesPlateau && plateau.getNbMinesLeft() == 0) plateau.setGameState(GameState.Win);
            }
        }
        else if(o instanceof Plateau)
        {
            GameState gameState = plateau.getGameState();
            if(gameState == GameState.Playing)
            {
                this.createView();
                this.flagStatut.setText(String.valueOf(plateau.getNbMinesLeft()));
                this.timerStatut.setText(plateau.getMinuteSecondeFormat());
            }
            else
            {
                // End MESSAGE
                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(40);

                if(gameState == GameState.Lost)
                {
                    buttonView.setImage(deadSmiley);
                    
                    messageFin.setText("PERDU");
                    messageFin.setFill(Color.RED);
                }
                else if(gameState == GameState.Win)
                {
                    buttonView.setImage(happySmiley);

                    messageFin.setText("BRAVO");
                    messageFin.setFill(Color.GREEN);
                }
                vbox.getChildren().add(messageFin);
                playboard.getChildren().add(vbox);
            }
        }
    }
}
