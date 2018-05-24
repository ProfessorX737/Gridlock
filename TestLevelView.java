import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestLevelView {

    public static void test2() {
        JFrame f = new JFrame("GridLock");
        f.setLayout(new BorderLayout());
        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);
        
        
    	GridlockGame game = new GridlockGame();
    	game.generatePuzzles();
    	game.savePuzzles();
    	
    	LevelSelect ls = new LevelSelect(150,300);

        f.add(ls, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public static void test3() {
        JFrame f = new JFrame("GridLock");
        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);
        
    	GridlockGame game = new GridlockGame();
    	game.generatePuzzles();
    	game.savePuzzles();
    	
    	int cellSize = 50;
    	int height = cellSize * GridlockGame.DEFAULT_BOARD_SIZE;
    	int width = height + ButtonPanel.WIDTH;
    	LevelView lv = new LevelView(game.getPuzzles(GridlockGame.ULTRA_HARD),width,height,cellSize);

        f.add(lv, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public static void test4() {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());

    	GridlockGame game = new GridlockGame();
    	game.generatePuzzles();
    	game.savePuzzles();
    	
    	int cellSize = 50;
    	int height = cellSize * GridlockGame.DEFAULT_BOARD_SIZE;
    	int width = height + ButtonPanel.WIDTH;
    	LevelView lv = new LevelView(game.getPuzzles(GridlockGame.ULTRA_HARD),width,height,cellSize);

		BackFrame bframe = new BackFrame("Select Puzzle", lv, 25);

		frame.add(bframe,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void test5() {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());

    	GridlockGame game = new GridlockGame();
    	game.generatePuzzles();
    	game.savePuzzles();
    	
    	int cellSize = 50;
    	int height = cellSize * GridlockGame.DEFAULT_BOARD_SIZE;
    	int width = height + ButtonPanel.WIDTH;
    	LevelSelect ls = new LevelSelect(width,height);

		BackFrame bframe = new BackFrame("Select Difficulty", ls, 25);

		frame.add(bframe,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void test6() {
    	MainMenu menu = new MainMenu();
    	GridlockGame game = new GridlockGame();
    	MenuController menuContr = new MenuController(game,menu);
    	menu.setController(menuContr);
    }

    public static void main(String args[]) {
    	test6();
    }

}
