package GamePackage;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * The Cell class creates objects that represent squares on
 * our game board. We created a custom class so that we can
 * change the dimension and color of the squares easily.
 * 
 * @author Alex Reiff
 *
 */
public class Cell extends JPanel {

    public Cell() {
    	
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(10,10);
    }
}