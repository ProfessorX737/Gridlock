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
    private static boolean running;
    private static boolean lost;
    private static String opponent;


    // Networking
    private static String host;
    private static int portNumber;
    private static Socket clientSocket = null;
    private static PrintStream os = null;
    private static DataInputStream is = null;

    private static BufferedReader inputLine = null;
    private static boolean closed = false;

    // Game frame
    private static JFrame f;
    private static PuzzleGame puzzleGame;
    private static PuzzleView pv;
    private static PuzzleController pc;

    private static ButtonPanel bp;
    private static SideButtonController bc;

    private static GameView gameView;
    private static GameController gameController;

    /** Constructor for NetUIController
     * 
     * @param np, N
     * @param host
     * @param portNumber
     */
    public NetUIController(NetworkPanel np, String host, int portNumber){
        this.np = np;
        this.portNumber = portNumber;
        this.host = host;
        this.running = false;
    }
    
    /**
     * ActionListener for the Challenge Button
     */

    public ActionListener getChallengeBtnListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if (!running) return;
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
    
    /**
     * Update Button Action Listener
     */

    public ActionListener getUpdateBtnListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                getList();
            }
        };
        return al;
    };

    /**
     * Connect Button Listener
     * See if username is set, then attempt to connect
     */
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
                            np.lockConnectBtn();
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
    
    /**
     * Quit button listener
     */

    public ActionListener QuitBtnListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        };
        return al;
    };

    /**
     * Forfeit Button Listener
     */
    public ActionListener ForfeitListener(){
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	if (!running) return;
            	
                // Clicked the forfeit button
                // Notify server of forfeit, close the game, unlock the buttons
//                message("forfeit " + username + " " + opponent);
                if (lost == false){
                    message("forfeit " + username + " " + opponent);
                }

                np.unlockButtons();

                // Clean up
                if (f != null){
                    f.setVisible(false);
                    f.removeAll();

                }
                System.out.println("FORFEITING against " + opponent);

            }
        };
        return al;
    }

    /**
     * Handshaking message with server, announcing username
     * @return whether connection to server was successful
     */
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

    /**
     * Requests list of online users from server
     */
    private void getList(){
            os.println("getlist " + username);
            os.flush();

    }

    /**
     * Grabs the list from the server
     * @param users
     */
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

    /**
     * Function which event handler thread runs
     */
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

    /**
     * Main event handler for client side, when it receives messages from the server
     * 
     */
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
                break;

            case "setopp":
                setOpponent(message);
                break;

            case "forfeit":
                forfeit(message);
                break;

            case "stats":
                updateStats(message);
                break;

            default:
                break;
        }
    }

    /**
     * Sends a message to the server
     * @param message
     */
    private void message(String message){
        os.println(message);
        os.flush();
    }

    /**
     * A challenge user sent has been accepted
     * @param message
     */
    private void acceptChallenge(String message){
        // Notify challenge has been accepted
        String[] oppo = message.trim().split(" ");
        createDialogBox("Your challenge with " + oppo[2] + "has been accepted");
        // Update playing against
        np.setPlayingAgainst(oppo[2]);
    }

    /**
     * No user to challenge for that name exists
     * @param message
     */
    private void noUser(String message){
        // Notify person no user exists for that
        createDialogBox("Sorry but no user named " + message.trim().split(" ")[1] + " exists");
    }

    /**
     * Server notifes another user is ingame
     * @param message
     */
    private void userBusy(String message){
        // Notify person user currently busy
        // userbusy user2 currently busy
        String player = message.trim().split(" ")[1];
        createDialogBox("User: " + player + " is currently busy, please try again later");
    }

    /**
     * 	Server notfies puzzle has been completed
     * @param message
     */
    private void puzzleDone(String message){
        String player = message.trim().split(" ")[2];
        createDialogBox("Puzzle has been completed by " + player);
        lost = true;
        // Notify user, puzzle completed by other person
        // puzzledone by user1
        np.lockButtons();
    }

    /**
     * Handles a challenge from another user from the server
     * @param message
     */
    private void handleChallenge(String message){
        // Pop up window, see if message is accepted or declined
        String player = message.trim().split(" ")[3];
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Challenge from " + player +
                ". Do you accept the challenge?", "Challenge notification", dialogButton);
        if(dialogResult == 0) {
            // send acceptance of
            message("accepted " + username + " " + player);
            System.out.println("Sent: " + "accepted " + username + player);

        } else {
            message("decline " + username + " " + player);
        }
        // User can wish to accept or decline the challenge
    }

    /**
     * Creates a new JFrame Window with GridLock Puzzle
     * @param message, message to be turned into puzzle
     */
    private void displayPuzzle(String message){
        np.lockButtons();
        lost = false;
        f = new JFrame("GridLock " + username);

        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);

        GridlockGame game = new GridlockGame();
        PuzzleGame puzzleGame = new PuzzleGame(6, 6);
//        PuzzleGame puzzleGame = game.getPuzzle(0, 2);
        //puzzleGame = new PuzzleGame(6,6,2,5); // NOT SURE ABOUT THE EXIT CONDITION

        // Create the puzzle
//        System.out.println(message);
//
//        System.out.println("---------------------");
        String[] parts = message.split("-");
        for (String s: parts){
            System.out.println(s);

        }
        System.out.println("---------------------");
        boolean skip = false;
        for (String s: parts){
            if (!skip) {
                skip = true;
                continue;
            }
            Vehicle v = Vehicle.strToVehicle(s.trim());
            if (v.getColor().getRGB() == Color.RED.getRGB()) {
            	System.out.println("RED VEHICLE AT" + v.getRow());
            	// set the exit conditions
            	puzzleGame.setExitCol(5);
            	puzzleGame.setExitRow(v.getRow());
            }
            puzzleGame.addVehicle(v);
        }
        puzzleGame.initState();

        PuzzleView pv = new PuzzleView(puzzleGame, 50);
        PuzzleController pc = new PuzzleController(puzzleGame, pv);

        ButtonPanel bp = new ButtonPanel();
        SideButtonController bc = new SideButtonController(pv, puzzleGame, bp);
        BorderedPuzzleView borderedPuzzleView = new BorderedPuzzleView(pv);

        GameView gameView = new GameView(bp, bc, pv, pc, borderedPuzzleView);
        BorderedPuzzleController borderedPuzzleController = new BorderedPuzzleController(borderedPuzzleView);
        new GameController(gameView, borderedPuzzleController);

        puzzleGame.setNUIController(this);

        f.add(gameView);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.pack();
        f.setMinimumSize(new Dimension(gameView.getWidth(), gameView.getHeight() + 23));
        f.setVisible(true);

    }
    
    /**
     * Creates a pop up message
     * @param message
     */

    private void createDialogBox(String message){
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Notification from server, a challenge has been denied
     * @param message
     */
    private void declined(String message){
        String player = message.trim().split(" ")[1];
        createDialogBox("Your challenge with " + player + " has been declined");
    }
    
    /**
     * Puzzle Complete Method, 
     * called by PuzzleGame when puzzle is complete
     */

    public void puzzleDone(){
        System.out.println("PUZZLE DONE CALLED");
        createDialogBox("Congratulations, you won");
        // Hide all the windows etc
        if (!lost){
            message("done " + username + " " + opponent);
            lost = true;
        }
        // Clean up
        f.setVisible(false);
        f.dispose();
    }

    /**
     * Displays the opponen'ts name
     * @param message
     */
    private void setOpponent(String message){
        System.out.println(" RECEIVED SETOPP MESSAGE" + message);
        // setopp opponent's name
        String[] parts = message.split(" ");
        opponent = parts[1].toLowerCase();
        System.out.println(" THIS IS THE OPPONENTS NAME " + opponent);
        np.setOpponentName(opponent);

    }

    /**
     * Forfeit
     * @param message
     */
    private void forfeit(String message){
        String[] parts = message.split(" ");
        opponent = parts[1].toLowerCase();
        createDialogBox("User: " + opponent + ", has forfeited the game");

        lost = true; // Flag, to not notify the server once you complete the puzzle
    }

    /**
     * Get new wins and losses
     * @param message
     */
    private void updateStats(String message){
        System.out.println("IN UPDATE STATS " + message);
        String[] parts = message.split(" ");
        np.updateWins(Integer.parseInt(parts[1]));
        np.updateLoss(Integer.parseInt(parts[2]));
    }
}
