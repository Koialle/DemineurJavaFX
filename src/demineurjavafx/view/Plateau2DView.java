
package demineurjavafx.view;

import demineurjavafx.DemineurJavaFX;
import demineurjavafx.model.Case;
import demineurjavafx.model.Plateau;
import demineurjavafx.model.Plateau.GameState;
import demineurjavafx.model.Plateau2D;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Plateau2DView extends PlateauView {

    private final Image skullImage;
    private final Text lostText;
    
    public Plateau2DView(Plateau2D plateau)
    {
        super(plateau);
        width = plateau.getSize().getX() * CASE_SIZE + 20;
        height = plateau.getSize().getY() * CASE_SIZE + 120;
        
        skullImage = new Image("/demineurjavafx/resources/images/death-skull.png");
        lostText = new Text("Vous avez mouru...");
        lostText.setFont(Font.loadFont(DemineurJavaFX.class.getResourceAsStream("/demineurjavafx/resources/fonts/MORTEM.ttf"), 30));
        lostText.setStroke(Color.RED);
        lostText.setTextAlignment(TextAlignment.CENTER);
    }
    
    @Override
    protected void createView()
    {
        super.createView();
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
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Case)
        {
            Case c = (Case)o;
            if(c.isVisible() && c.getValue() == -1) {
                plateau.setGameState(GameState.Lost);
            } else {
                plateau.updateNbMinesLeft();
                this.flagStatut.getText().setText(String.valueOf(plateau.getNbMinesLeft()));
                
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
                this.flagStatut.getText().setText(String.valueOf(plateau.getNbMinesLeft()));
                // Tester que la partie est perdue
                // Tester que la partie est gagnée
            }
            else
            {
                // Transparent BACKGROUND
                Rectangle rectangle = new Rectangle(
                        gridboard.getWidth(),
                        gridboard.getHeight()
//                    plateau.getSize().getX() * CASE_SIZE,
//                    plateau.getSize().getX() * CASE_SIZE
                );
                rectangle.setFill(Color.rgb(255, 255, 255, 0.06));
//                rectangle.setOpacity(0.1);
                playboard.getChildren().add(rectangle);

                // End MESSAGE
                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(40);

                if(gameState == GameState.Lost)
                {
                    buttonView.setImage(deadSmiley);
                    
//                    text.setText("Vous avez perdu ! Ce qui signifie... la Mort.");
                    ImageView skullview = new ImageView();
                    skullview.setImage(skullImage);
//                    skullview.setFitWidth(size);
                    skullview.setPreserveRatio(true);
                    skullview.setCache(true);
                    
//                    vbox.getChildren().addAll(skullview);
                    vbox.getChildren().addAll(lostText);
                }
                else if(gameState == GameState.Win)
                {
                    buttonView.setImage(happySmiley);
                    
                    Text text = new Text();
                    text.setTextAlignment(TextAlignment.CENTER);
                    text.setFont(Font.font(null, FontWeight.MEDIUM, 40));
                    text.setText("Gagné ! Vous êtes en vie !");
                    text.setFill(Color.GREEN);
                    vbox.getChildren().add(text);
                }
                playboard.getChildren().add(vbox);
            }
        }
    }
}
