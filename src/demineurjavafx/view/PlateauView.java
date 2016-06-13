
package demineurjavafx.view;

import demineurjavafx.DemineurJavaFX;
import demineurjavafx.model.Plateau;
import demineurjavafx.model.Plateau.GameState;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Ophélie EOUZAN
 */
abstract public class PlateauView extends Parent implements Observer {

    protected static final int CASE_SIZE = 40;
    
    protected Plateau plateau = null;
    protected int width;
    protected int height;
    
    protected BorderPane layout;
    protected StackPane playboard;
    protected StatutBox timerStatut;
    protected StatutBox flagStatut;
    protected Button smileyButton;
    protected Timer timer;
    
    protected Image neutralSmiley;
    protected Image happySmiley;
    protected Image deadSmiley;
    protected ImageView buttonView;
    
    protected Text messageFin;
    
    public PlateauView(Plateau plateau)
    {
        this.plateau = plateau;
        
        neutralSmiley = new Image("/demineurjavafx/resources/images/neutral-smiley.png");
        happySmiley = new Image("/demineurjavafx/resources/images/happy-smiley2.png");
        deadSmiley = new Image("/demineurjavafx/resources/images/dead-smiley.png");
        
        buttonView = new ImageView();
        buttonView.setImage(neutralSmiley);
        buttonView.setFitHeight(50);
        buttonView.setPreserveRatio(true);
//        buttonView.setCache(true);
        
        messageFin = new Text();
        messageFin.setTextAlignment(TextAlignment.CENTER);
        messageFin.setFont(Font.loadFont(DemineurJavaFX.class.getResourceAsStream("/demineurjavafx/resources/fonts/JellyCrazies.ttf"), 30));
    }
    
    protected void createView()
    {
        layout = new BorderPane();
        // Scoreboard
        BorderPane statusboard = new BorderPane();
        // Ajouter le timer et le compteur de drapeau au scoreboard
        // Ainsi que le bouton Smiley pour abandonner le jeu, qui fait la tête quand on a perdu.
        flagStatut = new StatutBox("0");
        
        // Setting smiley button
        smileyButton = new Button("", buttonView);
        smileyButton.setOnMouseClicked((MouseEvent t) -> {
            if(plateau.getGameState() == GameState.Playing)
            {
                plateau.propagateExplosion();
            }
        });
        timerStatut = new StatutBox(""/*"00:00"*/);
        timer = new Timer(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(plateau.getGameState() == GameState.Playing)
                {
                    timerStatut.setText(plateau.getMinuteSecondeFormat());
                }
                else timer.cancel();
            }
        }, 0, 1000);
        
        statusboard.setLeft(flagStatut);
        statusboard.setCenter(smileyButton);
        statusboard.setRight(timerStatut);
        layout.setTop(statusboard);

        // Grille de cases :
        playboard = new StackPane();
        playboard.setAlignment(Pos.CENTER);
        layout.setCenter(playboard);
        this.getChildren().add(layout);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Plateau)
        {
            
        }
    }
    
    public void startTimer()
    {
        
    }
}
