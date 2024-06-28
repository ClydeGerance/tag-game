/**
    The Coin class represents a game object that stores the position, 
    radius, image, and collection state of a coin.
    
    @author Clyde Lester M. Gerance (185503) , Chloe Laine D.G. Pangilinan (214524)

	@version May 15, 2023
 **/

/*
	I have not discussed the Java language code in my program
	with anyone other than my instructor or the teaching assistants
	assigned to this course.

	I have not used Java language code obtained from another student,
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program
	was obtained from another source, such as a textbook or website,
	that has been clearly noted with a proper citation in the comments
	of my program.
*/

import java.awt.*;
import javax.swing.*;

public class Coin {
    private int x, y;
    private int radius;
    private Image coinImage;
    private boolean collected;

    public Coin(int width, int height) {
        radius = 10;
        x = (int) (Math.random() * (width - 2 * radius)) + radius;
        y = (int) (Math.random() * (height - 2 * radius)) + radius;
        coinImage = new ImageIcon("coin.png").getImage();
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(coinImage, x - radius, y - radius, 2 * radius, 2 * radius, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }
    
       public void setCollected(boolean collected) {
        this.collected = collected;
    }
    
      public boolean isCollected() {
        return collected;
    }

}
