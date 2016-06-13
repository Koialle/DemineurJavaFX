
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

    private Text text;    
    public StatutBox(String text)
    {
        StackPane root = new StackPane();
        
        Rectangle rectangle = new Rectangle(150, 60);
        rectangle.setFill(Color.BLACK);
//        rectangle.setStyle("-fx-border-color: grey;\n"
//                + "-fx-border-insets: 5;\n"
//                + "-fx-border-width: 3;\n"
//                + "-fx-border-style: inset;\n");
        
        this.text = new Text(text);
        this.text.setFont(Font.loadFont(DemineurJavaFX.class.getResourceAsStream("/demineurjavafx/resources/fonts/digital-7.ttf"), 50));
        this.text.setFill(Color.RED);
        this.text.setTextAlignment(TextAlignment.CENTER);
        
        root.getChildren().addAll(rectangle, this.text);
        
        this.getChildren().add(root);
    }
    
    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
