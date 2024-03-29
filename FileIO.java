/**@author Alex Ringeri File IO to read to and from a textfile storing the player information (username, password, wins, losses, points) */

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;

public class FileIO {
	private final String FILE_NAME;

	private FileOutputStream fOutStream;
	private ObjectOutputStream objectOut;
	private FileInputStream fInputStream;
	private ObjectInputStream objectIn;
	
	/**Constructor FileIO 
	 * @param String fileNameIn 		-name of the file
	 * @return void					
	 * @throws IOException 				-if the file is empty
	 * @throws FileNotFoundException 	-if the file cannot be found
	 * @throws IOException				-if it cannot write to the file */
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
	
	/** Method initFile for storing player information
	 * @param void
	 * @return void */
	public void initFile(){
		Map<String,PlayerInfo> map = new HashMap<String,PlayerInfo>();
		map.put("guest", new PlayerInfo("guest","guest"));
		writeObject(map);
	}
	
	/** Method read for reading from the file
	 * @param void
	 * @return Object null
	 * @throws IOException				-Error reading from the file
	 * @throws ClassNotFoundException */
	public Object read(){
		try {
			return objectIn.readObject();
		}
		catch(IOException e){
			System.out.println("Error reading object from file " + e.getMessage());
			initFile();
			System.out.println("File reset");
		}
		catch(ClassNotFoundException e){
			System.out.println("Error reading object from file " + e.getMessage());
		}
		return null;
		

	}
	
	/** Method resetReader to reset the input stream
	 * @param void
	 * @return void
	 * @throws IOException	-if the reader is unable to be reset */
	public void resetReader(){
		try{
			objectIn = new ObjectInputStream(fInputStream);
		}
		catch(IOException e){
			System.out.println("Unable to reset object reader: "+ e.getMessage());
		}
	}
	
	/** Method writeObject for writing to the file
	 * @param Object input 
	 * @return void
	 * @throws IOException 	-when the OutputStream is unable to write to the file  */
	
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
	
	public String getTopPlayers(){
		Map<String,PlayerInfo> map = new HashMap<String,PlayerInfo>();
		map = (HashMap)read();
		int size = map.size();
		PlayerInfo[] pInfo = map.values().toArray(new PlayerInfo[map.size()]);
		PlayerInfo max = pInfo[0];
		for (int i=1; i<pInfo.length; i++){
			PlayerInfo p = pInfo[i];
			if (p.getPoints()>max.getPoints()){
				max = p;
			}
		}
		PlayerInfo max2 = pInfo[0];
		for (int i=1; i<pInfo.length; i++){
			PlayerInfo p = pInfo[i];
			if (p.getPoints()>max2.getPoints()){
				if (p != max)
					max2=p;
			}
		}
		PlayerInfo max3 = pInfo[0];
		for (int i=1; i<pInfo.length; i++){
			PlayerInfo p = pInfo[i];
			if (p.getPoints()>max3.getPoints()){
				if (p != max2 && p !=max)
					max3=p;
			}
		}
		return max.toString()+","+max2.toString()+","+max3.toString();
	}
	
	/** Method close for closing the stream
	 * @param void
	 * @return void
	 * @throws IOException 	-Error closing the file */
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
	
	public static void main(String [] args){
		FileIO fi = new FileIO("userInfo.dat");
		System.out.println(fi.getTopPlayers());
	}
}
