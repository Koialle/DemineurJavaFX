
package demineurjavafx;

import demineurjavafx.model.Plateau.Difficulty;
import demineurjavafx.model.Plateau.Size;
import demineurjavafx.model.Plateau2D;
import demineurjavafx.view.PlateauView2D;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Ophélie EOUZAN
 */
public class DemineurJavaFX extends Application {
    
    private Size size;
    private Difficulty difficulty;
    private boolean caseX = false;
    
    private final String[] tailles = new String[]{"10 x 10", "15 x 10", "15 x 15", "20 x 20"};
    private final String[] difficultes = new String[]{"Facile", "Moyen", "Difficile", "Expert"};
//    private final String[] extensions = new String[]{"CaseX"};
    
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
        Menu menuExtension = new Menu("Extension");
        
        // Taille de la fenètre de démineur
        final ToggleGroup groupTaille = new ToggleGroup();
        for (String taille : tailles) {
            this.createRadioMenuItem(taille, groupTaille,menuTaille);
        }
        groupTaille.getToggles().get(0).setSelected(true);
        menuOptions.getItems().addAll(menuTaille);
        
        // Difficulté de la partie de démineur
        final ToggleGroup groupDifficulte = new ToggleGroup();
        for (String difficulte : difficultes) {
            this.createRadioMenuItem(difficulte, groupDifficulte, menuDifficulte);
        }
        groupDifficulte.getToggles().get(0).setSelected(true);
        menuOptions.getItems().addAll(menuDifficulte);
        
        // Extension(s)
        CheckMenuItem crossCaseItem = this.createCheckRadioItem("CaseX", menuExtension);
        menuOptions.getItems().addAll(menuExtension);
        
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
            plateau.setCaseCroix(crossCaseItem.isSelected());
            
            PlateauView2D plateauView = new PlateauView2D(plateau);
            plateau.addObserver(plateauView);
            plateau.initializePlateau();
            layoutPartie.getChildren().addAll(menuBar, plateauView);
            plateau.startTimer();
            
            // Affichage du plateau | Changement de Scene
            Scene scene = new Scene(layoutPartie, plateauView.getWidth() + 10, plateauView.getHeight() + 120);
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
        
        // Window stuff
        primaryStage.setTitle("Démineur JavaFX - Ophélie EOUZAN");
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0); // Close all process
        });
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

    private RadioMenuItem createRadioMenuItem(String text, ToggleGroup toggleGroup, Menu menu)
    {
        RadioMenuItem radioItem = new RadioMenuItem(text);
        radioItem.setUserData(text);
        radioItem.setToggleGroup(toggleGroup);
        menu.getItems().add(radioItem);
        
        return radioItem;
    }
    
    private CheckMenuItem createCheckRadioItem(String text, Menu menu)
    {
        CheckMenuItem checkItem = new CheckMenuItem(text);
        checkItem.setUserData(text);
        menu.getItems().add(checkItem);
        return checkItem;
//        checkItem.selectedProperty().addListener(new ChangeListener<Boolean>() {
//            public void changed(ObservableValue ov,
//            Boolean old_val, Boolean new_val) {
//                node.setVisible(new_val);
//            }
//        });
    }
}
