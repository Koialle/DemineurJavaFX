
package demineurjavafx.view;

import demineurjavafx.model.Case;
import demineurjavafx.model.CaseCroix;
import java.util.Observable;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
        
        // Composants pour la vue de l'extension CaseCroix
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof CaseCroix){
            this.createCaseView((CaseCroix) o);
        } else {
            this.createCaseView((Case) o);
        }
        
    }
    
    @Override
    protected void createCaseView(Case c)
    {
        this.getChildren().clear();
        super.createCaseView(c);
        if(!c.isFlaged()) 
        {
            root.getChildren().clear();
            this.updateTextView(c);
            if(c.isVisible())
            {
                if(c.isTrigger()) { // S'il s'agit de la case qui a déclenché l'explosion 
                    rectangle.setFill(Color.RED);
                } else if(c instanceof CaseCroix) {
                    rectangle.setFill(Color.GREEN);
                } else {
                    rectangle.setFill(Color.LIGHTGREY);
                }
                root.getChildren().add(rectangle);
                
                if(c instanceof CaseCroix) {
                    root.getChildren().add(crossImageView);
                } else {
                    root.getChildren().addAll(text);
                }
            }
            // Si la case est cachée
            else
            {
                if(c instanceof CaseCroix) {
                    rectangle.setFill(Color.GREEN);
                } else{
                    rectangle.setFill(Color.GREY);
                }
                
                rectangle.setStroke(Color.DARKGREY);
                root.getChildren().add(rectangle);
            }
            this.getChildren().add(root);
        }
    }
}
