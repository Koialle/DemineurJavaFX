
package demineurjavafx.view;

import demineurjavafx.model.Case;
import demineurjavafx.model.CaseCroix;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Ophélie EOUZAN
 */
abstract public class CaseView extends Parent implements Observer {

    protected final StackPane root = new StackPane();
    protected final Text text;
    protected final int size;
    
    protected final Image flagImage;
    protected final ImageView flagImageView;
    
    // Extension CaseCroix
    protected final Image crossImage;
    protected final ImageView crossImageView;
    
    public CaseView(int size)
    {
        this.size = size;
        
        // Text
        text = new Text();
        text.setFont(Font.font(null, FontWeight.BOLD, 25));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText("");
        
        // Images
        flagImage = new Image("/demineurjavafx/resources/images/demineur-flag.png");
        
        flagImageView = new ImageView();
        flagImageView.setImage(flagImage);
        flagImageView.setFitHeight(size-1);
        flagImageView.setPreserveRatio(true);
//        buttonView.setCache(true);
        
        // Extension CaseCroix
        crossImage = new Image("/demineurjavafx/resources/images/cross.png");
        
        crossImageView = new ImageView();
        crossImageView.setImage(crossImage);
        crossImageView.setFitHeight(size-1);
        crossImageView.setPreserveRatio(true);
//        buttonView.setCache(true);

        this.getChildren().add(root);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        this.createCaseView((Case) o);
    }
    
    protected void createCaseView(Case c)
    {
        if(c.isFlaged()) {
            this.getChildren().clear();
            root.getChildren().clear();
            root.getChildren().add(flagImageView);
            this.getChildren().add(root);
        }
    }
    
    protected void updateTextView(Case c)
    {
        text.setText("");
        if(c.isVisible())
        {
            if(c.isTrapped() || c.isTrigger()) { // Case est une bombe
                text.setText("X");
                text.setFill(Color.BLACK);
            } else if (!c.isEmpty()) {
                int caseValue = c.getValue();
                text.setText(String.valueOf(caseValue));
                if(caseValue == 1) text.setFill(Color.BLUE); // Transformer en switch-case
                if(caseValue == 2) text.setFill(Color.GREEN);
                if(caseValue == 3) text.setFill(Color.RED);
                if(caseValue == 4) text.setFill(Color.DARKBLUE);
                if(caseValue == 5) text.setFill(Color.DARKRED);
                if(caseValue > 5) text.setFill(Color.BLACK);
            }
        }
    }
}
