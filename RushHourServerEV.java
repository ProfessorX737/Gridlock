import com.sun.security.ntlm.Client;

import java.awt.*;
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

                    case "challenge":
                        challenge(parts[1],parts[2]);
                        break;

                    case "accepted":
                        acceptChallenge(parts[1],parts[2]);
                        break;

                    case "done":
                        puzzleComplete(parts[1],parts[2]);
                        break;

                    case "offline":
                        offlineUser(parts[1]);
                        break;

                    default:
                        break;
                }

            }catch(InterruptedException e){
                System.out.println("Event handler interrupted");
            }
        }
    }

    private void getList(String username){
        StringBuilder builder = new StringBuilder("userList ");
        for (String names : userList.keySet()){
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

    private void challenge(String user1, String user2){

        // Check if user exists

        System.out.println("Challenge to " + user2 + "from " + user1);
        if (clients.containsKey(user2)){
            System.out.println("user2 exists");
            ClientInfo otherUser = clients.get("user2");
            if (!otherUser.isBusy()){
                send(user1, "challenging " + user2);
                send(user2, "challenged " + user2 + " from " + user1);
            }
            else{
                send(user1, "userbusy " + user2 +" is currently busy");
            }
        }

        else{
            send(user1, "nouser " + user2 +" does not exist");
        }

    }

    // User 1 accepted user 2
    private void acceptChallenge(String user1, String user2){
        ClientInfo usrInfo1 = clients.get(user1);
        ClientInfo usrInfo2 = clients.get(user2);

        usrInfo1.setBusy(true);
        usrInfo2.setBusy(true);
        usrInfo1.setPlayingAgainst(user2);
        usrInfo2.setPlayingAgainst(user1);

        send(user2, "accepted " + user2 + " " + user1);
        sendPuzzle(user1, user2);
    }

    private void sendPuzzle(String user1, String user2){
        PuzzleGame newPuzzle = createPuzzle();
        // Convert puzzle to String Format and send to both users
        String puzzle = newPuzzle.getStringRep();
        System.out.println("Puzzle output: " + puzzle);

        send(user1, "puzzle " + puzzle);
        send(user2, "puzzle " + puzzle);
    }

    // Hard coded for now
    private PuzzleGame createPuzzle(){
        PuzzleGame puzzleGame = new PuzzleGame(6,6,2,5);
        puzzleGame.addVehicle(false, 2, 2, 2, Color.RED);
        puzzleGame.addVehicle(false, 3, 0, 0, Color.ORANGE);
        puzzleGame.addVehicle(true, 2, 0, 3, Color.ORANGE);
        puzzleGame.addVehicle(true, 3, 0, 4, Color.ORANGE);
        puzzleGame.addVehicle(true, 3, 0, 5, Color.ORANGE);
        puzzleGame.addVehicle(true, 2, 1, 0, Color.ORANGE);
        puzzleGame.addVehicle(false, 2, 1, 1, Color.ORANGE);
        puzzleGame.addVehicle(false, 2, 3, 0, Color.ORANGE);
        puzzleGame.addVehicle(true, 2, 3, 2, Color.ORANGE);
        puzzleGame.addVehicle(true, 2, 4, 1, Color.ORANGE);
        puzzleGame.addVehicle(false, 2, 5, 2, Color.ORANGE);
        puzzleGame.addVehicle(false, 2, 4, 4, Color.ORANGE);
        puzzleGame.addVehicle(false, 2, 5, 4, Color.ORANGE);
        return puzzleGame;
    }

    // User1 finished puzzle
    private void puzzleComplete(String user1, String user2){
        send(user2, "puzzledone by " + user1);

        // Update Client Info
        ClientInfo usrInfo1 = clients.get(user1);
        ClientInfo usrInfo2 = clients.get(user2);

        usrInfo1.setBusy(false);
        usrInfo2.setBusy(false);
        usrInfo1.setPlayingAgainst(null);
        usrInfo2.setPlayingAgainst(null);

    }

    // Remove online mapping, set user offline
    private void offlineUser(String user){
        userList.remove(user);
        ClientInfo info = clients.get(user);

        // Notify other user if they were ingame that other user disconnected, make them the winner
        String opponent = info.getPlayingAgainst();
        if (opponent != null){
            send(info.getPlayingAgainst(), "DC" + user + " has disconnected");
            ClientInfo otherUser = clients.get(opponent);
            info.resetPlayingAgainst();
            info.setBusy(false);
        }

        info.setOnline(false);
        info.resetPlayingAgainst();
        info.setBusy(false);



    }
}
