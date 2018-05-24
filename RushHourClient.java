import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class RushHourClient implements Runnable{
	private static Socket clientSocket = null;
	private static PrintStream os = null;
	private static DataInputStream is = null;
	
	private static BufferedReader inputLine = null;
	private static boolean closed = false;
	private static String username = null;
	
	public static void main(String[] args) {
		int portNumber = 55555;
		String host = "localhost";
		
		// Open a socket with portNumber and given host
		// Open input and output streams
		
		try {
			clientSocket = new Socket(host, portNumber);
			inputLine = new BufferedReader(new InputStreamReader(System.in));
			os = new PrintStream(clientSocket.getOutputStream());
			is = new DataInputStream(clientSocket.getInputStream());
		}
		catch(UnknownHostException e){
			System.err.println("Don't know about host " + host);}
		catch(IOException e) {
			System.err.println("Couldn't get I/O for the connection to the host "
			          + host);
		}
		System.out.println(clientSocket);
		System.out.println(os);
		System.out.println(is);
		// Get input
		if (clientSocket != null && os!= null && is!=null) {
			try {
				new Thread(new RushHourClient()).start();
				System.out.println("Connection successful, please input data on the next line");
		        while (!closed) {
		        	String newLine = inputLine.readLine().trim();
		        	if (newLine.equals( "close")){
		        		closed = true;
					}
		        	System.out.println("Sent: " + newLine);
		          os.println(newLine);
		          os.flush();
		        }
				/* Create a thread to read from the server. */
		        
		        
		        /*
		         * Close the output stream, close the input stream, close the socket.
		         */
		        os.close();
		        is.close();
		        clientSocket.close();
		      } catch (IOException e) {
		        System.err.println("IOException:  " + e);
		      }
		}
		
		
		
	}
	
	// Separate thread to print out server replies	
	public void run() {
		String responseLine;
		try {
		      while ((responseLine = is.readLine()) != null) {
		        System.out.println("From server: " + responseLine);
		        if (responseLine.indexOf("drop") != -1)
		          break;
		      }
		      closed = true;
		} catch (IOException e) {
		      System.err.println("IOException:  " + e);
		}
		
	}

}
