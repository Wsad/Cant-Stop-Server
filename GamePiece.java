/** @author Alex Ringeri and Nigel Gauvin
 *	GamePiece class that represents a game piece on a Column
 */

public class GamePiece {
	/** The height of the game piece on its column (between 0 and 13) */
	private int height;
	/** The player number which this game piece is assigned.*/
	private int player;
	/** Boolean to represent whether the game piece is a final piece or temporary piece*/
	private boolean isFinal;
	
	/**
	 * Constructor, creates a new game piece object.
	 * @param playerIn The player number to be assigned for the game piece.
	 */
	public GamePiece(int playerIn){
		height = 0;
		player = playerIn;
		isFinal = false;
	}
	
	/**
	 * Constructor: overloaded to accept a player and height
	 * @param playerIn The player number to be assigned for the game piece.
	 * @param heightIn The height to be assigned for the game piece.
	 */
	public GamePiece(int playerIn, int heightIn){
		height = heightIn;
		player = playerIn;
		isFinal = false;
	}
	
	/**
	 * Method to advance piece along column.
	 */
	public void advance(){
		height++;
	}
	
	/**
	 * Gets the height of the game piece.
	 * @return height of the game piece.
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * Gets the player number from game piece.
	 * @return the assigned player number.
	 */
	public int getPlayer(){
		return player;
	}
	
	/**
	 * Sets the height of the game piece.
	 * @param h The value that the game piece's height will be changed to.
	 */
	public void setHeight(int h){
		if (h >= 0 && h <= 13)//maybe add check for the column height
		height = h;
	}
	
	/**
	 * Sets the isFinal variable. Change the game piece type.
	 * @param b A boolean that isFinal will be changed to.
	 */
	public void setFinal(boolean b){
		isFinal = b;
	}
	
	/**
	 * Will return whether the game piece is temporary or final. (true = final, false = temporary)
	 * @return the isFinal variable
	 */
	public boolean isFinal(){
		return isFinal;
	}
}
