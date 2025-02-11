/**
    This class serves as the entry point for starting the game. 
    It creates an instance of the GameFrame class, connects to the server, 
    and sets up the graphical user interface for the game.
    
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

public class GameStarter {

	public static void main(String[] args) {
    GameFrame gf = new GameFrame();
    gf.connectToServer();
		gf.setUpGUI();
	}
}