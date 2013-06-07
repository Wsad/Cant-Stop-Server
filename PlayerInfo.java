import java.io.Serializable;

public class PlayerInfo implements Serializable {
	private int wins, losses, games;
	private String password;
	private String username;
	
	public PlayerInfo(String passwordIn){
		password = passwordIn;
		wins = 0;
		losses = 0;
		games = 0;
	}
	
	public String getPassword(){
		return password;
	}
	
}
