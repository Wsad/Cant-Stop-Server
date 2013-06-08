/** @author Alex Ringeri PlayerInfo Class */

import java.io.Serializable;

public class PlayerInfo implements Serializable {
	private int wins, losses, games;
	private String password;
	private String username;

	/** Constructor: creates PlayerInfo object accepts the password entered and sets 	*		 wins, losses and games to 0.
	*		 @param  passwordIn 	-represents the password player enters	*/	
	public PlayerInfo(String passwordIn){
		password = passwordIn;
		wins = 0;
		losses = 0;
		games = 0;
	}

	/** Method getPassword to retrieve the password 
	*	   @param void
	*	   @return String password 	     */
	public String getPassword(){
		return password;
	}
	
}
