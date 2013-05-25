
import java.util.ArrayList;

public class Column {
	int num, height;
	boolean conquered;
	ArrayList<GamePiece> pieces;
	
	public static void main (String []args){
		Column c1 = new Column(2,3);
		c1.setHeight(1);
		System.out.println(c1.getHeight()); //set to 1
		c1.setHeight(-50); //error expected
		
		c1.setNum(5);	//Set Column number to 5
		System.out.println(c1.getNum());//return number 5
		
		c1.setNum(-15);//error expected
		
		c1.setNum(99);//error expected
		
		c1.setNum(7); //Set number to 7 
		System.out.println(c1.getNum());// return a 7
		
		c1.setConquered(true);
		System.out.println(c1.getConquered());//set to true
		c1.setConquered(false);
		System.out.println(c1.getConquered());//set to false
	}
	
	public Column(int numIn, int heightIn) {
		num = numIn;
		height = heightIn;
		conquered = false; 
		pieces = new ArrayList<GamePiece>();
	}
	
	public int getHeight (){
		return height;
	}
	
	public int getNum (){
		return num;
	}
	
	public boolean getConquered(){
		return conquered;
	}
	
	public void addPiece(GamePiece gpIn){
		pieces.add(gpIn);
	}
	
	public GamePiece getTempPiece(int player){
		for (int i = 0; i < pieces.size(); i++){
			if ((pieces.get(i).getPlayer() == player) && (!pieces.get(i).isFinal()))
				return pieces.get(i);
		}
		return null;
	}
	
	public GamePiece getFinalPiece(int player){
		for (int i = 0; i < pieces.size(); i++){
			if ((pieces.get(i).getPlayer() == player) && (pieces.get(i).isFinal()))
				return pieces.get(i);
		}
		return null;
	}
	
	public boolean containsTemp(Player p){
		for (int i = 0; i < pieces.size(); i++){
			if ((pieces.get(i).getPlayer() == p.getPlayerNum()) && (!pieces.get(i).isFinal()))
				return true;
		}
		return false;
	}
	
	public GamePiece getTopPiece(){
		GamePiece topPiece = null;
		for (int i =0; i<pieces.size(); i++){
			if (pieces.get(i).getHeight()== height)
				topPiece = pieces.get(i);
		}
		return topPiece;
	}
	
	
	public boolean containsFinal(Player p){
		for (int i = 0; i < pieces.size(); i++){
			if ((pieces.get(i).getPlayer() == p.getPlayerNum()) && (pieces.get(i).isFinal()))
				return true;
		}
		return false;
	}
	
	public void setHeight(int h){
		String error = "You have not entered an acceptable height";
		if ((h >= 3) && (h <= 13))
			height = h;	
		else 
			System.out.println(error);
	}
	
	public void setNum(int n){
		String msg = "You have not entered an acceptable num";
		if (n >=2 && n <= 12){
			num = n; 			  }
		else 						{
			System.out.println(msg); }
	}
	
	// Probably should have a check for possible errors
	public void setConquered(boolean c){
		conquered = c;
	}
	
	

}
