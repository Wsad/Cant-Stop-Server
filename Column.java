
public class Column {
	int num, height;
	boolean conquered;
	
	public static void main (String []args){
		Column c1 = new Column();
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
	
	public void setHeight(int h){
		String error = "You have not entered an acceptable height";
		if (h >= 0){
			height = h;	}
		else 
			System.out.println(error);
	}
	
	public void setNum(int n){
		String msg = "You have not entered an acceptable num";
		if (n >=0 && n <= 12){
			num = n; 			  }
		else 						{
			System.out.println(msg); }
	}
	
	// Probably should have a check for possible errors
	public void setConquered(boolean c){
		conquered = c;
	}
	
	

}
