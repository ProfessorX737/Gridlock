import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Network Controller
 * handles all the events and manages the data of game state
 */

public class NetUIController implements NetworkUIController{

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

    private String[] getList(){
        try {
            os.println("getlist " + username);
            os.flush();
            String responseLine = is.readLine();
            System.out.println("From server: " + responseLine);


        } catch (IOException e) {
            System.err.println("IOException:  " + e);
            return false;
        }

    }


}
