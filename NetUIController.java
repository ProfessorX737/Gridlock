import javax.swing.*;
import java.awt.*;
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
    private boolean lost;
    private String opponent;


    // Networking
    private static String host;
    private static int portNumber;
    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;

    private static BufferedReader inputLine = null;
    private static boolean closed = false;

    // Game frame
    private JFrame f;
    private PuzzleGame puzzleGame;
    private PuzzleView pv;
    private PuzzleController pc;

    private ButtonPanel bp;
    private SideButtonController bc;

    private GameView gameView;
    private GameController gameController;

    public NetUIController(NetworkPanel np, String host, int portNumber){
        this.np = np;
        this.portNumber = portNumber;
        this.host = host;
        this.running = false;
    }

    public ActionListener getChallengeBtnListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.out.println(" THIS IS OPPONENT's NAME" + np.getOpponentName());
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
                            return;
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
                System.exit(0);
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
            if (responseLine.indexOf("usertaken") != -1){
                createDialogBox("Username is currently online");
                return false;
            }
            else if (responseLine.indexOf("closed") != -1){
                createDialogBox("Something wrong happened");
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
                break;

            case "puzzledone":
                puzzleDone(message);
                break;

            case "declined":
                declined(message);
                break;

            case "puzzle":
                System.out.println(" ----------- PUZZLE CREATION ----------");
                System.out.println(message);
                displayPuzzle(message);

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
        createDialogBox("Your challenge with " + oppo[2] + "has been accepted");
        // Update playing against
        np.setPlayingAgainst(oppo[2]);
    }

    private void noUser(String message){
        // Notify person no user exists for that
        createDialogBox("Sorry but no user named " + message.trim().split(" ")[1] + " exists");
    }

    private void userBusy(String message){
        // Notify person user currently busy
        // userbusy user2 currently busy
        String player = message.trim().split(" ")[1];
        createDialogBox("User: " + player + " is currently busy, please try again later");
    }

    private void puzzleDone(String message){
        String player = message.trim().split(" ")[2];
        createDialogBox("Puzzle has been completed by " + player);
        lost = true;
        // Notify user, puzzle completed by other person
        // puzzledone by user1
    }

    private void handleChallenge(String message){
        // Pop up window, see if message is accepted or declined
        String player = message.trim().split(" ")[3];
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Challenge from " + player +
                ". Do you accept the challenge?", "Challenge notification", dialogButton);
        if(dialogResult == 0) {
            // send acceptance of
            message("accepted " + username + player);
            System.out.println("Sent: " + "accepted " + username + player);

        } else {
            message("decline " + username + " " + player);
        }
        // User can wish to accept or decline the challenge
    }

    private void displayPuzzle(String message){
        lost = false;
        f = new JFrame("GridLock");

        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);

        puzzleGame = new PuzzleGame(6,6,2,5); // NOT SURE ABOUT THE EXIT CONDITION
        // Create the puzzle
        System.out.println(message);

        System.out.println("---------------------");
        String[] parts = message.split("-");
        for (String s: parts){
            System.out.println(s);

        }
        System.out.println("---------------------");
        boolean skip = false;
        for (String s: parts){
            if (!skip){
                skip = true;
                continue;
            }
            Vehicle v = Vehicle.strToVehicle(s.trim());
            puzzleGame.addVehicle(v);
        }

        pv = new PuzzleView(puzzleGame, 50);
        pc = new PuzzleController(puzzleGame, pv);

        bp = new ButtonPanel();
        bc = new SideButtonController(pv, puzzleGame, bp);

        gameView = new GameView(bp, bc, pv, pc);
        gameController = new GameController(gameView, pc);

        puzzleGame.setNUIController(this);

        f.add(gameView);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setMinimumSize(new Dimension(gameView.getWidth(), gameView.getHeight() + 23));
        f.setVisible(true);

    }

    private void createDialogBox(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    private void declined(String message){
        String player = message.trim().split(" ")[1];
        createDialogBox("Your challenge with " + player + " has been declined");
    }

    public void puzzleDone(){
        System.out.println("PUZZLE DONE CALLED");
        createDialogBox("Congratulations, you won");
        // Hide all the windows etc
        if (!lost){
            message("done " + username + " " + opponent);
            lost = true;
        }
    }
}
