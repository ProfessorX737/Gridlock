import javax.swing.*;
import java.awt.*;

public class GridLock {
    
    public static void test0() {
    	MainMenu menu = new MainMenu();
    	GridlockGame game = new GridlockGame();
    	MenuController menuContr = new MenuController(game,menu);
    	menu.setController(menuContr);
    }
}

    public static void test1() {
        JFrame f = new JFrame("GridLock");

        // JPanel container = new JPanel();

        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);
        PuzzleGeneratorAStar foo = new PuzzleGeneratorAStar();
        PuzzleGame puzzleGame = foo.generateEasyPuzzle();
        puzzleGame.initState();
        List<int[][]> path = PuzzleSolver.solve(puzzleGame);
        for (int[][] state : path) {
            showBoard(puzzleGame, state);
            System.out.println("");
        }

        PuzzleView pv = new PuzzleView(puzzleGame, 50);
        PuzzleController pc = new PuzzleController(puzzleGame, pv);
        ButtonPanel bp = new ButtonPanel();
        SideButtonController bc = new SideButtonController(pv, puzzleGame, bp);
        BorderedPuzzleView borderedPuzzleView = new BorderedPuzzleView(pv);
        GameView gameView = new GameView(bp, bc, pv, pc, borderedPuzzleView);
        BorderedPuzzleController borderedPuzzleController = new BorderedPuzzleController(borderedPuzzleView);
        GameController gameController = new GameController(gameView, borderedPuzzleController);

        f.add(gameView);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setMinimumSize(new Dimension(gameView.getWidth(), gameView.getHeight() + 23));
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

