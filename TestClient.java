import java.io.*;
import java.net.*;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String host = args[0];
	    int port = Integer.parseInt(args[1]);
	    Socket s = null;
	    try {
	    	s = new Socket(host, port);
	    }
	    catch (UnknownHostException e) {
	    	System.err.println("Unknown host: " + host);
	         System.exit(-1);
	      }
	      catch (IOException e) {
	         System.err.println("Unable to get I/O connection to: "
	                           + host + " on port: " + port);
	         System.exit(-1);
	      }
	      BufferedReader serverIn = null;
	      BufferedReader userIn = null;
	      PrintWriter serverOut = null;
	      try {         
	         serverIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
	         userIn = new BufferedReader(new InputStreamReader(System.in));
	         serverOut = new PrintWriter(s.getOutputStream(), true /* autoFlush */);

	         System.out.println(serverIn.readLine());

	         boolean done = false;
	         while (!done) {
	        	System.out.println(serverIn.readLine());
	            String line = userIn.readLine();
	            if (line.equals("BYE")) { done = true; }
	            else if (!line.equals("none"))
	            		serverOut.println(line);
	         }//while
	      }
	      catch (IOException e) {
	         System.out.println("Problem reading or writing:" + e.getMessage());
	      }

	      try {
	         userIn.close();
	         serverOut.close();
	         serverIn.close();
	         s.close();
	      }
	      catch (IOException e) {
	         System.out.println("Problem closing reader, writer, or socket:" 
	                            + e.getMessage());
	      }
	}	

}
