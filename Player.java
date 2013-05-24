/** @author Alex Ringeri
	A Player class to represent the a player for the Can't Stop board game
*/

import java.io.*;
import java.net.*;

public class Player{
	private int playerNum;
	private int wins;
	private Connection connection;
	
	public Player(int numIn, Socket socketIn){
		playerNum = numIn;
		wins = 0;
		connection = new Connection(socketIn);
	}
	
	public void closeConnection(){
		connection.close();
	}
	
	public void send(String info){
		connection.print(info);
	}
	
	public String readSplitChoice(){
		connection.read();
	}
	
	public boolean readTurnChoice(){
		if (connection.read().equals("Roll"))
			return true;
		else if (connection.read().equals("Stop"))
			return false;
		else {
			System.out.println("Improper input from client for turn choice");
			return false;
		}
	}
	
	public void addWin(){
		wins++;
	}
	
	public int getPlayerNum(){
		return playerNum;
	}
	
	public int getWins(){
		return wins;
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public void setNumber(int numIn){
		if (numIn >= 1)
			playerNum = numIn;
		else
			System.out.println("Invalid player number. Method: Player.setNumber");
	}
	
	public void setWins(int winsIn){
		if (winsIn >= 0)
			wins = winsIn;
		else
			System.out.println("Invalid win value. Method: Player.setWins");
	}
}