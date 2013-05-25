import java.util.Random;
/** @author Nigel Gauvin and Alex Ringeri: Dice Class
 * methods: roll, split and getDice */
 

public class Dice {
	private int d1,d2,d3,d4;
	private Random dice1;
	
	public static void main (String[]args){
		
		Dice d1 = new Dice ();//**Multiple calls (6) to prove dice roll is random and that the split method works**//
		d1.roll();
		d1.split();
		System.out.println(d1.getDice());
		d1.roll();
		d1.split();
		System.out.println(d1.getDice());
		d1.roll();
		d1.split();
		System.out.println(d1.getDice());
		d1.roll();
		d1.split();
		System.out.println(d1.getDice());
		d1.roll();
		d1.split();
		System.out.println(d1.getDice());
		d1.roll();
		d1.split();
		System.out.println(d1.getDice());
		
	}
	//** Constructor: Initializes 4 dice integers
	public Dice () {
		
			d1 = 0;
			d2 = 0;
			d3 = 0;
			d4 = 0;
			dice1 = new Random();	

		
	}
	
	//** Method: Roll @param: void @return String containing 4 dice integers delimited by a comma*/
	public String roll(){
		//Mark the range
		int START = 1;
		int END = 6;
		
		//** Create a range for the random numbers
		int range = (int)END - (int)START + 1;
		
	    int fraction1 = (int)(range * dice1.nextDouble());
	    d1 =  (fraction1 + START);
	    int fraction2 = (int)(range * dice1.nextDouble());
	    d2 =  (fraction2 + START);
	    int fraction3 = (int)(range * dice1.nextDouble());
	    d3 =  (fraction3 + START);
	    int fraction4 = (int)(range * dice1.nextDouble());
	    d4 =  (fraction4 + START);
	    
	    String text = " " + d1 + "," + d2 + "," + d3 + "," + d4;  
	    return text;
		}
	
	
		//**Method: Split Adds all the possibilities for a dice to be split @param: void @return: void*/
		public void split(){
			
		int split1 = d1 + d4; 
		int halfsplit1 = d2 + d3;
			 
		int split2 = d1 + d3;
		int halfsplit2 = d2 + d4;
			 
		int split3 = d1 + d2;
		int halfsplit3 = d3 + d4;
		
			 
		System.out.println("(" + split1 + "," + halfsplit1 + ")" 
		+ " (" + split2 + "," + halfsplit2 + ")" 
		+ " (" + split3 + "," + halfsplit3 + ")");
		
		}
		
		//** Method getDice @param: Void @return: String containing dice integers 
		public String getDice(){
			String text = " " + d1 + " " + d2 + " " + d3 + " " + d4;  
			return text;
		}
	
	

}
