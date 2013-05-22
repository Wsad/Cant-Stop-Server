import java.util.Random;

public class Dice {
	int d1,d2,d3,d4;
	
	public static void main (String[]args){
		
		Dice d1 = new Dice ();
		d1.roll();
		d1.split();
		System.out.println(d1.getDice());
											}
	
	public Dice () {
		
			d1 = 0;
			d2 = 0;
			d3 = 0;
			d4 = 0;
		
					}
	
	public void roll (){
		//**Create a random object to act as a dice
		Random dice1 = new Random();	

		//Mark the range
		int START = 1;
		int END = 6;
		
		//** Create a range for the random numbers
		long range = (long)END - (long)START + 1;
		
		 // compute a fraction of the range, 0 <= frac < range
	    long fraction1 = (long)(range * dice1.nextDouble());
	    d1 =  (int)(fraction1 + START);
	    long fraction2 = (long)(range * dice1.nextDouble());
	    d2 =  (int)(fraction2 + START);
	    long fraction3 = (long)(range * dice1.nextDouble());
	    d3 =  (int)(fraction3 + START);
	    long fraction4 = (long)(range * dice1.nextDouble());
	    d4 =  (int)(fraction4 + START);
	
						}
	
		public void split(){
			
		int split1 = d1 + d4; 
		int halfsplit1 = d2 + d3;
			 
		int split2 = d1 + d3;
		int halfsplit2 = d2 + d4;
			 
		int split3 = d1 + d2;
		int halfsplit3 = d3 + d4;
			 
		System.out.println("(" + split1 + "," + halfsplit1 + ")" + " (" + split2 + "," + halfsplit2 + ")" + " (" + split3 + "," + halfsplit3 + ")");
			
							}
		
		public String getDice(){
			String text = " " + d1 + " " + d2 + " " + d3 + " " + d4;  
			return text;
				
								}
	
	

}
