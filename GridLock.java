import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GridLock {
    private final static String red_car = "r";
    private final static String road = "-";
    private final static String wall = "W";

    public static void test2() {
        JFrame f = new JFrame("GridLock");
        JPanel container = new JPanel();

        f.setLayout(new BorderLayout());
        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);
//		PuzzleGame puzzleGame = new PuzzleGame(6,6,2,5);
//		puzzleGame.addVehicle(false, 2, 2, 2, Color.RED);
//		puzzleGame.addVehicle(false, 3, 0, 0, Color.ORANGE);
//		puzzleGame.addVehicle(true, 2, 0, 3, Color.ORANGE);
//		puzzleGame.addVehicle(true, 3, 0, 4, Color.ORANGE);
//		puzzleGame.addVehicle(true, 3, 0, 5, Color.ORANGE);
//		puzzleGame.addVehicle(true, 2, 1, 0, Color.ORANGE);
//		puzzleGame.addVehicle(false, 2, 1, 1, Color.ORANGE);
//		puzzleGame.addVehicle(false, 2, 3, 0, Color.ORANGE);
//		puzzleGame.addVehicle(true, 2, 3, 2, Color.ORANGE);
//		puzzleGame.addVehicle(true, 2, 4, 1, Color.ORANGE);
//		puzzleGame.addVehicle(false, 2, 5, 2, Color.ORANGE);
//		puzzleGame.addVehicle(false, 2, 4, 4, Color.ORANGE);
//		puzzleGame.addVehicle(false, 2, 5, 4, Color.ORANGE);
//		puzzleGame.initState();
        PuzzleGeneratorAStar foo = new PuzzleGeneratorAStar();
        PuzzleGame puzzleGame = foo.generateMediumPuzzle();
        puzzleGame.initState();
        List<int[][]> path = PuzzleSolver.solve(puzzleGame);
        for (int[][] state : path) {
            showBoard(puzzleGame, state);
            System.out.println("");
        }

        PuzzleView pv = new PuzzleView(puzzleGame, 50);
        PuzzleController pc = new PuzzleController(puzzleGame, pv);
        pv.setController(pc);

        ButtonPanel bp = new ButtonPanel();
        ButtonController bc = new SideButtonController(pv, puzzleGame, bp);
        bp.setController(bc);
        pc.setButtonController(bc);
        pv.setController(bc.getMouseAdapter());

        container.setLayout(new GridLayout(1, 2));
        container.add(pv, BorderLayout.CENTER);
        container.add(bp, BorderLayout.EAST);

        f.add(container, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public static void test1() {
        JFrame f = new JFrame("GridLock");

        // JPanel container = new JPanel();

        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);
        PuzzleGeneratorAStar foo = new PuzzleGeneratorAStar();

		final long startTime = System.currentTimeMillis();
        PuzzleGame puzzleGame = foo.generateMediumPuzzle();
		final long endTime = System.currentTimeMillis();
		System.out.printf("Puzzle generation time: %d ms%n", (endTime - startTime));
        puzzleGame.initState();

        PuzzleView pv = new PuzzleView(puzzleGame, 50);
        PuzzleController pc = new PuzzleController(puzzleGame, pv);
        // pv.setController(pc);

        ButtonPanel bp = new ButtonPanel();
        SideButtonController bc = new SideButtonController(pv, puzzleGame, bp);
        // bp.setController(bc);
        // pc.setButtonController(bc);
        // pv.setController(bc.getMouseAdapter());
        GameView gameView = new GameView(bp, bc, pv, pc);
        GameController gameController = new GameController(gameView, pc);
        // f.setMinimumSize(new Dimension(450, 300));


        // container.setLayout(new GridLayout(1,2));
//		container.setLayout(new GridBagLayout());
//		GridBagConstraints c = new GridBagConstraints();
//		c.gridx = 0;
//		c.gridy= 0;
//		c.weightx = 1;
//		container.add(pv);
//
//		c.gridx = 1;
//		c.gridy = 0;
//		c.weightx = 0.5;
//		container.add(bp);
//
//		f.add(container);
        f.add(gameView);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        System.out.println(gameView.getSize());
        f.setMinimumSize(new Dimension(gameView.getWidth(), gameView.getHeight() + 23));
        f.setVisible(true);
    }

    public static void test3() {
        JFrame f = new JFrame("GridLock");
        f.setLayout(new BorderLayout());
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
        PuzzleView bv = new PuzzleView(puzzleGame, 50);
        PuzzleController bc = new PuzzleController(puzzleGame, bv);
        bv.setController(bc);
        f.add(bv, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public static void main(String args[]) {
        test1();
//    	GridlockGame game = new GridlockGame();
//    	game.generatePuzzles();
    }

    static void showBoard(PuzzleGame game, int[][] board) {
        for (int y = -1; y <= game.getNumRows(); y++) {
            for (int x = -1; x <= game.getNumCols(); x++) {
                if (y == -1 || y == game.getNumRows() || x == -1 || x == game.getNumCols()) {
                    System.out.print(wall);
                } else if (board[y][x] == 0) {
                    System.out.print(red_car);
                } else if (board[y][x] == -1) {
                    System.out.print(road);
                } else {
                    System.out.print(Integer.toHexString(board[y][x]));
                }
            }
            System.out.println("");
        }
    }
}
