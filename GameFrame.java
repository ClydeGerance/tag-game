/**
    This class represents the main game frame that contains the game 
    canvas and handles various game-related functionalities. It sets 
    up the GUI, animation timer, key listeners, and handles communication with 
    a server for multiplayer functionality. It also provides methods for playing
    background music, effects, and game over sound.
    
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
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

public class GameFrame extends JFrame {
  private Container contentPane;
  private GameCanvas gameCanvas;
  private Timer animationTimer;
  private boolean up,down,left,right,isOutOfBounds;
  private int width, height;
  private int speed, interval;

  private Socket socket;
  private int playerID;

  private ReadFromServer rfsRunnable;
  private WriteToServer wtsRunnable;



  Scanner in = new Scanner(System.in);
  
 /**The constructor used to create the game frame**/
  public GameFrame() {
    width = 800;
    height = 800;
    speed = 2;
    interval=2;
    gameCanvas = new GameCanvas(width,height); 
    up=down=left=right=isOutOfBounds=false;
  }

/**Method for setting up the GUI**/ 
  public void setUpGUI() {
    contentPane = this.getContentPane();
    this.setTitle("Player "+ playerID);
    this.add(gameCanvas,BorderLayout.CENTER);
    gameCanvas.createPlayer();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);

    setUpAnimationTimer();
    setUpKeyListener();
  }

/**Method for setting up the animation timer**/ 
  private void setUpAnimationTimer() {
    playSound();
    ActionListener al = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {

        if (up) {
          gameCanvas.player.moveV(-speed);
        } else if (down) {
          gameCanvas.player.moveV(speed);  
        }  else if (left) {
          gameCanvas.player.moveH(-speed);
        }  else if (right) {
          gameCanvas.player.moveH(speed);
        }

  
        /**displays game over once a player catches the other player and then the game ends**/
        if(gameCanvas.player.PisCollidingSB(gameCanvas.snake) ||
                gameCanvas.player.PisCollidingSB(gameCanvas.dog)) {
          playGameOver();
          System.out.println("GAME OVER!");
          animationTimer.stop();
        }
        if(gameCanvas.player.getCollectedCoins() == 10){
          playYouWin();
          System.out.println("YOU WIN!");
          animationTimer.stop();
        }
        gameCanvas.snake.moveH();
//        gameCanvas.snake.moveV();
//        gameCanvas.dog.moveH();
        gameCanvas.dog.moveV();
        gameCanvas.repaint();

      }
    };
    animationTimer = new Timer(interval, al);
    animationTimer.start();
  }

/**Method for setting up the key listeners**/
  private void setUpKeyListener() {
    KeyListener kl = new KeyListener() {
      public void keyTyped(KeyEvent ke) {
      }
      
      public void keyPressed(KeyEvent ke){
        int keyCode = ke.getKeyCode();   
        switch (keyCode) {
          case KeyEvent.VK_UP:
            if (gameCanvas.player.getY() <= 0){
              up = false;
              break;
            } else {
              up=true;
              break;
            }
          case KeyEvent.VK_DOWN:
            if (gameCanvas.player.getY() + gameCanvas.player.getImageHeight() >= height) {
              down = false;
              break;
            } else {
              down=true;
              break;
            }
          case KeyEvent.VK_LEFT:
            if (gameCanvas.player.getX() <= 0) {
              left = false;
              break;
            } else {
              left=true;
              break;
            }
          case KeyEvent.VK_RIGHT:
            if (gameCanvas.player.getX() + gameCanvas.player.getImageWidth() >= width) {
              right = false;
              break;
            } else {
              right=true;
              break;
            }
        }
      }
      
      public void keyReleased(KeyEvent ke){
        int keyCode = ke.getKeyCode();

        switch (keyCode) {
          case KeyEvent.VK_UP:
            up=false;
            break;
          case KeyEvent.VK_DOWN:
            down=false;
            break;
          case KeyEvent.VK_LEFT:
            left=false;
            break;
          case KeyEvent.VK_RIGHT:
            right=false;
            break;
        }        
      }
    };
    this.addKeyListener(kl);
    this.setFocusable(true);
  }

 /**Method used to play background music for the game**/
	public void playSound() {
		try{
			File file = new File("Game_Sound.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
      clip.loop(5);
		} catch (Exception e){ 
      System.out.println("Exception in playSound()");
		}
	}

	public void playEffect() {
		try{
			File file = new File("Effect_Sound.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();

		} catch (Exception e){ 
      System.out.println("Exception in playEffect()");
		}
	}

	public void playGameOver() {
		try{
			File file = new File("Game_Over.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (Exception e){ 
      System.out.println("Exception in playGameOver()");
		}
	}
    public void playYouWin() {
      try{
        File file = new File("You_Win.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
      } catch (Exception e){
        System.out.println("Exception in playGameOver()");
      }
    }

  
  public void connectToServer() {
    try {
      System.out.print("Ip Address: ");
      String ipAddress = in.nextLine();
      System.out.print("Port Number: ");
      int portNumber = Integer.parseInt(in.nextLine());

      
      socket = new Socket(ipAddress, portNumber);
      DataInputStream in = new DataInputStream(socket.getInputStream());
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());

   
      playerID = in.readInt();
      System.out.println("You are Player " + playerID + "!");

 
      if (playerID == 1) {
        System.out.println("Waiting for Player 2 to connect...");
        gameCanvas.addPlayer();
      }
      
      rfsRunnable = new ReadFromServer(in);
      wtsRunnable = new WriteToServer(out);
      
      rfsRunnable.waitForStartMsg();
 
    } catch(IOException e) {
      System.out.println("IOException from connectToServer()");
    }
  }
  
  private class ReadFromServer implements Runnable {
    private DataInputStream dataIn;

    public ReadFromServer(DataInputStream in) {
      dataIn = in;
    }

    public void run() {
      try{
        while(true){
          if (gameCanvas.enemy!=null) {
            gameCanvas.enemy.setX(dataIn.readInt());
            gameCanvas.enemy.setY(dataIn.readInt());
          }
        }
        
      } catch(IOException e) {
        System.out.println("IOException from RFS run()");
      }
    }

    public void waitForStartMsg() {
      try{
        String startMsg = dataIn.readUTF();
        System.out.println("Message from Server: " + startMsg);
        Thread readThread = new Thread(rfsRunnable);
        Thread writeThread = new Thread(wtsRunnable);
        readThread.start();
        writeThread.start();
        
      }catch(IOException e) {
        System.out.println("IOException from waitForStartMsg()");
      }
    }
  }

  private class WriteToServer implements Runnable {
    private DataOutputStream dataOut;

    public WriteToServer(DataOutputStream out) {
      dataOut = out;
    }
      
    public void run() {
      try {  
        while(true){
          if (gameCanvas.player!=null) {
            dataOut.writeInt(gameCanvas.player.getX());
            dataOut.writeInt(gameCanvas.player.getY());
            dataOut.flush();
          }
          try {
            Thread.sleep(25);
          } catch(InterruptedException e) {
            System.out.println("InterruptedException from WTS run()");
          }
        }
        
      } catch(IOException e) {
        System.out.println("IOException from WTS run()");
      }
    }
  }  
}