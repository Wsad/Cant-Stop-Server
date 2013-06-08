import java.util.Random;
/** @author Nigel Gauvin and Alex Ringeri: Dice Class
 * methods: roll, split and getDice */
 

public class Dice {
	private static int d1,d2,d3,d4;
	private static Random dice1=new Random();
	
	/** Method: Roll @param: void @return String containing 4 dice integers delimited 	*   by a comma */
	public static String roll(){
		d1 = dice1.nextInt(6)+1;
		d2 = dice1.nextInt(6)+1;
		d3 = dice1.nextInt(6)+1;
		d4 = dice1.nextInt(6)+1;
			    
	    String text = d1 + "," + d2 + "," + d3 + "," + d4;  
	    return text;
	}
	
	
		/** Method: Split Adds all the possibilities for a dice to be split 			*   @param: void @return: void */
	public static String split(){
			
		int split1 = d1 + d4; 
		int halfsplit1 = d2 + d3;
			 
		int split2 = d1 + d3;
		int halfsplit2 = d2 + d4;
			 
		int split3 = d1 + d2;
		int halfsplit3 = d3 + d4;
		
			 
		return "(" + split1 + "," + halfsplit1 + ")(" + split2 + "," + halfsplit2 + ")(" + split3 + "," + halfsplit3 
					+ ")(" + halfsplit1 + "," + split1 +")("+halfsplit2 +"," + split2+")("+halfsplit3 +","+split3+")";
		
	}
		
	/** Method getDice to retrieve the Dice object that holds 4 dice integers
	*	@param: Void 
	*	@return: String text	-representing four dice integers */
	public String getDice(){
			String text = " " + d1 + " " + d2 + " " + d3 + " " + d4;  
			return text;
	}
}
