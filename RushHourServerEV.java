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
                parts = currentLine.toLowerCase().trim().split(" ");

                // Determine command
                switch(parts[0]){
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

                    case "decline":
                        declineUser(parts[1],parts[2]);
                        break;

                    case "forfeit":
                        forfeit(parts[1],parts[2]);
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
            builder.append(names + " ");
        }
        sendStats(username);
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
            System.out.println(user2 + " exists");
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

        if (usrInfo1 == null || usrInfo2 == null){
            send(user1, user2 + " does not exist");
            return;
        }

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

        send(user1, "setopp " + user2);
        send(user2, "setopp " + user1);

        send(user1, "puzzle " + puzzle);
        send(user2, "puzzle " + puzzle);
    }

    // Hard coded for now
    private PuzzleGame createPuzzle(){
        GridlockGame game = new GridlockGame();
        PuzzleGame puzzleGame = game.getPuzzle(GridlockGame.VERY_EASY, 1);
        puzzleGame.initState();
        return puzzleGame;
    }

    // User1 finished puzzle
    private void puzzleComplete(String user1, String user2){
        send(user2, "puzzledone by " + user1);

        // Update Client Info
        ClientInfo usrInfo1 = clients.get(user1);
        ClientInfo usrInfo2 = clients.get(user2);

        usrInfo1.incrementWins();
        usrInfo2.incrementLosses();

        usrInfo1.setBusy(false);
        usrInfo2.setBusy(false);
        usrInfo1.setPlayingAgainst(null);
        usrInfo2.setPlayingAgainst(null);
        sendStats(user1);
        sendStats(user2);
    }

    // forfeit
    private void forfeit(String user1, String user2){
        ClientInfo info = clients.get(user1);
        // Check if actually in-game
        if (info.isBusy()){
            info.setBusy(false);
            String oppo = info.getPlayingAgainst();
            info.resetPlayingAgainst();
            // Increase loss count
            info.incrementLosses();
            sendStats(user1);

            if (oppo != null){
                send(user2, "forfeit " + user1);
                ClientInfo OppoInfo = clients.get(user2);
                OppoInfo.setBusy(false);
                OppoInfo.resetPlayingAgainst();
                OppoInfo.incrementWins();
                // Increase win count
                sendStats(user2);
            }


        }
    }

    // Remove online mapping, set user offline
    // TODO update win and loss
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

    private void declineUser(String user1, String user2){
        // Notify other user he has been declined
        send(user2,"declined " + user1);
    }

    private void sendStats(String user1){
        ClientInfo info = clients.get(user1);
        int losses = info.getLosses();
        int wins = info.getWins();

        send(user1, "stats " + Integer.toString(wins) + " " + Integer.toString(losses) );

    }
}
