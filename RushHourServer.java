package cs2511;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class RushHourServer {
	private ServerSocket ss = null;
	private int port;
	BlockingQueue eventQueue = null;
	Set<ClientInfo> clients = null;
	Map<String, PrintStream> outputUser = null;
	
	public static void main(String[] args) {
		RushHourServer server = new RushHourServer(55555);
		server.run();
	}
	
	public RushHourServer(int port) {
		this.port = port;
	}

	// In charge of handling new connections and creating threads
	public void run() {
		
		try {
			System.out.println("Starting server on port " + port);
			ss = new ServerSocket(port);
			
			// Start an event handling thread
			
			
			
			while(true) {

				// When a new connection established, handle it
				Socket newClient = ss.accept();
				DataInputStream is = new DataInputStream(newClient.getInputStream());
				PrintStream os = new PrintStream(newClient.getOutputStream());
				
				// Get handshake message to identify user
				// first message will be new username in format
				System.out.println("New connection established" + newClient);
				while (true) {
					String line;
					try {
						line = is.readLine();
						System.out.println(line);
					} catch (IOException e1) {
						// Connection probably broke?
						e1.printStackTrace();
						System.out.println("Connection broke");
						break;
					}
				}
//				String line = is.readLine();
				
			
				newClient.close();

			}
			
			
		}catch(IOException e) {
			System.out.println("I/O Exception: Issue starting up server");
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public void eventHandler() {
		// TODO 
	}
	
	// TODO implement threads in java, limit concurrent threads to 8, thread pool?
	private class clientThread extends Thread{
			private ClientInfo info = null;
			private DataInputStream is = null;
			private BlockingQueue<String> eventQueue = null;
			
			public clientThread(ClientInfo info, DataInputStream is, BlockingQueue<String> eventQueue) {
				this.info = info;
				this.is = is;
				this.eventQueue = eventQueue;
			};
			
			
			public void run() {
			
				// Add messages to event queue

				// Keep processing messages sent from client
				while (true) {
					String line;
					try {
						line = is.readLine();
					} catch (IOException e1) {
						// Connection probably broke?
						e1.printStackTrace();
						System.out.println("Connection broke");
						break;
					}
					
					if (line != null) {
						try {
							// Add messages to the queue
							// If blocking queue is full wait 200ms before trying again
							while(!eventQueue.offer(line)) {
								Thread.sleep(200);
							}
							
						} catch(Exception e){
							System.out.println(e);
						}
					}
					else {
						System.out.println("Null result found");
					}
					
				}
				
				// Connection closed, remove hashmap mapping
				
			}
		}
	
	
}
