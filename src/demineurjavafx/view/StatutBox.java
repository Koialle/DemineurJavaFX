
package demineurjavafx.view;

import demineurjavafx.DemineurJavaFX;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Ophélie EOUZAN
 */
public class StatutBox extends Parent {

    private final Text text;    
    public StatutBox(String text)
    {
        StackPane root = new StackPane();
        
        // Rectangle
        Rectangle rectangle = new Rectangle(150, 60);
        rectangle.setFill(Color.BLACK);
        
        // Texte
        this.text = new Text(text);
        this.text.setFont(Font.loadFont(DemineurJavaFX.class.getResourceAsStream("/demineurjavafx/resources/fonts/digital-7.ttf"), 50));
        this.text.setFill(Color.RED);
        this.text.setTextAlignment(TextAlignment.CENTER);
        
        root.getChildren().addAll(rectangle, this.text);
        this.getChildren().add(root);
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
