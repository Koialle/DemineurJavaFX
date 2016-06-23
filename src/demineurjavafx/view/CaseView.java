
package demineurjavafx.view;

import demineurjavafx.model.Case;
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
    
    public CaseView(int size)
    {
        this.size = size;
        text = new Text();
        text.setFont(Font.font(null, FontWeight.BOLD, 25));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setText("");
        flagImage = new Image("/demineurjavafx/resources/images/demineur-flag.png");

        this.getChildren().add(root);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        this.createCaseView((Case) o);
    }
    
    abstract void createCaseView(Case c);
    
    protected void updateText(Case c)
    {
        text.setText("");
        if(c.isVisible())
        {
            int caseValue = c.getValue();
            if(caseValue < 0) // -1 & -2 : Case est une bombe
            {
                text.setText("X");
                text.setFill(Color.BLACK);
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
        }
    }
}
