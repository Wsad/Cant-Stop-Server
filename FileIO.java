import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileIO {
	private final String FILE_NAME;

	private FileOutputStream fOutStream;
	private ObjectOutputStream objectOut;
	private FileInputStream fInputStream;
	private ObjectInputStream objectIn;
	
	public FileIO(String fileNameIn){
		FILE_NAME = fileNameIn;
		try{
			fInputStream = new FileInputStream(new File(FILE_NAME));
			objectIn = new ObjectInputStream(fInputStream);
		}
		catch (IOException e){
			//If .dat file is empty -- initialize it here
			System.out.println(e.getMessage());
			initFile();
			try{
				fInputStream = new FileInputStream(new File(FILE_NAME));
				objectIn = new ObjectInputStream(fInputStream);
			}
			catch (FileNotFoundException f){
				System.out.println(f.getMessage());
			}
			catch (IOException g){
				System.out.println(g.getMessage());
			}
		}
	}
	
	public void initFile(){
		Map<String,PlayerInfo> map = new HashMap<String,PlayerInfo>();
		map.put("guest", new PlayerInfo("guest"));
		writeObject(map);
	}
	
	public Object read(){
		try {
			return objectIn.readObject();
		}
		catch(IOException e){
			System.out.println("Error reading object from file " + e.getMessage());
		}
		catch(ClassNotFoundException e){
			System.out.println("Error reading object from file " + e.getMessage());
		}
		return null;
		

	}
	
	public void resetReader(){
		try{
			objectIn = new ObjectInputStream(fInputStream);
		}
		catch(IOException e){
			System.out.println("Unable to reset object reader: "+ e.getMessage());
		}
	}
	
	
	public void writeObject(Object input){
		try {
			fOutStream = new FileOutputStream(new File(FILE_NAME), false);//check whether this overwrites data or appends.
			objectOut = new ObjectOutputStream(fOutStream);
			objectOut.writeObject(input);
		}
		catch(IOException e){
			System.out.println("Unable to write object to file: "+ e.getMessage());
		}
	}
	
	public void close(){
		try{			
			objectOut.close();
			fOutStream.close();
			objectIn.close();
			fInputStream.close();
		}
		catch(IOException e){
			System.out.println("Error closing FileIO: "+e.getMessage());
		}
		catch(NullPointerException e){
			//System.out.println("Attempted to close uninitialized Output stream: "+e.getMessage());
		}
	}
}
