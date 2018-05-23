import javax.swing.*;
import java.awt.*;

public class GridLock {

    public static void test1() {
        JFrame f = new JFrame("GridLock");

        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);
        GridlockGame game = new GridlockGame();
        PuzzleGame puzzleGame = game.getPuzzle(GridlockGame.ULTRA_HARD, 0);
        puzzleGame.initState();

        PuzzleView pv = new PuzzleView(puzzleGame, 50);
        PuzzleController pc = new PuzzleController(puzzleGame, pv);

        ButtonPanel bp = new ButtonPanel();
        SideButtonController bc = new SideButtonController(pv, puzzleGame, bp);

        GameView gameView = new GameView(bp, bc, pv, pc);
        GameController gameController = new GameController(gameView, pc);

        f.add(gameView);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setMinimumSize(new Dimension(gameView.getWidth(), gameView.getHeight() + 23));
        f.setVisible(true);
    }

    public static void main(String args[]) {
        test1();
    }
}

//		final long startTime = System.currentTimeMillis();
//        PuzzleGame puzzleGame = foo.generateMediumPuzzle();
//		final long endTime = System.currentTimeMillis();
//		System.out.printf("Puzzle generation time: %d ms%n", (endTime - startTime));
//        puzzleGame.initState();
