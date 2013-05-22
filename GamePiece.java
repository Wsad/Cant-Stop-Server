
public class GamePiece {
	
	int height;
	Column col; 
	
	
	public GamePiece(){
		height = 0;
		col = new Column ();
					  }
	
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
	
	public void setColumn(Column c){
		col = c;
	}
	
	public Column getColumn(){
		return col;
	}
	
	public int getColNum(){
		return col.getNum();
	}
	
	
	

}
