import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class GameManager {
	private ArrayList<Player> players;
	private Board board;
	private int turn;// port;
	private ServerSocket serverSocket;
	
	public GameManager(int portIn){
		players = new ArrayList<Players>();
		board = new Board();
		turn = 0;
		port = portIn;
		serverSocket = null;
	}
	
	public void connectPlayers(int numPlayers, int port){
		Player[] playerInit = new Player[numPlayers]; 
		try {
			serverSocket = new ServerSocket(port);
		} 
		catch (IOException e) {
			System.err.println("Could not listen on port: " + port + ".\n" + e.getMessage());
			System.exit(-1);
		}
		try {
			for (int i = 0; i < numPlayers; i++)
				playerInit[i] = new Player(i+1, serverSocket.accept());
		}
		catch {
			System.err.println("Player accept failed: " + e.getMessage());
			System.exit(-1);
		}
		try {
			for (int i = 0; i < numPlayers; i++)
				playerInit[i].send(i + 1);
		}
		catch {
			System.err.println("Player Message not delivered: " + e.getMessage());
			System.exit(-1);
		}
	}
	
	public void setPieces(Player p){//FIX!!
		if (p.readTurnChoice()){ //recieved "Roll" from client
			String roll = Dice.roll();
			try {
				p.send(roll);//send roll info to player
			}
			catch {
				System.err.println("Player roll message not delivered: " + e.getMessage());
				System.exit(-1);
			}
			try {
				String split = p.readSplitChoice();//read split from player
				while (!validSplit(split)){//if split is invalid, loop until proper split is recieved.
					p.send("err");
					split = p.readSplitChoice();
				}	
				//if valid split, place pieces on available columns
				Scanner splitScan = new Scanner(split);
				int d1 = splitScan.nextInt();
				int d2 = splitScan.nextInt();
				Column c1 = board.getColumn(d1);
				Column c2 = board.getColumn(d2);
				
				if(!c1.getConquered){//if not conquered
					if (c1.containsTemp(p))
						//advance temp piece
					else if (c1.containsFinal(p))
						//set piece above final
					else
						c1.addPiece(new GamePiece(p.getPlayerNum));
				}
				if(!c2.getConquered){//if not conquered
					if (c2.containsTemp(p))
						//advance temp piece
					else if (c2.containsFinal(p))
						//set piece above final
					else
						c2.addPiece(new GamePiece(p.getPlayerNum));
				}
				/*
				if (board.availableColumns().contains(board.getColumn(d1))//if column for d1 is available
					board.getColumn(d1).addGamePiece(p.getPlayerNum());//place gamepiece for d1
				if (board.availableColumns().contains(board.getColumn(d2))
					board.getColumn(d2).addGamePiece(p.getPlayerNum());//place gamepiece for d2 */
			}
			catch {
				System.err.println("Error reading split: " + e.getMessage());
				System.exit(-1);
			}
	}
	
	public void setFinal(Player p){
		ArrayList<GamePiece> tempPieces = board.getTempPieces(p);
		for (int i = 0; i < tempPieces.size(); i++)
			tempPieces.get(i).setFinal(true);
	}
	
	public boolean hasWon(Player p){
		ArrayList<Column> conqueredList = board.getConqueredCol()
		int conquered = 0;
		for (int i =0; i < conqueredList.size() ; i++){
			if(conqueredList.get(i).getTopPiece().getPlayer() == p.getPlayerNum())
				conquered++;
		}
		if (conquered >= 3)
			return true;
		else
			return false;
	}
	
	public void turn(Player p){
		//fill here
	}
	
	public static void main(String[] args){
		GameManager gm = new GameManager(Integer.parseInt(args[0]));
	}
	
	public boolean validSplit(String in){
		//fill here
	}
}
	