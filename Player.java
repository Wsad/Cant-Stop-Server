/** @author Alex Ringeri
	A Player class to represent the a player for the Can't Stop board game
*/

//import java.io.*;
import java.net.*;
import java.io.*;

public class Player{
	/** The player number which is assigned base on connection order.*/
	private int playerNum;
	/** Contains the number of times the player has conquered 3 columns.*/
	private int wins;
	/** A Connection object which will be used to communicate between the server and client*/
	private Connection connection;
	
	/**
	 * Constructor: creates a Player object
	 * @param numIn The player number that is assigned to the player.
	 * @param socketIn A socket that is connected to the client
	 */
	public Player(int numIn, Socket socketIn){
		playerNum = numIn;
		wins = 0;
		connection = new Connection(socketIn);
	}
	
	/**
	 *  Closes the connection between server and players.
	 *  Used at the end of games to disconnect the clients.
	 */
	public void closeConnection(){
		connection.close();
	}
	
	/**
	 * Sends a string through the TCP network to the player.
	 * @param info The String that will be sent to the player.
	 */
	public void send(String info){
		connection.print(info);
	}
	
	/**
	 * Reads the split of the dice choice, used by the GameManager class during the validSplit method. 
	 * The return will be a string of two numbers, delimited by a comma or 
	 * the string "crap" which indicates the player cannot make a move.
	 * @return String of two numbers delimited by a comma or "crap" 
	 */
	public String readSplitChoice() throws IOException{
		return connection.read();
	}
	
	/**
	 * Reads the turn choice from the players. Allows the GameManager to know which option the player selects (either roll or stop). 
	 * The method will return a Boolean, true if the player wants to roll again and false if the player wants to stop.
	 * @return True if received "roll" and False is received "stop"
	 */
	public boolean readTurnChoice() throws IOException{
		if (connection.read().equals("roll"))
			return true;
		else if (connection.read().equals("stop"))
			return false;
		else {
			System.out.println("Improper input from client for turn choice");
			return false;
		}
	}
	
	/**
	 * Adds a win to the player's wins variable when they conquer a column.
	 */
	public void addWin(){
		wins++;
	}
	
	/**
	 * Gets the assigned player number.
	 * @return The player number of the player.
	 */
	public int getPlayerNum(){
		return playerNum;
	}
	
	/**
	 * Gets the number of wins for the player.
	 * @return number of wins of the player.
	 */
	public int getWins(){
		return wins;
	}
	
	/**
	 * Gets the connection from the player.
	 * @return The connection object the player uses.
	 */
	public Connection getConnection(){
		return connection;
	}
	
	/**
	 * Sets the player number to the specified value.
	 * @param numIn The value that the player number will be changed to.
	 */
	public void setNumber(int numIn){
		if (numIn >= 1)
			playerNum = numIn;
		else
			System.out.println("Invalid player number. Method: Player.setNumber");
	}
	
	/**
	 * Sets the number of wins for a player.
	 * @param winsIn The value that the wins will be set to.
	 */
	public void setWins(int winsIn){
		if (winsIn >= 0)
			wins = winsIn;
		else
			System.out.println("Invalid win value. Method: Player.setWins");
	}
}