import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Network Controller
 * handles all the events and manages the data of game state
 */

public class NetUIController implements NetworkUIController, Runnable{

    private static NetworkPanel np;
    private static String username;
    private boolean running;


    // Networking
    private static String host;
    private static int portNumber;
    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;

    private static BufferedReader inputLine = null;
    private static boolean closed = false;

    public NetUIController(NetworkPanel np, String host, int portNumber){
        this.np = np;
        this.portNumber = portNumber;
        this.host = host;
        this.running = false;
    }

    public ActionListener getChallengeBtnListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                message("challenge " + username + " " + np.getOpponentName());
                createDialogBox("You have challenged" + np.getOpponentName());
            }
        };
        return al;
    };

    /** Sets the username of the networked game
     *  Remove all white space and only get the first word
      */

    public ActionListener getSetUserBtnListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                username = np.getUsername().trim();
                String[] parts = username.split(" ");
                username = parts[0];
            }
        };
        return al;
    };

    public ActionListener getUpdateBtnListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                getList();
            }
        };
        return al;
    };

    // See if username is set, then attempt to connect
    public ActionListener ConnectBtnListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (running) return;
                // If username set
                if (username != null){
                    // Attempt connection

                    try {
                        clientSocket = new Socket(host, portNumber);
                        inputLine = new BufferedReader(new InputStreamReader(System.in));
                        os = new PrintStream(clientSocket.getOutputStream());
                        is = new DataInputStream(clientSocket.getInputStream());
                        if (sendUsername()){
                            System.out.println("Connection established");
                            running = true;
                            np.setUsername(username);
                            new Thread(new NetUIController(np, host, portNumber)).start();
                        }
                        else{
                            return;
                        }

                    }
                    catch(UnknownHostException e){
                        System.err.println("Don't know about host " + host);
                        return;
                    }

                    catch(IOException e) {
                        System.err.println("Couldn't get I/O for the connection to the host "
                                + host);
                        return;
                    }

                }
            }
        };
        return al;
    };

    public ActionListener QuitBtnListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

            }
        };
        return al;
    };

    public ActionListener ForfeitListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

            }
        };
        return al;
    }

    private boolean sendUsername(){
        String newLine = "user " + username;
        os.println(newLine);
        os.flush();

        try {
            String responseLine = is.readLine();
            System.out.println("From server: " + responseLine);
            System.out.println("Why no print?");
            if (responseLine.indexOf("usertaken") != -1){
                return false;
            }
            else if (responseLine.indexOf("closed") != -1){
                return false;
            }

        } catch (IOException e) {
            System.err.println("IOException:  " + e);
            return false;
        }

        return true;
    }

    private void getList(){
            os.println("getlist " + username);
            os.flush();

    }

    private void updateList(String users){
//        createDialogBox("You got the list");
        // remove userList
        System.out.println("In updateList +" + users);
        String[] parts = users.toLowerCase().split(" ");
        List<String> newList = new ArrayList<>();
        for (String s: parts){
            if (s.equals(username.toLowerCase()) || s.equals("userlist")) {
                continue;
            }
            else{
                newList.add(s);
            }
        }
        String[] arr = newList.toArray(new String[newList.size()]);
        np.setUserListBox(arr);

    }

    // Separate which handles the events that the server sends to client
    // Runs on a separate thread which operates on the same class
    public void run() {
        System.out.println("Listener activated");
        String responseLine;
        try {
            while ((responseLine = is.readLine()) != null) {
                System.out.println("From server: " + responseLine);
                processMessage(responseLine);;
//                if (responseLine.indexOf("drop") != -1)
//                    break;
            }
            // Connection broke
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
        System.out.println("Listener done");

    }

    private void processMessage(String message){
        String[] parts = message.trim().split(" ");
        switch(parts[0].toLowerCase()){
            case "userlist":
                updateList(message);
                break;

            case "accepted":
                acceptChallenge(message);
                break;

            case "nouser":
                noUser(message);
                break;

            case "challenged":
                handleChallenge(message);

            default:
                break;
        }
    }

    private void message(String message){
        os.println(message);
        os.flush();
    }

    private void acceptChallenge(String message){
        // Notify challenge has been accepted
        String[] oppo = message.trim().split(" ");
        createDialogBox("Your challenge with " + oppo[2]);
        // Update playing against

    }

    private void noUser(String message){
        // Notify person no user exists for that
    }

    private void userBusy(String message){
        // Notify person user currently busy
        // userbusy user2 currently busy
    }

    private void puzzleDone(){
        // Notify user, puzzle completed by other person
        // puzzledone by user1
    }

    private void handleChallenge(String message){
        // Pop up window, see if message is accepted or declined
        // User can wish to accept or decline the challenge
    }

    private void generatePuzzle(String message){
        // Create the puzzle
    }

    private void createDialogBox(String message){
        JOptionPane.showMessageDialog(null, message);
    }
}
