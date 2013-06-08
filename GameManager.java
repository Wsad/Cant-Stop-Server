/**@author Alex Ringeri	GameManager Class to run the game */

import java.io.*;
import java.net.*;
//import java.util.AbstractMap;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

public class GameManager {
	private ArrayList<Player> players;
	private Board board;
	private boolean run;
	private int port;
	private static final int NUM_PLAYERS = 2;//2;
	private ServerSocket serverSocket;
	private final FileIO USER_INFO;
	private final String USER_FILE = "userInfo.dat";
	
	/** Constructor GameManger holds player, board, serversocket, userinfo and run
	 * 				@param void */
	public GameManager(){
		players = new ArrayList<Player>();
		board = new Board();
		serverSocket = null;
		USER_INFO = new FileIO(USER_FILE);
		run = true;
	}
	
	/** Main Method runs the server and all game logic
	 * @throws ArrayIndexOutOfBoundsException -if port is not found */ 
	public static void main(String[] args){
		GameManager gm = new GameManager();
		//gm.connectPlayers(NUM_PLAYERS, Integer.parseInt(args[0])); //JOSH: What happens if you can't parse the port?
		boolean havePort = false;
		int portIn = 0;
		while (!havePort){
			try {
				portIn = Integer.parseInt(args[0]);
				havePort = true;
			}
			catch (ArrayIndexOutOfBoundsException e){
				System.out.print("Port not found. Enter port number and press enter: ");
				Scanner sc = new Scanner(System.in);
				portIn = sc.nextInt();
				sc.nextInt();
				havePort = true;
			}
		}
		gm.connectPlayers(NUM_PLAYERS, portIn);
		while (gm.runGame()){
			for (int i=1; i<=NUM_PLAYERS;i++)
				gm.turn(i);		
		}
		gm.disconnect();
	}
	
	/**Method runGame to retrive run variable
	 * @param void
	 * @return boolean run */
	public boolean runGame(){
		return run;
	}
	
	/**Method setGame to set the run variable
	 * @param boolean value -set the value of the run boolean
	 * @return void */
	public void setRun(boolean value){
		run = value;
	}
	
	/**Method connectPlayers to connect the players to the server
	 * @param int NUM_PLAYERS -represents the number of players
	 * @param int portIn      -represents the port number
	 * @throws IOException    -if the port number is incorrect
	 * @throws IOException 	  -if the player connection fails */
	public void connectPlayers(int NUM_PLAYERS, int portIn){
		port = portIn;
		try {
			serverSocket = new ServerSocket(portIn);
		} 
		catch (IOException e) {
			System.err.println("Could not listen on port: " + portIn + ".\n" + e.getMessage());
			System.exit(-1);
		}
		
		int numConnected = 0;
		
		//Retrieve map of player info from file "userInfo.dat"
		Map<String,PlayerInfo> userMap = (HashMap<String,PlayerInfo>)USER_INFO.read();
		
		//Keep trying to accept players until two connections are made
		while (numConnected < NUM_PLAYERS){
			try {
				players.add(new Player(numConnected+1, serverSocket.accept()));
				boolean connected = false;
				//while the player has not connected
				while (!connected){
					Player player = players.get(numConnected);
				
					String userMessage = player.getConnection().read();//read user name string from client
					String username = userMessage.substring(2);
					
					//If client is a new user
					if (userMessage.charAt(0)== 'N'){
						if (userMap.containsKey(username)){//if user name already exists send error
							player.send("err,Duplicate User Name");
						}else{
							player.send("ack");
							String password = player.getConnection().read();
							userMap.put(username,new PlayerInfo(password));//add user info to map
							player.send("ack");
							USER_INFO.writeObject(userMap);//write map to file.
							//USER_INFO.flush();
							connected = true;
							numConnected++;
						}
					}
					//If client is a returning user
					else if (userMessage.charAt(0)== 'R'){
						if (userMap.containsKey(username)){//if username exists
							player.send("ack");
							String password = player.getConnection().read();//read password string from client
							if (userMap.get(username).getPassword().equals(password)){//if password matches
								player.send("ack");
								connected = true;
								numConnected++;
							}
							else
								player.send("err,Invalid Password");//password doesn't match
						}else
							player.send("err,Unknown User");//user not found
					}
				}
			}
			catch (IOException e) {
				//The server loses connection to a client while they try to set up user name.
				//Do not increment numConnected and return to top of while loop.
				System.err.println("Player accept failed: " + e.getMessage());
			}
			
		}
		USER_INFO.close();
	}
	
	/**Method turn allows players to roll, detects valid moves, runs the game logic
	 * @param int p 		-represents the player number
	 * @return void
	 * @throws IOException	-when one player disconnects */
	public void turn(int p){
		boolean turn = true;
		Player player = players.get(p-1);
		while (turn){
			try {
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
						for (int i=0; i<NUM_PLAYERS; i++){//send other players roll info
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
					//printFinal(player);Debug
					if (hasWon(player)){
						player.addWin();
						player.send("you won");
						for (int i=0; i<NUM_PLAYERS; i++){
							if (players.get(i)!= player)
								players.get(i).send("you lost");
						}
						run = false;
					}
					else if (p == players.size())//if active player is last in array send go to first player
						players.get(0).send("go");
					else					//else send to next player (is p since list is from 0 to numPlayers - 1.
						players.get(p).send("go");
				}
			}
			catch (IOException playerDisconnect){
				//If a player disconnects mid game, the server will not be able to read from the sock.
				//It will throw an IOException, and be caught here.
				//The player still connected will win the game and the server will reset to accept connections again.
				for (int i=0; i<NUM_PLAYERS; i++){
					if (players.get(i)!= player)
						players.get(i).send("you won");
				}
				turn = false;
				run = false;
			}
		}		
	}
	
	/**Method printFinal to print the column number and height of the final pieces of a specific player
	 * @param Player p 		-Player object
	 * @return void */
	public void printFinal(Player p){
		Column[] col = board.getColArr();
		for (int i=0;i<col.length;i++){
			if (col[i].getFinalPiece(p.getPlayerNum()) != null){
				System.out.print("Column "+(i+2)+" :"+col[i].getFinalPiece(p.getPlayerNum()).getHeight());
				if (col[i].getConquered())
					System.out.println(" Conquered : Y");
				else 
					System.out.println(" Conquered : N");
			}
		}
	}
	
	/**Method setFinal to set temp pieces to final for a player
	 * @param Player p 		-Player object
	 * @return void */
	public void setFinal(Player p){
		ArrayList<GamePiece> tempPieces = board.getTempPieces(p);
		for (int i = 0; i < tempPieces.size(); i++)
			tempPieces.get(i).setFinal(true);
	}
	
	/**Method hasWon checks if the player has won
	 * @param Player p 		-Player object
	 * @return boolean */
	public boolean hasWon(Player p){
		Column [] col = board.getColArr();
		int conquered = 0;
		for (int i=0;i<col.length;i++){
			GamePiece finalPiece = col[i].getFinalPiece(p.getPlayerNum());
			if ((finalPiece) != null && (finalPiece.getHeight() == col[i].getHeight()))
				conquered++;
		}
		
		if (conquered >= 3)
			return true;
		else
			return false;
	}
	
	/**Method canMove to check if the player can move a GamePiece
	 * @param Player p 				-Player object
	 * @param String rollIn  		-the roll of the player
	 * @param int numberTempPieces  -number of temp pieces of the player
	 * @return boolean */
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
	
	/**Method setPiece to set a GamePiece object to a column for a specific player
	 * @param Player p 		-Player object
	 * @param int col 		-column number 
	 * @return boolean		 */
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

	/**Method advPiece to advance a GamePiece up acolumn for a specific player
	 * @param Player p 		-Player object
	 * @param int col 		-column number 
	 * @return boolean		 */
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
		return false;
	}
	
	/**Method disconnect closes the connection between players
	 * @param void
	 * @return void		 */
	public void disconnect(){
		for(int i=0;i<NUM_PLAYERS;i++){
			players.get(i).send("closing connection: ");
			players.get(i).closeConnection();
		}
	}
	
	/**Method setPort the port variable
	 * @param int portIn 	-represents port number
	 * @return void		 */
	public void setPort(int portIn){
		port = portIn;
	}
	
	/**Method validSplit check if the split is valid, also handles crapping out
	 * @param String diceSplitIn 	-the split options for the player
	 * @param String splitIn		-the option the player chooses
	 * @return void		 */
	public boolean validSplit(String diceSplitIn, String splitIn){
		return ((diceSplitIn.contains(splitIn))||(splitIn.contains("crap")));
	}
}
	