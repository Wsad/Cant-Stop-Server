public class GamePiece {
	private int height;
	private int player;
	private boolean isFinal;
	
	
	public GamePiece(int playerIn){
		height = 0;
		player = playerIn;
		isFinal = false;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int h){
		if (h >= 0 && h <= col.getHeight())
		height = h;
	}
	
	public void setFinal(boolean b){
		isFinal = b;
	}
	
	public boolean isFinal(){
		return isFinal;
	}
}
