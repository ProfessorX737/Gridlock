import java.awt.*;

import javax.swing.*;

public class GridLock{
	public static void test2() {
		JFrame f = new JFrame("GridLock");
		JPanel container = new JPanel();

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
		puzzleGame.initState();
		
		PuzzleView pv = new PuzzleView(puzzleGame,50);
		PuzzleController pc = new PuzzleController(puzzleGame,pv);
		pv.setController(pc);

		ButtonController bc = new SideButtonController(pv, puzzleGame);
		ButtonPanel bp = new ButtonPanel();
		bp.setController(bc);


		container.setLayout(new GridLayout(1,2));
		container.add(pv,BorderLayout.CENTER);
		container.add(bp, BorderLayout.EAST);


		f.add(container, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
	public static void test1() {
		JFrame f = new JFrame("GridLock");
		JPanel container = new JPanel();

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
		puzzleGame.initState();


		PuzzleView pv = new PuzzleView(puzzleGame,50);
		PuzzleController pc = new PuzzleController(puzzleGame,pv);
		pv.setController(pc);

		ButtonController bc = new SideButtonController(pv, puzzleGame);
		ButtonPanel bp = new ButtonPanel();
		bp.setController(bc);

		container.setLayout(new GridLayout(1,2));
		container.add(pv,BorderLayout.CENTER);
		container.add(bp, BorderLayout.EAST);

		f.add(container, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
	public static void main(String args[]) {
		test2();
	}
}
