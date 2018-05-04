
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // generate a window
        JFrame gridlock = new JFrame();                   // generate a window for the game
        gridlock.add(new Board());
        gridlock.setSize(220, 220);                       // set the size of the window
        gridlock.setLocationRelativeTo(null);             // set the window to center of the screen
        gridlock.setVisible(true);                        // indicate window
        gridlock.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit program when click the close button of windo
    }

}
