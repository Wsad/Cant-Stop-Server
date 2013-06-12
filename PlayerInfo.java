/** @author Alex Ringeri PlayerInfo Class */

import java.io.Serializable;

public class PlayerInfo implements Serializable {
	private int wins, losses, games;
	private String password;
	private String username;

	/** Constructor: creates PlayerInfo object accepts the password entered and sets 	*		 wins, losses and games to 0.
	*		 @param  passwordIn 	-represents the password player enters	*/	
	public PlayerInfo(String passwordIn, String usernameIn){
		password = passwordIn;
		wins = 0;
		losses = 0;
		games = 0;
		username = usernameIn;
	}

	/** Method getPassword to retrieve the password 
	*	   @param void
	*	   @return String password 	     */
	public String getPassword(){
		return password;
	}
	public void addWin(){
		wins++;
	}
	public void addLoss(){
		losses++;
	}
	public void addGame(){
		games++;
	}
	public int getWins(){
		return wins;
	}
	public int getLosses(){
		return losses;
	}
	public int getGames(){
		return games;
	}
	public int getPoints(){
		int p = this.getWins()*10 - this.getLosses()*10;
		return p;
	}
	
	public void setUsername(String userIn){
		username = userIn;
	}
	
	public String toString(){
		return username + "," + wins + "," + games + "," + getPoints();
	}
}
