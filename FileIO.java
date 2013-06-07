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
			fOutStream = new FileOutputStream(new File(FILE_NAME));//check whether this overwrites data or appends.
			objectOut = new ObjectOutputStream(fOutStream);
			Map<String,PlayerInfo> map = new HashMap<String,PlayerInfo>();
			map.put("john", new PlayerInfo("password"));
			
			objectOut.writeObject(map);
			
			fInputStream = new FileInputStream(new File(FILE_NAME));
			objectIn = new ObjectInputStream(fInputStream);
		}
		catch (IOException e){
			System.err.println("Error creating Object reader or writer: " +e.getMessage());
		}
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
	}
}
