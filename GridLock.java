import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class GridLock{
	public static void test2() {
		JFrame f = new JFrame("GridLock");
		f.setLayout(new BorderLayout());
		f.setLayout(new BorderLayout());
		f.setBackground(Color.BLACK);
		PuzzleGame puzzleGame = new PuzzleGame(6,6,2,5);
		puzzleGame.addVehicle(false, 2, 2, 2, Color.RED);
		puzzleGame.addVehicle(false, 3, 0, 0, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 0, 3, Color.ORANGE);
		puzzleGame.addVehicle(true, 3, 0, 4, Color.ORANGE);
		puzzleGame.addVehicle(true, 3, 0, 5, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 1, 0, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 1, 1, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 3, 0, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 3, 2, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 4, 1, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 5, 2, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 4, 4, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 5, 4, Color.ORANGE);
		PuzzleView bv = new PuzzleView(puzzleGame,50);
		PuzzleController bc = new PuzzleController(puzzleGame,bv);
		bv.setController(bc);
        f.add(bv,BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
	public static void test1() {
		JFrame f = new JFrame("GridLock");
		f.setLayout(new BorderLayout());
		f.setLayout(new BorderLayout());
		f.setBackground(Color.BLACK);
		PuzzleGame puzzleGame = new PuzzleGame(6,6,2,5);
		puzzleGame.addVehicle(false, 2, 2, 0, Color.RED);
		puzzleGame.addVehicle(false, 3, 0, 0, Color.ORANGE);
		puzzleGame.addVehicle(true, 3, 1, 2, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 3, 0, Color.ORANGE);
		puzzleGame.addVehicle(false, 3, 5, 0, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 4, 4, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 3, 4, Color.ORANGE);
		puzzleGame.addVehicle(true, 3, 0, 5, Color.ORANGE);
		PuzzleView bv = new PuzzleView(puzzleGame,50);
		PuzzleController bc = new PuzzleController(puzzleGame,bv);
		bv.setController(bc);
        f.add(bv,BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
		
	}
	
	public static void test3() {
		JFrame f = new JFrame("GridLock");
		f.setLayout(new BorderLayout());
		f.setLayout(new BorderLayout());
		f.setBackground(Color.BLACK);
		PuzzleGeneratorAStar foo = new PuzzleGeneratorAStar();
		PuzzleGame puzzleGame = foo.generateSpicyPuzzle();
		PuzzleView bv = new PuzzleView(puzzleGame,50);
		PuzzleController bc = new PuzzleController(puzzleGame,bv);
		bv.setController(bc);
        f.add(bv,BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}

	public static void main(String args[]) {
		test3();
	}
}
