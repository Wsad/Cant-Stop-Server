/** @author Alex Ringeri
	A connection class to represent the connection between a client and the server.
*/

import java.io.*;
import java.net.*;

public class Connection{
	/** The Socket object through which the player will communicate with the server. */
	private Socket socket;
	/** The BufferedReader which will be used to read Strings sent from the client. */
	private BufferedReader reader;
	/** The PrintWriter which will be used to send Strings from the server to each player or client.*/
	private PrintWriter writer;
	
	/**	Constructor: Creates a Connection object. Requires a Socket which has already been connected to the client.
	@param s A Socket object which has been initialized and connected to client outside of this class. */
	public Connection(Socket s){
		socket = s;
		try {
			reader = new BufferedReader(new	InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch {
			System.err.println("Unable to create 'reader' and 'writer' for client");
		}
	}
	
	/** This method will read the next line sent from the client. 
	@return The method will return a String containing a line of input from the client */
	public String read(){
		try{
			return reader.readLine();
		} catch (IOException e){
			System.err.println("Unable to read from client: " + e.getMessage());
		}
	}
	
	/** This method will print the String 'out' to the output stream. This will send information from the server to the client. 
	@param out The String which is to be sent to the client. */
	public void print(String out){
		try{
			writer.println(out);
		} catch (IOException e){
			System.err.println("Unbable to print to client: " + e.getMessage());
		}
	}
	
	/** Closes the connection for the socket, reader and writer */
	public void close(){
		writer.close();
		reader.close();
		socket.close();
	}
}