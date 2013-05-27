import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.ArrayList;

public class GameManager {
	private ArrayList<Player> players;
	private Board board;
	private static boolean run;
	private int port;
	private static int numPlayers = 2;
	private ServerSocket serverSocket;
	
	public GameManager(){
		players = new ArrayList<Player>();
		board = new Board();
		serverSocket = null;
		run = true;
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
	
	public void turn(int p){
		boolean turn = true;
		Player player = players.get(p-1);
		while (turn){
			String turnchoice = player.getConnection().read();
			if (turnchoice.equals("roll")){ //received "Roll" from client
				String roll = Dice.roll();
				String diceSplit = Dice.split();
				for (int i=0; i<players.size(); i++)
					players.get(i).send(roll);//Send roll information to each player.
				String split = player.readSplitChoice();//read split from player
				while (!validSplit(diceSplit, split)){//if split is invalid, loop until proper split is received.
					player.send("err");
					split = player.readSplitChoice();
				}
				
				int numTemp = board.getNumPieces(player);
				
				if (canMove(player, roll, numTemp)){
					//if valid split and the player can move: place pieces on available columns
					Scanner splitScan = new Scanner(split).useDelimiter(",");
					int d1 = splitScan.nextInt();
					int d2 = splitScan.nextInt();
					Column c1 = board.getColumn(d1);
					Column c2 = board.getColumn(d2);
					
					if(!c1.getConquered()){//if not conquered
						if (c1.containsTemp(player)){
							advPiece(player.getPlayerNum(),d1);
						}//advance temp piece
						else if (numTemp < 3){
							if (c1.containsFinal(player)){
								setPiece(player.getPlayerNum(),d1);//set piece above final
							}else{
								c1.addPiece(new GamePiece(player.getPlayerNum()));
							}
							numTemp++;
						}
					}
					if(!c2.getConquered()){//if not conquered
						if (c2.containsTemp(player))
							advPiece(player.getPlayerNum(),d2);//advance temp piece
						else if (numTemp < 3){
							if (c2.containsFinal(player))
								setPiece(player.getPlayerNum(),d2);//set piece above final
							else
								c2.addPiece(new GamePiece(player.getPlayerNum()));
							numTemp++;
						}
					}
					player.send("ack");
					for (int i=0; i<numPlayers; i++){//send other players roll info
						if (players.get(i) != player) 
							players.get(i).send(split);
					}
					turn = true; //loop again
				}
				else { //player craps out
					turn = false;//stop loop
					board.clearTemp(player);
					player.send("ack");
					if (p == players.size())//if active player is last in array send go to first player
						players.get(0).send("go");
					else					//else send to next player (is p since list is from 0 to numPlayers - 1.
						players.get(p).send("go");
				}
			}else{//Player chooses to stop.
				turn = false;//stop loop
				board.getFinalPieces(player);
				setFinal(player);
				if (hasWon(player)){
					player.addWin();
					for (int i=0; i<numPlayers; i++)
						players.get(i).send("P" + player.getPlayerNum()+ " has Won");
					/**if (playAgain()){ 		Play again.
						board = new Board();
						run = true;
						players.get(0).send("go");
					}
					else*/
						run = false;
				}
				else if (p == players.size())//if active player is last in array send go to first player
					players.get(0).send("go");
				else					//else send to next player (is p since list is from 0 to numPlayers - 1.
					players.get(p).send("go");
			}
		}		
	}
	
	public boolean playAgain(){
		for (int i =0;i< players.size();i++)
			players.get(i).send("Play Again? Y/N");
		ArrayList<Player> newPlayers = new ArrayList<Player>();
		boolean ret = false;
		for (int i =0;i< players.size();i++){
			if (players.get(i).getConnection().read().equals("Y"))
				newPlayers.add(players.get(i));
				if (i>1)
					ret = true;
		}
		players = newPlayers;
		return ret;
			
	}
	
	public void setFinal(Player p){
		ArrayList<GamePiece> tempPieces = board.getTempPieces(p);
		for (int i = 0; i < tempPieces.size(); i++)
			tempPieces.get(i).setFinal(true);
	}
	
	public boolean hasWon(Player p){
		Column [] col = board.getColArr();
		int conquered = 0;
		for (int i=0;i<col.length;i++){
			GamePiece finalPiece = col[i].getFinalPiece(p.getPlayerNum());
			if ((finalPiece) != null && (finalPiece.getHeight() == col[i].getHeight()))
				conquered++;
		}
		/*System.out.println("Has Won Method");
		ArrayList<Column> conqueredList = board.getConqueredCols();
		
		for (int i =0; i < conqueredList.size() ; i++){
			System.out.println("\nConquered Column " + i+"\n # " +conqueredList.get(i).getNum());
			if(conqueredList.get(i).getTopPiece().getPlayer() == p.getPlayerNum())
				conquered++;
		}*/
		if (conquered >= 3)
			return true;
		else
			return false;
	}
	
	public boolean canMove(Player p, String rollIn, int numberTempPieces){
		Scanner rollScan = new Scanner(rollIn).useDelimiter(",");
		int d1 = rollScan.nextInt();
		int d2 = rollScan.nextInt();
		int d3 = rollScan.nextInt();
		int d4 = rollScan.nextInt();
		
		int[] cols = { d1+d2, d1+d3, d1+d4, d2+d3, d2+d4, d3+d4 };
		int moveCounter =0;//number of possible moves for the player.
		for (int i=0; i<cols.length; i++){
			if (!board.getColumn(cols[i]).getConquered())//if column is not conquered then increase move counter.
				moveCounter++;
			if (board.getColumn(cols[i]).containsTemp(p)){
				return true;
			}
		}
		if (moveCounter == 0)
			return false;
		if (numberTempPieces < 3)
			return true;
		else
			return false;
		//for each possible move: 	check whether all columns are conquered -> return false
		//							Check whether player has temp piece ->return true (count piece)
		//							count total number of temp pieces.
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
	
	public void disconnect(){
		for(int i=0;i<numPlayers;i++){
			players.get(i).send("closing connection: ");
			players.get(i).closeConnection();
		}
	}
	
	public static void main(String[] args){
		GameManager gm = new GameManager();
		gm.connectPlayers(numPlayers, Integer.parseInt(args[0]));
		while (run){
			for (int i=1; i<=numPlayers;i++)
				gm.turn(i);
		}
		gm.disconnect();
	}
	
	
	public boolean validSplit(String diceSplitIn, String splitIn){
		return diceSplitIn.contains(splitIn);
	}
}
	