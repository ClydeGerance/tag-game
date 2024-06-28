/**
    The Dog class is a subclass of Player that represents a dog game object, 
    with properties such as position, size, image, and vertical movement. 
    The dog can move up and down within the game area.
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


public class Dog extends Player{
    private int x, y;
    private int imageW, imageH;
    private Image dog;
    private int vSpeed = 1;
    private int hSpeed = 1;

    public Dog(int width, int height) {
        imageW = 110;
        imageH = 83;
        x = 345;
        y = 400;
        dog = new ImageIcon("dog.png").getImage();
    }

    public void drawD(Graphics2D g2d) {
        Graphics2D g = (Graphics2D) g2d;
        g2d.drawImage(dog, (int) x, (int) y, null);

    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public int getImageWidth() {
        return imageW;
    }
    
    public int getImageHeight() {
        return imageH;
    }
    
    public void moveV(){

        if (y>=800-imageH || y<0) {
            vSpeed = vSpeed *-1;
        }
        y=y + vSpeed;
    }
}
