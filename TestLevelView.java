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
        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);
        
        
    	GridlockGame game = new GridlockGame();
    	game.generatePuzzles();
    	game.savePuzzles();
    	
    	LevelView lv = new LevelView(game.getPuzzles(GridlockGame.ULTRA_HARD),50);

        f.add(lv, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
    
    public static void test1() {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());

		JScrollPane scroll = new JScrollPane(new TestPane());

		frame.add(scroll);

		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
    }

    public static void main(String args[]) {
    	test3();
    }

}
