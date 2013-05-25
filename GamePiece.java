public class GamePiece {
	private int height;
	private int player;
	private boolean isFinal;
	
	
	public GamePiece(int playerIn){
		height = 0;
		player = playerIn;
		isFinal = false;
	}
	
	public GamePiece(int playerIn, int heightIn){
		height = heightIn;
		player = playerIn;
		isFinal = false;
	}
	
	public void advance(){
		height++;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getPlayer(){
		return player;
	}
	
	public void setHeight(int h){
		if (h >= 0 && h <= 13)//maybe add check for the column height
		height = h;
	}
	
	public void setFinal(boolean b){
		isFinal = b;
	}
	
	public boolean isFinal(){
		return isFinal;
	}
}
