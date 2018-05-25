import javax.swing.*;
import java.awt.*;

public class GridLock {
    
    public static void test0() {
		GridlockGame game = new GridlockGame();
		game.generatePuzzlesInBackground();
		MainMenu menu = new MainMenu();
		MenuController menuContr = new MenuController(game,menu);
		menu.setController(menuContr);
		
    	JFrame f = new JFrame("GridLock");
		Animation animation = new Animation(f,menu);
		f.add(animation);
		f.pack();
		f.setSize(new Dimension(500,500));
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
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
