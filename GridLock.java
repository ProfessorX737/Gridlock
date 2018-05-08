import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class GridLock{
	public static void main(String args[]) {
		JFrame f = new JFrame("GridLock");
		f.setLayout(new BorderLayout());
		f.setLayout(new BorderLayout());
		f.setBackground(Color.BLACK);
		PuzzleGame puzzleGame = new PuzzleGame(10,10,2,4);
		puzzleGame.addVehicle(true, 2, 3, 3, Color.BLUE);
		puzzleGame.addVehicle(false, 2, 2, 1, Color.RED);
		puzzleGame.addVehicle(true, 2, 0, 4, Color.GREEN);
		puzzleGame.addVehicle(false, 3, 0, 0, Color.PINK);
		PuzzleView bv = new PuzzleView(puzzleGame,50);
		PuzzleController bc = new PuzzleController(puzzleGame,bv);
		bv.setController(bc);
        f.add(bv,BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
