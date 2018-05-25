import javax.swing.*;
import java.awt.*;

public class GridLock {
    
    public static void test0() {
		GridlockGame game = new GridlockGame();
		game.generatePuzzlesInBackground();
		MainMenu menu = new MainMenu();
		MenuController menuContr = new MenuController(game,menu);
		menu.setController(menuContr);
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
