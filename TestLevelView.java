import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestLevelView {
    public static void test3() {
        JFrame f = new JFrame("GridLock");
        f.setLayout(new BorderLayout());
        f.setLayout(new BorderLayout());
        f.setBackground(Color.BLACK);
        
        
    	GridlockGame game = new GridlockGame();
    	System.out.printf("size of firt level: %d%n",game.getPuzzles(GridlockGame.ULTRA_HARD).size());
    	game.generatePuzzles();
    	game.savePuzzles();
    	
    	LevelView lv = new LevelView(game.getPuzzles(GridlockGame.ULTRA_HARD),300,500,10);

        f.add(lv, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public static void main(String args[]) {
        test3();
    }

}
