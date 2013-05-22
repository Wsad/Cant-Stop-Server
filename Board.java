
public class Board {
	
	Column c2 = new Column();
	Column c3 = new Column();
	Column c4 = new Column();
	Column c5 = new Column();
	Column c6 = new Column();
	Column c7 = new Column();
	Column c8 = new Column();
	Column c9 = new Column();
	Column c10 = new Column();
	Column c11 = new Column();
	Column c12 = new Column();
	
	public static void main (String []args){
		Board b1 = new Board ();
		System.out.println(b1.toString());
		b1.getList();
	}
	
	public Board(){
		
		c2.setHeight(3);
		c3.setHeight(4);
		c4.setHeight(5);
		c5.setHeight(6);
		c6.setHeight(7);
		c7.setHeight(8);
		c8.setHeight(7);
		c9.setHeight(6);
		c10.setHeight(5);
		c11.setHeight(4);
		c12.setHeight(3);
		
					}
	
	//public boolean updateColumns(){
		
	//	boolean [] col = this.getList(); 
	//	for (int i =0; i< col.length; i++) {
			//if (i == )
			
	//										}
	//								}
	
	
	
	public int[] getList()				{
		String text = " ";
		int [] list = new int[11];
	
		list[0] = c2.getHeight();
		list[1] = c3.getHeight();
		list[2] = c4.getHeight();
		list[3] = c5.getHeight();
		list[4] = c6.getHeight();
		list[5] = c7.getHeight();
		list[6] = c8.getHeight();
		list[7] = c9.getHeight();
		list[8] = c10.getHeight();
		list[9] = c11.getHeight();
		list[10] = c12.getHeight();
	
	for (int i = 0; i<list.length; i++){
		text += " " + list[i];
										}
		System.out.println (text);
		return list;
											}
	
	public String toString(){
		String text = "Column2 : " + c2.getHeight() + "\n" + "Column3 : " + c3.getHeight() 
		+ "\n" + "Column4 : " + c4.getHeight() + "\n" + "Column5 : " + c5.getHeight() 
		+ "\n" + "Column6 : " + c6.getHeight() + "\n" + "Column7 : " + c7.getHeight() 
		+ "\n" + "Column8 : " + c8.getHeight() + "\n" +"Column9 : " + c9.getHeight() 
		+ "\n" + "Column10 : " + c10.getHeight() + "\n" + "Column11 : " + c11.getHeight() 
		+ "\n" + "Column12 : " + c12.getHeight();
		return text;
	}

}
