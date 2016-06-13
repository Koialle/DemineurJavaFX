
package demineurjavafx;

import demineurjavafx.model.Plateau.Difficulty;
import demineurjavafx.model.Plateau.Size;
import demineurjavafx.model.Plateau2D;
import demineurjavafx.view.Plateau2DView;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
        
        // Création de l'écran de lancement du jeu
        VBox layout = new VBox();
        
        // Barre de menu
        MenuBar menuBar = new MenuBar();
        
        // Menu des options de jeu
        Menu menuOptions = new Menu("Options");
        Menu menuTaille = new Menu("Taille");
        Menu menuDifficulte = new Menu("Difficulté");
        
        // Taille de la fenètre de démineur
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
        final ToggleGroup groupDifficulte = new ToggleGroup();
        for (String difficulte : new String[]{"Facile", "Moyen", "Difficile", "Expert"}) {
            RadioMenuItem itemDifficulte = new RadioMenuItem(difficulte);
            itemDifficulte.setUserData(difficulte);
            itemDifficulte.setToggleGroup(groupDifficulte);
            menuDifficulte.getItems().add(itemDifficulte);
        }
        groupDifficulte.getToggles().get(0).setSelected(true);
        menuOptions.getItems().addAll(menuDifficulte);
        
        // Menu d'actions
        Menu menu = new Menu("Menu");
        MenuItem menuJouer = new MenuItem("Jouer");
        MenuItem menuQuitter = new MenuItem("Quitter");
        
        // Lancement du jeu en fonction des options 
        // choisies dans le menu d'options
        menuJouer.setOnAction((ActionEvent t) -> {
            // Récupération des options choisies
            String taille = groupTaille.getSelectedToggle().getUserData().toString();
            String difficulte = groupDifficulte.getSelectedToggle().getUserData().toString();
            setGameProperties(taille, difficulte);
            
            // Création d'un plateau en fonction des options choisies
            VBox layoutPartie = new VBox();
            layoutPartie.setFillWidth(true);
            
            Plateau2D plateau = new Plateau2D(size, difficulty);
            Plateau2DView plateauView = new Plateau2DView(plateau);
            plateau.addObserver(plateauView);
            plateau.initializePlateau();
            layoutPartie.getChildren().addAll(menuBar, plateauView);
            plateau.startTimer();
            
            // Affichage du plateau | Changement de Scene
            Scene scene = new Scene(layoutPartie, plateauView.getWidth(), plateauView.getHeight() + 20);
            primaryStage.setScene(scene);
        });
        
        // Quitte l'application
        menuQuitter.setOnAction((ActionEvent t) -> {
            System.exit(0);
        });
        menu.getItems().addAll(menuJouer, menuQuitter);
        
        menuBar.getMenus().addAll(menu, menuOptions);
        layout.getChildren().add(menuBar);
        
        // Image de démineur pour le menu de départ
        Image demineur = new Image("/demineurjavafx/resources/images/demineur.png");
        ImageView demineurView = new ImageView();
        demineurView.setImage(demineur);
        demineurView.setFitWidth(300);
        demineurView.setFitHeight(280);
        layout.getChildren().add(demineurView);
        
        Scene startScene = new Scene(layout, 300, 300); 
        
        primaryStage.setTitle("Démineur JavaFX - Ophélie EOUZAN");
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
