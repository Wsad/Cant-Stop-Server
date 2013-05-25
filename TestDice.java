/**@author Nigel Gauvin and Alex Ringeri Test Class for the Dice Object: tests the roll method, the split and the get dice. 
Multiple calls to test the unique rolls and the split method*/
public class TestDice {

public static void main (String[]args){
		
		Dice d1 = new Dice ();//**Multiple calls (6) to prove dice roll is random and that the split method works**//
		System.out.println(d1.roll());
		d1.split();
		System.out.println(d1.getDice());
		
		System.out.println(d1.roll());
		d1.split();
		System.out.println(d1.getDice());
		
		System.out.println(d1.roll());
		d1.split();
		System.out.println(d1.getDice());
		
		System.out.println(d1.roll());
		d1.split();
		System.out.println(d1.getDice());
		
		System.out.println(d1.roll());
		d1.split();
		System.out.println(d1.getDice());
		
		System.out.println(d1.roll());
		d1.split();
		System.out.println(d1.getDice());
		
	}
}
