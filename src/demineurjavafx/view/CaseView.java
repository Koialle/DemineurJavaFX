
package demineurjavafx.view;

import demineurjavafx.model.Case;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
public class CaseView extends Parent implements Observer {

    private final StackPane root = new StackPane();
    private final Rectangle rectangle;
    private final Text text;
    private final int size;
    
    private final Image flagImage;
    
    public CaseView(int size)
    {
        this.size = size;
        
        text = new Text();
        flagImage = new Image("/demineurjavafx/resources/images/demineur-flag.png");
        
        // Draw background rectangle
        rectangle = new Rectangle(size-1, size-1); // -1 car setStroke() ajoute une bordure de 1, et les cases finisses par sortir de la fenètre
        rectangle.setFill(Color.GREY);
        rectangle.setStroke(Color.DARKGREY);
        
        // Set text
        text.setFont(Font.font(null, FontWeight.BOLD, 25));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText("");
        
        root.getChildren().add(rectangle);
        root.getChildren().add(text);
        this.getChildren().add(root);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        this.createCaseView((Case) o);
    }
    
    private void createCaseView(Case c)
    {
        if(c instanceof Case)
        {
            this.getChildren().clear();
            if(c.isFlaged())
            {
                ImageView ivFlag = new ImageView();
                ivFlag.setImage(flagImage);
                ivFlag.setFitWidth(size-1);
                ivFlag.setPreserveRatio(true);
                ivFlag.setCache(true);
                this.getChildren().add(ivFlag);
            }
            else
            {
                root.getChildren().clear();
                text.setText("");
                if(c.isVisible())
                {
                    rectangle.setFill(Color.LIGHTGREY);
                    root.getChildren().add(rectangle);
                    int caseValue = ((Case)c).getValue();
                    
                    if(caseValue < 0) // -1 & -2 : Case est une bombe
                    {
                        text.setText("X");
                        text.setFill(Color.BLACK);
                        
                        if(caseValue == -2) // -2 pour la case qui a déclenché l'explosion
                        {
                            rectangle.setFill(Color.RED);
                        }
                    }
                    else if(caseValue > 0)
                    {
                        text.setText(String.valueOf(caseValue));
                        if(caseValue == 1) text.setFill(Color.BLUE);
                        if(caseValue == 2) text.setFill(Color.GREEN);
                        if(caseValue == 3) text.setFill(Color.RED);
                        if(caseValue == 4) text.setFill(Color.DARKBLUE);
                        if(caseValue == 5) text.setFill(Color.DARKRED);
                        if(caseValue > 5) text.setFill(Color.BLACK);
                        
                    }
                    root.getChildren().add(text);
                }
                else
                {
                    rectangle.setFill(Color.GREY);
                    rectangle.setStroke(Color.DARKGREY);
                    root.getChildren().add(rectangle);
                }
                this.getChildren().add(root);
            }
        }
    }
}
