
package demineurjavafx.model;

import java.util.ArrayList;

/**
 *
 * @author Ophélie EOUZAN
 */
public class Case2D extends Case {
    private final int x, y;
        
    public Case2D(int x, int y, int value)
    {
        super(value);
        this.x = x;
        this.y = y;
        
        neighbors = new ArrayList();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
