import java.io.*;
import java.util.Scanner;

public class FileIO {
	private final String FILE_NAME;
	
	private Scanner SCAN;

	private FileWriter fw;
	private BufferedWriter bw;
	private PrintWriter writer;
	
	
	//file will be organized "username;password;score;\n"
	public FileIO(String fileNameIn){
		FILE_NAME = fileNameIn;
		try{
			SCAN = new Scanner(new File(FILE_NAME));
			fw = new FileWriter(FILE_NAME, true);
			bw = new BufferedWriter(fw);
			writer = new PrintWriter(bw);
		}
		catch (IOException e){
			System.err.println("Error creating file IO: " +e.getMessage());
		}
	}
	
	public String readLine(){
		String ret = "";
		if (SCAN.hasNext())
			ret = SCAN.nextLine();
		return ret;
	}
	
	public void resetReader(){
		try{
			SCAN = new Scanner(new File(FILE_NAME));
		}
		catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void println(String input){
		writer.println(input);
	}
	
	public boolean hasNext(){
		return SCAN.hasNextLine();
	}
	
	public void close(){
		try{			
			writer.close();
			bw.close();
			fw.close();
		}
		catch(IOException e){
			System.out.println("Error closing FileIO: "+e.getMessage());
		}
	}
}
