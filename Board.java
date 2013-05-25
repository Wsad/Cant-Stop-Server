import java.util.ArrayList;

public class Board {
	
	Column[] columns = new Column[11];
	
	/*public static void main (String []args){
		Board b1 = new Board ();
		System.out.println(b1.toString());
		b1.getList();
	}*/
	
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
	
	//public boolean updateColumns(){
		
	//	boolean [] col = this.getList(); 
	//	for (int i =0; i< col.length; i++) {
			//if (i == )
			
	//										}
	//								}
	
	
	
	/*public int[] getList() {
		String text = " ";
		int [] list = new int[11];
	
		list[0] = c2.getHeight();
		list[1] = c3.getHeight();
		list[2] = c4.getHeight();
		list[3] = c5.getHeight();
		list[4] = c6.getHeight();
		list[5] = c7.getHeight();
		list[6] = c8.getHeight();
		list[7] = c9.getHeight();
		list[8] = c10.getHeight();
		list[9] = c11.getHeight();
		list[10] = c12.getHeight();
	
		for (int i = 0; i<list.length; i++){
			text += " " + list[i];
		}
		System.out.println (text);
		return list;
	}*/
	
	public ArrayList<Column> getConqueredCols(){
		ArrayList<Column> ret = new ArrayList<Column>();
		for (int i =0; i < columns.length; i++){
			if (columns[i].getConquered())
				ret.add(columns[i]);
		}
		return ret;
	}
	
	public Column getColumn(int col){
		if ((col > 1) && (col < 13))
			return columns[col-2];
		System.out.println("Invalid column number: getColumn method");
		return null;
	}
	
	public Column[] getColArr(){
		return columns;
	}
	
	public int getNumPieces(Player p){
		int count = 0;
		for (int i=0; i<columns.length; i++){
			if (columns[i].containsTemp(p))
				count++;
		}
		return count;
	}
	
	public ArrayList<GamePiece> getTempPieces(Player p){
		ArrayList<GamePiece> retPieces = new ArrayList<GamePiece>();
		for (int i=0; i<columns.length; i++){
			retPieces.add(columns[i].getTempPiece(p.getPlayerNum()));
		}
		return retPieces;
	}
	
	public void clearTemp(Player p){
		for (int i=0; i<columns.length; i++){
			GamePiece piece = columns[i].getTempPiece(p.getPlayerNum());
			if (piece != null)
				piece.setFinal(false);
		}
	}
	
	/*public String toString(){
		String text = "Column2 : " + c2.getHeight() + "\n" + "Column3 : " + c3.getHeight() 
		+ "\n" + "Column4 : " + c4.getHeight() + "\n" + "Column5 : " + c5.getHeight() 
		+ "\n" + "Column6 : " + c6.getHeight() + "\n" + "Column7 : " + c7.getHeight() 
		+ "\n" + "Column8 : " + c8.getHeight() + "\n" +"Column9 : " + c9.getHeight() 
		+ "\n" + "Column10 : " + c10.getHeight() + "\n" + "Column11 : " + c11.getHeight() 
		+ "\n" + "Column12 : " + c12.getHeight();
		return text;
	}*/

}
