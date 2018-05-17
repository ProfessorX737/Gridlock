
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class RushHourServer {
	private static ServerSocket ss;
	private static int port;
	private static BlockingQueue<String> eventQueue;
	private static Map<String,ClientInfo> clients;
	private static Map<String, Socket> userList;
	private static RushHourServerEV evHandler;
	
	public static void main(String[] args) {
		RushHourServer server = new RushHourServer(55555);
		server.run();
	}

	public RushHourServer(int port) {
		this.port = port;
		this.userList = new ConcurrentHashMap<String, Socket>();
		this.clients = new ConcurrentHashMap<String, ClientInfo>();
		this.eventQueue = new LinkedBlockingQueue<String>();
	}

	// In charge of handling new connections and creating threads
	public void run() {
		String line;
		String username;

		try {
			System.out.println("Starting server on port " + port);
			ss = new ServerSocket(port);

			// Start Event Handler Thread
			// RushHourServerEV(BlockingQueue eventQueue,  Map<String,ClientInfo> clients, Map<String, Socket> userList)
			evHandler = new RushHourServerEV(eventQueue, clients, userList);
			evHandler.start();


			while(true) {

				// When a new connection established, handle it
				Socket newClient = ss.accept();
				DataInputStream is = new DataInputStream(newClient.getInputStream());
				PrintStream os = new PrintStream(newClient.getOutputStream());
				
				// Get handshake message to identify user
				// first message will be new username in format
				System.out.println("New connection established" + newClient);
				// command should be user "username", the first word is chosen, rest is ignored
				System.out.println("Getting username from user");

				// Test code for now

				try {
					line = is.readLine();
					System.out.println("From Host:" + line);
					String[] parts = (line.trim()).split(" ");

					// TODO: check for duplicate usernames? if duplicate close connection, give them notification that username currently in use
					if (parts[0].toLowerCase().equals("user")){
						username = parts[1];
						userList.put(username,newClient);

						// Start new ConnectionHandler object
						ClientInfo newInfo = new ClientInfo(username);
						clients.put(username, newInfo);
						ConnectionHandler newConn = new ConnectionHandler(newInfo, eventQueue, newClient);

						// Should limit how many threads, need one for each active connection right now!
						System.out.println("Thread created and started for " + username);
						newConn.start();

					}
					else{
						// Invalid first message
						os.println("Invalid command: user <username> required");
						newClient.close();
					}

				} catch (IOException e1) {
					// Connection probably broke?
					e1.printStackTrace();
					System.out.println("Connection broke");
					break;
				}

////				String line = is.readLine();
//				newClient.close();

			}
			
			
		}catch(IOException e) {
			System.out.println("I/O Exception: Issue starting up server");
			e.printStackTrace();
			System.out.println(e);

		}catch(Exception e){
			System.out.println("A Non I/O related issue occurred");
			e.printStackTrace();
			System.out.println(e);
		}
	}

}
