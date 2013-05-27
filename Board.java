import java.util.ArrayList;

public class Board {
	
	Column[] columns = new Column[11];

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
	
	public ArrayList<GamePiece> getFinalPieces(Player p){
		ArrayList<GamePiece> retPieces = new ArrayList<GamePiece>();
		System.out.println("Final pieces arr: \n");//debug
		for (int i=0; i<columns.length; i++){
			GamePiece temp = columns[i].getFinalPiece(p.getPlayerNum());
			if (temp != null){
				retPieces.add(temp);
				System.out.println("col"+(i+2));
			}
		}
		
		for (int i=0; i<retPieces.size();i++)
			System.out.println(i+ " Height" + retPieces.get(i).getHeight());
		return retPieces;
	}
		
	public ArrayList<GamePiece> getTempPieces(Player p){
		ArrayList<GamePiece> retPieces = new ArrayList<GamePiece>();
		System.out.println("Temp pieces arr: \n");//debug
		for (int i=0; i<columns.length; i++){
			GamePiece temp = columns[i].getTempPiece(p.getPlayerNum());
			if (temp != null){
				retPieces.add(temp);
				System.out.println("col"+(i+2));
			}
		}
		
		for (int i=0; i<retPieces.size();i++)
			System.out.println(i+ " Height" + retPieces.get(i).getHeight());
		return retPieces;
	}
	
	public void clearTemp(Player p){
		for (int i=0; i<columns.length; i++){
			GamePiece piece = columns[i].getTempPiece(p.getPlayerNum());
			if (piece != null)
				piece.setFinal(false);
		}
	}
}
