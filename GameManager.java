import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class GameManager {
	private ArrayList<Player> players;
	private Board board;
	private int turn, port;
	private static int numPlayers = 2;
	private ServerSocket serverSocket;
	
	public GameManager(){
		players = new ArrayList<Player>();
		board = new Board();
		turn = 0;
		serverSocket = null;
	}
	
	public void connectPlayers(int numPlayers, int portIn){
		port = portIn;
		try {
			serverSocket = new ServerSocket(portIn);
		} 
		catch (IOException e) {
			System.err.println("Could not listen on port: " + portIn + ".\n" + e.getMessage());
			System.exit(-1);
		}
		try {
			for (int i = 0; i < numPlayers; i++)
				players.add(new Player(i+1, serverSocket.accept()));
		}
		catch (IOException e) {
			System.err.println("Player accept failed: " + e.getMessage());
			System.exit(-1);
		}
		for (int i = 0; i < numPlayers; i++)
			players.get(i).send(""+(i+1));
	}
	
	public void setPieces(int p){//FIX!!
		Player player = players.get(p-1);
		player.send("go");
		if (player.readTurnChoice()){ //received "Roll" from client
			String roll = Dice.roll();
			player.send(roll);//send roll info to player
			String split = player.readSplitChoice();//read split from player
			
			while (!validSplit(split)){//if split is invalid, loop until proper split is received.
				player.send("err");
				split = player.readSplitChoice();
			}
			
			if (canMove(player,roll)){
				//if valid split, place pieces on available columns
				Scanner splitScan = new Scanner(split);
				int d1 = splitScan.nextInt();
				int d2 = splitScan.nextInt();
				Column c1 = board.getColumn(d1);
				Column c2 = board.getColumn(d2);
				
				if(!c1.getConquered()){//if not conquered
					if (c1.containsTemp(player))
						advPiece(player.getPlayerNum(),d1);//advance temp piece
					else if (c1.containsFinal(player))
						setPiece(player.getPlayerNum(),d1);//set piece above final
					else
						c1.addPiece(new GamePiece(player.getPlayerNum()));
				}
				if(!c2.getConquered()){//if not conquered
					if (c2.containsTemp(player))
						advPiece(player.getPlayerNum(),d2);//advance temp piece
					else if (c2.containsFinal(player))
						setPiece(player.getPlayerNum(),d2);//set piece above final
					else
						c2.addPiece(new GamePiece(player.getPlayerNum()));
				}
				player.send("ack");
				for (int i=0; i<numPlayers; i++){//send other players roll info
					if (players.get(i) != player) 
						players.get(i).send(roll);
				}
			}
			else { //player craps out
				board.clearTemp(player);
			}
		}
	}
	
	public void setFinal(Player p){
		ArrayList<GamePiece> tempPieces = board.getTempPieces(p);
		for (int i = 0; i < tempPieces.size(); i++)
			tempPieces.get(i).setFinal(true);
	}
	
	public boolean hasWon(Player p){
		ArrayList<Column> conqueredList = board.getConqueredCols();
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
	
	public boolean canMove(Player p, String roll){
		Scanner rollScan = new Scanner(roll);
		int d1 = rollScan.nextInt();
		int d2 = rollScan.nextInt();
		int d3 = rollScan.nextInt();
		int d4 = rollScan.nextInt();
		
		int[] cols = { d1+d2, d1+d3, d1+d4, d2+d3, d2+d4, d3+d4 };
		int tempCounter = 0;
		for (int i=0; i<cols.length; i++){
			if (board.getColumn(i).getConquered())
				return false;
			else if (board.getColumn(i).containsTemp(p)){
				tempCounter++;
				return true;
			}
		}
		if (board.getNumPieces(p) < 3)
			return true;
		else
			return false;
		//for each possible move: 	check whether col is conquered -> return false
		//							Check whether player has temp piece ->return true (count piece)
		//							If there are no temp pieces, count total number of temp pieces.
		//								if number of temp pieces is less than 3 -> return true
		//								else return false (crapped out)
	}
	
	public boolean setPiece(int player, int col){
		Column column = board.getColumn(col);
		GamePiece gamePiece = column.getFinalPiece(player);
		if (gamePiece != null){
			column.addPiece(new GamePiece(player, gamePiece.getHeight()+1)); 
			return true;
		}
		else if (gamePiece == null){
			column.addPiece(new GamePiece(player));
			return true;
		}
		else
			return false;
	}

	public boolean advPiece(int player, int col){
		Column column = board.getColumn(col);
		GamePiece gamePiece = column.getTempPiece(player);
		if (column.getHeight() == gamePiece.getHeight()){
			column.setConquered(true);
			return false;
		}
		else if (column.getHeight() -1 == gamePiece.getHeight()){
			gamePiece.advance();
			column.setConquered(true);
			return true;
		}
		else if (gamePiece != null){
			gamePiece.advance();
			return true;
		}
		else
			return false;
	}
	
	public void turn(Player p){
		//fill here
	}
	
	public void disconnect(){
		for(int i=0;i<numPlayers;i++){
			players.get(i).send("closing connection: ");
			players.get(i).closeConnection();
		}
	}
	
	public static void main(String[] args){
		GameManager gm = new GameManager();
		gm.connectPlayers(numPlayers, Integer.parseInt(args[0]));
		for (int i=0; i<numPlayers;i++)
			gm.setPieces(i+1);
		gm.disconnect();
	}
	
	
	public boolean validSplit(String in){
		return true;//fill here
	}
}
	