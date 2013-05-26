/** @author Nigel Gauvin and Alex Ringeri Column class: number, height, conquered and an ArrayList of Gamepieces*/
import java.util.ArrayList;

public class Column {
	private int num, height;
	private boolean conquered;
	private ArrayList<GamePiece> pieces;
	
	
	/**Column Constructor:Accepts the number and height from the parameter, sets conquered to false and initializes the ArrayList of GamePieces
	 *  @param NumberIn, HeightIn @return: Void  */
	public Column(int numIn, int heightIn) {
		num = numIn;
		height = heightIn;
		conquered = false; 
		pieces = new ArrayList<GamePiece>();
	}
	
	/**Method getHeight @param: Void @return height */
	public int getHeight (){
		return height;
	}
	
	/**Method getNum @param: Void @return num */
	public int getNum (){
		return num;
	}
	
	/**Method getConquered @param: Void @return conquered */
	public boolean getConquered(){
		return conquered;
	}
	
	/**Method addPiece @param: GamePiece gpIn @return Void */
	public void addPiece(GamePiece gpIn){
		pieces.add(gpIn);
	}
	
	/**Method getTempPiece @param: Integer player @return GamePiece */
	public GamePiece getTempPiece(int player){
		for (int i = 0; i < pieces.size(); i++){
			if ((pieces.get(i).getPlayer() == player) && (!pieces.get(i).isFinal()))
				return pieces.get(i);
		}
		return null;
	}
	
	/**Method getFinalPiece @param: integer player @return GamePiece */
	public GamePiece getFinalPiece(int player){
		for (int i = 0; i < pieces.size(); i++){
			if ((pieces.get(i).getPlayer() == player) && (pieces.get(i).isFinal()))
				return pieces.get(i);
		}
		return null;
	}
	
	/**Method containsTemp @param: Player p @return boolean */
	public boolean containsTemp(Player p){
		for (int i = 0; i < pieces.size(); i++){
			if ((pieces.get(i).getPlayer() == p.getPlayerNum()) && (!pieces.get(i).isFinal()))
				return true;
		}
		return false;
	}
	
	/**Method getTopPiece @param: Void @return GamePiece */
	public GamePiece getTopPiece(){
		GamePiece topPiece = null;
		for (int i =0; i<pieces.size(); i++){
			if (pieces.get(i).getHeight()== height)
				topPiece = pieces.get(i);
		}
		return topPiece;
	}
	
	/**Method getTempPiece @param: Player p @return boolean */
	public boolean containsFinal(Player p){
		for (int i = 0; i < pieces.size(); i++){
			if ((pieces.get(i).getPlayer() == p.getPlayerNum()) && (pieces.get(i).isFinal()))
				return true;
		}
		return false;
	}
	
	/**Method setHeight @param: Integer h @return Void */
	public void setHeight(int h){
		String error = "You have not entered an acceptable height";
		if ((h >= 3) && (h <= 13))
			height = h;	
		else 
			System.out.println(error);
	}
	
	/**Method setNum @param: Integer n @return Void */
	public void setNum(int n){
		String msg = "You have not entered an acceptable num";
		if (n >=2 && n <= 12){
			num = n; 			  }
		else 						{
			System.out.println(msg); }
	}
	
	/**Method setConquered @param: boolean c @return conquered */
	public void setConquered(boolean c){
		conquered = c;
	}
	
	

}
