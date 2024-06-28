/**
    This class represents the game canvas where the gameplay takes place. 
    It manages the dimensions, background image, players (including their collision detection), 
    coins, and the rendering of various game elements such as players, snake, dog, and coins. 
    It also keeps track of the collected coins and updates the display accordingly.
    
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
import java.util.Iterator;

public class GameCanvas extends JComponent {
    private int width, height;
    private int x, y,x1,y1;
    private Image backgroundImage, scaledBackground;
    private Image gameover;
    private Image youwin;
    public Player player, enemy;
    public Snake snake;
    public Dog dog;
    public int playerID;

    private ArrayList<Coin> coins;

    private int collectedCoins;
    private JLabel coinCountLabel;

    public GameCanvas(int w, int h) {
        width = w;
        height = h;
        x1 = (width / 2) - 264;
        y1 = (height / 2) - 146;
        x = (width / 2) - 303;
        y = (height / 2) - 177;
        backgroundImage = new ImageIcon("background.png").getImage();
        scaledBackground = backgroundImage.getScaledInstance(width, height, Image.SCALE_FAST);
        gameover = new ImageIcon("gameover.png").getImage();
        youwin = new ImageIcon("youwin.png").getImage();
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.WHITE);
        coins = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            coins.add(new Coin(width, height));
        }

        collectedCoins = 0;
        coinCountLabel = new JLabel("Coins: 0");
        coinCountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        coinCountLabel.setForeground(Color.WHITE);
        coinCountLabel.setBounds(10, 10, 100, 30);
        this.add(coinCountLabel);
        snake = new Snake(width, height);
        dog = new Dog(width, height);
        playerID = 0;
        createPlayer();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(scaledBackground, 0, 0, null);

        if (!player.PisCollidingSB(snake)) {
            if (playerID == 1) {
                player.drawPlayer(g2d);
                enemy.drawEnemy(g2d);
            } else {
                enemy.drawPlayer(g2d);
                player.drawEnemy(g2d);
            }
            snake.drawSB(g2d);
            dog.drawD(g2d);
        } else {
            if (playerID == 1) {
                player.drawPlayer(g2d);
                enemy.drawEnemy(g2d);
            } else {
                enemy.drawPlayer(g2d);
                player.drawEnemy(g2d);
            }
        }

        drawCoins(g2d);

        // Display game over icon once players have collided
        if (player.PisCollidingSB(snake) || player.PisCollidingSB(dog)) {
            g2d.drawImage(gameover, x1, y1, null);
        }
        if (player.getCollectedCoins() ==10) {
            g2d.drawImage(youwin, x, y, null);
        }
        if (enemy.getCollectedCoins() ==10){
            g2d.drawImage(youwin, x, y, null);
        }

        updateCollectedCoins(); // Update collected coins
        super.paintComponent(g2d);
    }

    // Method used to add player
    public void addPlayer() {
        playerID++;
    }

    // Method used to create player
    public void createPlayer() {
        if (playerID == 1) {
            player = new Player(1, (height / 2) - 25);
            enemy = new Player(width - 71, (height / 2) - 25);
        } else {
            enemy = new Player(1, (height / 2) - 25);
            player = new Player(width - 71, (height / 2) - 25);
        }
    }

    private void drawCoins(Graphics2D g2d) {
        for (Coin coin : coins) {
            coin.draw(g2d);
        }
    }

    private void updateCollectedCoins() {
        int newCollectedCoins = 0;
        ArrayList<Coin> collectedCoins = new ArrayList<>();

    for (Coin coin : coins) {
        if (!coin.isCollected()) { // Check if the coin has not been collected yet
            if (player.collectCoin(coin)) { // Attempt to collect the coin
                coin.setCollected(true); // Set the coin as collected
                newCollectedCoins++; // Increment the collectedCoins count
                collectedCoins.add(coin); // Add the coin to the collectedCoins list
            }
        }
    }

    coins.removeAll(collectedCoins); // Remove collected coins from the main coins list
    player.addCollectedCoins(newCollectedCoins);

    coinCountLabel.setText("Coins: " + player.getCollectedCoins());
}


}

