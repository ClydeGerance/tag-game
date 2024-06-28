/**
    This class represents the game server that accepts connections from 
    clients and handles the communication between the clients. It keeps track of 
    the number of players, their coordinates, and uses separate threads for reading 
    and writing data to each client.
    
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

import java.io.*;
import java.net.*;

public class GameServer {
  private ServerSocket ss;
  private int numPlayers;
  private int maxPlayers;

  private Socket p1Socket, p2Socket;
  private ReadFromClient p1ReadRunnable, p2ReadRunnable;
  private WriteToClient p1WriteRunnable, p2WriteRunnable;

  private int p1x, p1y, p2x, p2y;

/**The constructor used to create the server of the game**/ 
  public GameServer() {
    System.out.println("---- GAME SERVER ----");
    numPlayers = 0;
    maxPlayers = 2;

    p1x=10;
    p1y=50;
    p2x=700;
    p2y=450;

    try{
      ss = new ServerSocket(45678);
    } catch(IOException e) {
      System.out.println("IOException from GameServer constructor");
    }
  }


  public void acceptConnections() {
    try {
      System.out.println("Waiting for connections...");

      while (numPlayers < maxPlayers) {
        Socket s = ss.accept();
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        numPlayers++;
        
        out.writeInt(numPlayers);
        System.out.println("Player " + numPlayers + " has connected.");

        ReadFromClient rfc = new ReadFromClient(numPlayers, in);
        WriteToClient wtc = new WriteToClient(numPlayers, out);

        if (numPlayers == 1) {
          p1Socket = s;
          p1ReadRunnable = rfc;
          p1WriteRunnable = wtc;
        } else {
          p2Socket = s;
          p2ReadRunnable = rfc;
          p2WriteRunnable = wtc;  
          p1WriteRunnable.sendStartMsg();
          p2WriteRunnable.sendStartMsg(); 

          Thread readThread1 = new Thread(p1ReadRunnable);
          Thread readThread2 = new Thread(p2ReadRunnable);
          readThread1.start();
          readThread2.start();
          
          Thread writeThread1 = new Thread(p1WriteRunnable);
          Thread writeThread2 = new Thread(p2WriteRunnable);
          writeThread1.start();
          writeThread2.start();
          
        }
      }
      
      System.out.println("Players are complete!");
      
    } catch(IOException e){
      System.out.println("IOException from acceptConnections()");
    }
  }

  private class ReadFromClient implements Runnable {
    private int playerID;
    private DataInputStream dataIn;

    public ReadFromClient (int pID, DataInputStream in) {
      playerID = pID;
      dataIn = in;
    }

    public void run() {
      try{
        
        while(true){
          if(playerID == 1){
            p1x = dataIn.readInt();
            p1y = dataIn.readInt();
          } else {
            p2x = dataIn.readInt();
            p2y = dataIn.readInt();            
          }
        }
      } catch(IOException e) {
        System.out.println("IOException form RFC run()");
      }
    }
  }

  private class WriteToClient implements Runnable {
    private int playerID;
    private DataOutputStream dataOut;

    public WriteToClient (int pID, DataOutputStream out) {
      playerID = pID;
      dataOut = out;
    }

    /**Method used to send out the values of the x and y coordinates of the players to the client**/
    public void run() {
      try {

        while(true){
          if (playerID == 1){
            dataOut.writeInt(p2x);
            dataOut.writeInt(p2y);  
            dataOut.flush();
          } else {
            dataOut.writeInt(p1x);
            dataOut.writeInt(p1y);  
            dataOut.flush();            
          }
          try {
            Thread.sleep(25);
          }catch(InterruptedException e) {
            System.out.println("InterruptedException from WTC run()");
          }
        }
      
      } catch(IOException e) {
        System.out.println("IOException from WTC run()");
      }
    }

    public void sendStartMsg() {
      try{
        dataOut.writeUTF("We now have 2 players. Let's play!");
        
      } catch(IOException e) {
        System.out.println("IOException from sendStartMsg()");
      }
    }
  }

  public static void main(String[] args) {
    GameServer gs = new GameServer();
    gs.acceptConnections();
  }
}