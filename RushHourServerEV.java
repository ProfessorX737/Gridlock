import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

import java.util.concurrent.BlockingQueue;

/**
 * Event handler class thread for RushHourServer
 *  - handles all incoming and outgoing messages
 *  - manages the state of the RushHourServer
 */

public class RushHourServerEV extends Thread{
    private static BlockingQueue<String> eventQueue = null;
    private static Map<String,ClientInfo> clients = null;
    private static Map<String, Socket> userList = null;

    public RushHourServerEV(BlockingQueue eventQueue,  Map<String,ClientInfo> clients, Map<String, Socket> userList){
        this.eventQueue = eventQueue;
        this.clients = clients;
        this.userList = userList;
    }

    @Override
    public void run(){
        System.out.println("Event handler thread started");
        String currentLine;
        String[] parts;
        while (true){
            try{
                currentLine = eventQueue.take();
                System.out.println("Event Handler: processing " + currentLine);
                // Parse commands
                parts = currentLine.trim().split(" ");

                // Determine command
                switch(parts[0].toLowerCase()){
                    case "getlist":
                        getList(parts[1].toLowerCase());
                        break;

                }

            }catch(InterruptedException e){
                System.out.println("Event handler interrupted");
            }
        }
    }

    private void getList(String username){
        StringBuilder builder = new StringBuilder("userList ");
        for (String names : clients.keySet()){
            builder.append(names + "| ");
        }

        String result = builder.toString();
        System.out.println("Event Handler to <" + username + ">: " + result);

        // Get socket to send to user
        send(username, result);
    }

    private void send(String username, String message){
        Socket receiver = userList.get(username);
        try{
            PrintStream os = new PrintStream(receiver.getOutputStream());
            os.println(message);
        }catch(IOException e){
            System.out.println("Connection to" + username + " created I/O exception");
        }
    }

}
