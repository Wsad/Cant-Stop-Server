/** @author Alex Ringeri and Nigel Gauvin Board Class to contain Column objects and Manage 
 * GamePiece objects on the columns. */

import java.util.ArrayList;

public class Board {
	
	Column[] columns = new Column[11];

	/** Board Constructor: creates the Board with 11 columns 
	*	@param void
	*	@return void				    */
	public Board(){
		columns[0] = new Column(2,3);
		columns[1] = new Column(3,5);
		columns[2] = new Column(4,7);
		columns[3] = new Column(5,9);
		columns[4] = new Column(6,11);
		columns[5] = new Column(7,13);
		columns[6] = new Column(8,11);
		columns[7] = new Column(9,9);
		columns[8] = new Column(10,7);
		columns[9] = new Column(11,5);
		columns[10] = new Column(12,3);		
	}
	
	/** Method getConqueredCols to retrieve the list of conquered columns
	*	  @param void
	*	  @return ArrayList<Column> 				*/
	public ArrayList<Column> getConqueredCols(){
		ArrayList<Column> ret = new ArrayList<Column>();
		for (int i =0; i < columns.length; i++){
			if (columns[i].getConquered())
				ret.add(columns[i]);
		}
		return ret;
	}
	
	/** Method getColumn to retrieve a specific column
	*	  @param int col 	-representing the column number
	*	  @return null	*/
	public Column getColumn(int col){
		if ((col > 1) && (col < 13))
			return columns[col-2];
		System.out.println("Invalid column number: getColumn method");
		return null;
	}
	
	/** Method getColArr to retrieve the array of eleven columns
	*	  @param void
	*	  @return Column[] columns	-holds eleven columns objects */
	public Column[] getColArr(){
		return columns;
	}
	
	/* Method getNumPieces to retrieve the number of temporary pieces for a player
		  @param Player p 	-Player object
		  @return int count	-count of the temporary objects for player p */ 
	public int getNumPieces(Player p){
		int count = 0;
		for (int i=0; i<columns.length; i++){
			if (columns[i].containsTemp(p))
				count++;
		}
		return count;
	}

	/** Method getFinalPieces to retrieve the list of final GamePiece objects
	* 	 @param Player p 		-Player Object
	*	 @return ArrayList<GamePiece>	-list of final game piece objects 
						for Player p			*/

	public ArrayList<GamePiece> getFinalPieces(Player p){
		ArrayList<GamePiece> retPieces = new ArrayList<GamePiece>();
		for (int i=0; i<columns.length; i++){
			GamePiece temp = columns[i].getFinalPiece(p.getPlayerNum());
			if (temp != null){
				retPieces.add(temp);
			}
		}
		return retPieces;
	}
	/** Method getTempPieces to retrieve the list of temporary GamePiece objects
	* 	 @param Player p 		-Player Object
	*	 @return ArrayList<GamePiece>	-list of temporary GamePiece objects 
						for Player p			*/
	public ArrayList<GamePiece> getTempPieces(Player p){
		ArrayList<GamePiece> retPieces = new ArrayList<GamePiece>();
		for (int i=0; i<columns.length; i++){
			GamePiece temp = columns[i].getTempPiece(p.getPlayerNum());
			if (temp != null){
				retPieces.add(temp);
			}
		}
		return retPieces;
	}
	
	/** Method clearTemp to clear the temporary pieces for a specific player
	 * 	  @param Player p 	-Player object
	 * 	  @return void							*/
	public void clearTemp(Player p){
		for (int i=0; i<columns.length; i++){
			GamePiece piece = columns[i].getTempPiece(p.getPlayerNum());
			if (piece != null)
				piece.setFinal(false);
		}
	}
}
