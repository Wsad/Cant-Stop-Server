/**@author Nigel Gauvin and Alex Ringeri TestColumn: Testing the setHeigh method, the setNumb, the setConquered and the getConquered methods.*/
public class TestColumn {
	
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
}
