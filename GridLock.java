import javax.swing.*;
import java.awt.*;

public class GridLock {
    
    public static void test0() {
    	JFrame f = new JFrame("GridLock");
		f.add(new Animation(f));
		f.setSize(500, 400);                       // set the size of the window
        f.setLocationRelativeTo(null);             // set the window to center of the screen
        f.setVisible(true);                        // indicate window
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit program when click the close button of window
    	/*
		GridlockGame game = new GridlockGame();
		game.generatePuzzlesInBackground();
		MainMenu menu = new MainMenu();
		MenuController menuContr = new MenuController(game,menu);
		menu.setController(menuContr);
	*/
    }

    public static void main(String args[]) {
        test0();
    }
}

//		final long startTime = System.currentTimeMillis();
//        PuzzleGame puzzleGame = foo.generateMediumPuzzle();
//		final long endTime = System.currentTimeMillis();
//		System.out.printf("Puzzle generation time: %d ms%n", (endTime - startTime));
//        puzzleGame.initState();
