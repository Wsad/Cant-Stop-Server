import java.util.Scanner;

public class testvalidsplit {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		
		Dice.roll();
		String split = Dice.split();
		System.out.println(split);
		System.out.println("enter substring");
		String sub = scan.nextLine();
	
		if (split.contains(sub))
			System.out.println("true");
		else
			System.out.println("false");
	}
}
