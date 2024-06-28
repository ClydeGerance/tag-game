/**
    The Player class represents a player in a game, with properties such as position, 
    size, images for the player and enemy, and methods for drawing, moving, collision detection, 
    and coin collection. It also keeps track of the number of collected coins and the 
    total number of coins in the game.
    
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
import java.util.ArrayList;

public class Player {
    private Image player;
    private Image enemy;
    private int x, y;
    private int imageW, imageH;
    private int collectedCoins;
    private int totalCoins;

    public Player() {
        imageW = 50;
        imageH = 50;
        player = new ImageIcon("player.png").getImage();
        enemy = new ImageIcon("enemy.png").getImage();
        collectedCoins = 0;
        totalCoins = 0;
    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        imageW = 50;
        imageH = 50;
        player = new ImageIcon("player.png").getImage();
        enemy = new ImageIcon("enemy.png").getImage();
        collectedCoins = 0;
        totalCoins = 0;
    }

    public void drawPlayer(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(player, x, y, null);
    }

    public void drawEnemy(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(enemy, x, y, null);
    }

    public void moveH(int n) {
        x += n;
    }

    public void moveV(int n) {
        y += n;
    }

    public void setX(int n) {
        x = n;
    }

    public void setY(int n) {
        y = n;
    }

    public int getX() {
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

    public boolean isColliding(Player other) {
        return !(this.x + this.imageW <= other.getX() ||
                this.x >= other.getX() + other.getImageWidth() ||
                this.y + this.imageH <= other.getY() ||
                this.y >= other.getY() + other.getImageHeight());
    }

    public boolean PisCollidingSB(Snake other) {
        return !(this.x + this.imageW <= other.getX() ||
                this.x >= other.getX() + other.getImageWidth() ||
                this.y + this.imageH <= other.getY() ||
                this.y >= other.getY() + other.getImageHeight());
    }

    public boolean PisCollidingSB(Dog other) {
        return !(this.x + this.imageW <= other.getX() ||
                this.x >= other.getX() + other.getImageWidth() ||
                this.y + this.imageH <= other.getY() ||
                this.y >= other.getY() + other.getImageHeight());
    }

    public boolean collectCoin(Coin coin) {
        int coinX = coin.getX();
        int coinY = coin.getY();
        int coinRadius = coin.getRadius();
        int playerRadius = getImageWidth() / 2;

        int playerCenterX = getX() + playerRadius;
        int playerCenterY = getY() + playerRadius;

        double distance = Math.sqrt(Math.pow(playerCenterX - coinX, 2) + Math.pow(playerCenterY - coinY, 2));

        return distance <= playerRadius + coinRadius;
    }

    public void addCollectedCoins(int count) {
        collectedCoins += count;
    }

  public int getCollectedCoins() {
        return collectedCoins;
    }
 public void setTotalCoins(int count) {
        totalCoins = count;
    }
}