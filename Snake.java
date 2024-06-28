/**
    This class represents a Snake in a game and extends the Player class. 
    It manages the position, size, and movement of the snake, including 
    horizontal movement, and provides methods to draw the snake on the screen.
    
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


public class Snake extends Player{
  private int x, y;
  private int imageW, imageH;
  private Image snake;
  private int vSpeed = 1;
  private int hSpeed = 1;

  public Snake(int width, int height) {
    imageW = 142;
    imageH = 113;
    x = 400;
    y = 344;

    snake = new ImageIcon("snake.png").getImage();
  }

  public void drawSB(Graphics2D g2d) {
    Graphics2D g = (Graphics2D) g2d;
      g2d.drawImage(snake, (int) x, (int) y, null);

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
  public void moveH(){

    if (x>=800-imageW || x<0) {
      hSpeed = hSpeed *-1;
    }
    x=x - hSpeed;
  }
}