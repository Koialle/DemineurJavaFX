
package demineurjavafx.view;

import demineurjavafx.model.Case;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
        flagImage = new Image("/demineurjavafx/resources/images/demineur-flag.png");

        this.getChildren().add(root);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        this.createCaseView((Case) o);
    }
    
    abstract void createCaseView(Case c);
}
