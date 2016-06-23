
package demineurjavafx.view;

import demineurjavafx.model.Case;
import java.util.Observable;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Ophélie EOUZAN
 */
public class CaseView2D extends CaseView {

    private final Rectangle rectangle; // Rectangle spécifique à une Case en 2D, pour une case 3D on utilisera 
                                       // probablement autre chose, ou plusieurs rectangles...
    
    public CaseView2D(int size)
    {
        super(size);
        
        // Draw background rectangle
        rectangle = new Rectangle(size-1, size-1); // -1 car setStroke() ajoute une bordure de 1, et les cases finisses par sortir de la fenètre
        rectangle.setFill(Color.GREY);
        rectangle.setStroke(Color.DARKGREY);
        
        root.getChildren().add(rectangle);
        root.getChildren().add(text);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        this.createCaseView((Case) o);
    }
    
    @Override
    void createCaseView(Case c)
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
                root.getChildren().add(ivFlag);
            }
            else
            {
                root.getChildren().clear();
                this.updateText(c);
                if(c.isVisible())
                {
                    rectangle.setFill(Color.LIGHTGREY);
                    root.getChildren().add(rectangle);                    
                    if(c.getValue() == -2) // -2 pour la case qui a déclenché l'explosion
                    {
                        rectangle.setFill(Color.RED);
                    }
                    root.getChildren().add(text);
                }
                else
                {
                    rectangle.setFill(Color.GREY);
                    rectangle.setStroke(Color.DARKGREY);
                    root.getChildren().add(rectangle);
                }
                
            }
            this.getChildren().add(root);
        }
    }
}
