
package demineurjavafx;

import demineurjavafx.model.Plateau.Difficulty;
import demineurjavafx.model.Plateau.Size;
import demineurjavafx.model.Plateau2D;
import demineurjavafx.view.Plateau2DView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Ophélie EOUZAN
 */
public class DemineurJavaFX extends Application {
    
    private Size size;
    private Difficulty difficulty;
    
    @Override
    public void start(Stage primaryStage) {
        
        VBox layout = new VBox();
//        layout.setAlignment(Pos.CENTER);
//        layout.setSpacing(10);
        
        MenuBar menuBar = new MenuBar();
        
        Menu menuOptions = new Menu("Options");
        
        layout.getChildren().add(menuBar);   
        
        // Taille de la fenètre de démineur
        Menu menuTaille = new Menu("Taille");
        final ToggleGroup groupTaille = new ToggleGroup();
        for (String taille : new String[]{"10 x 10", "15 x 10", "15 x 15", "20 x 20"}) {
            RadioMenuItem itemTaille = new RadioMenuItem(taille);
            itemTaille.setUserData(taille);
            itemTaille.setToggleGroup(groupTaille);
            menuTaille.getItems().add(itemTaille);
        }
        groupTaille.getToggles().get(0).setSelected(true);
        menuOptions.getItems().addAll(menuTaille);
        
        // Difficulté de la partie de démineur
        Menu menuDifficulte = new Menu("Difficulté");
        final ToggleGroup groupDifficulte = new ToggleGroup();
        for (String difficulte : new String[]{"Facile", "Moyen", "Difficile", "Expert"}) {
            RadioMenuItem itemDifficulte = new RadioMenuItem(difficulte);
            itemDifficulte.setUserData(difficulte);
            itemDifficulte.setToggleGroup(groupDifficulte);
            menuDifficulte.getItems().add(itemDifficulte);
        }
        groupDifficulte.getToggles().get(0).setSelected(true);
        menuOptions.getItems().addAll(menuDifficulte);
        
        Menu menu = new Menu("Menu");
        
        
        MenuItem menuJouer = new MenuItem("Jouer");
        menuJouer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                String taille = groupTaille.getSelectedToggle().getUserData().toString();
                String difficulte = groupDifficulte.getSelectedToggle().getUserData().toString();
                setGameProperties(taille, difficulte);
                
                Plateau2D plateau = new Plateau2D(size, difficulty);
                Plateau2DView plateauView = new Plateau2DView(plateau);
                plateau.addObserver(plateauView);
                plateau.initializePlateau();
        //        plateau.startTimer();
                
                Scene scene = new Scene(plateauView, plateauView.getWidth(), plateauView.getHeight());
                primaryStage.setScene(scene);
            }
        });
        
        MenuItem menuQuitter = new MenuItem("Quitter");
        menuQuitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        menu.getItems().addAll(menuJouer, menuQuitter);
        
        menuBar.getMenus().addAll(menu, menuOptions);
        
        Image demineur = new Image("/demineurjavafx/resources/images/demineur.png");
        ImageView demineurView = new ImageView();
        demineurView.setImage(demineur);
        demineurView.setFitHeight(200);
        demineurView.setFitWidth(300);
        demineurView.setPreserveRatio(true);
//        demineurView.setCache(true);
        layout.getChildren().add(demineurView);
        
        Scene startScene = new Scene(layout, 300, 300); 
        
        
        
        primaryStage.setTitle("Démineur JavaFX - @Ophélie EOUZAN");
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void setGameProperties(String taille, String difficulte)
    {
        switch(taille)
        {
            case "15 x 10":
                size = Size.Medium;
                break;
            case "15 x 15":
                size = Size.Big;
                break;
            case "20 x 20":
                size = Size.Large;
                break;
            default:
                size = Size.Small;
                break;
        }

        switch(difficulte)
        {
            case "Moyen":
                difficulty = Difficulty.Medium;
                break;
            case "Difficile":
                difficulty = Difficulty.Difficult;
                break;
            case "Expert":
                difficulty = Difficulty.Hard;
                break;
            default:
                difficulty = Difficulty.Easy;
                break;
        }
    }
}
