public class GamePiece {
	private int height;
	private int player;
	private boolean isFinal;
	
	
	public GamePiece(int playerIn){
		height = 0;
		player = playerIn;
		isFinal = false;
	}
	
	
	//Method may no longer need this method: Can be taken care of in Column class.
	public boolean atTop(){
		boolean top = false;
		if (height == col.getHeight()){
			top = true;
		}
		return top;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int h){
		if (h >= 0 && h <= col.getHeight())
		height = h;
	}
	
	public boolean isFinal(){
		return isFinal;
	}
}
