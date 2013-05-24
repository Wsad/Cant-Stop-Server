import java.io.*;
import java.net.*;
import //arraylist

public class GameManager {
	private ArrayList<Player> players;
	private Board board;
	private int turn, port;
	private ServerSocket serverSocket;
	
	public GameManager(int portIn){
		players = new ArrayList<Players>();
		board = new Board();
		turn = 0;
		port = portIn;
		serverSocket = null;
	}
	
	public static void main(String[] args){
		GameManager gm = new GameManager(Integer.parseInt(args[0]));
	}
	
	public boolean validSplit(String in){
		//fill here
	}
}
	