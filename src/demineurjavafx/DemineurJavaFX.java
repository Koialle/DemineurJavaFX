
package demineurjavafx;

import demineurjavafx.model.Plateau.Difficulty;
import demineurjavafx.model.Plateau.Size;
import demineurjavafx.model.Plateau2D;
import demineurjavafx.view.Plateau2DView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ophélie EOUZAN
 */
public class DemineurJavaFX extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Plateau2D plateau = new Plateau2D(Size.Small, Difficulty.Easy);
        Plateau2DView plateauView = new Plateau2DView(plateau);
        
        plateau.addObserver(plateauView);
        
        
        Scene scene = new Scene(plateauView, plateauView.getWidth(), plateauView.getHeight());
        
        plateau.initializePlateau();
//        plateau.startTimer();
        
        primaryStage.setTitle("Démineur JavaFX - @Ophélie EOUZAN");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
