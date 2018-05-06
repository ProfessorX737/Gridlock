import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class GridLock{
	public static void main(String args[]) {
		JFrame f = new JFrame("GridLock");
		f.setLayout(new BorderLayout());
		f.setLayout(new BorderLayout());
		f.setBackground(Color.BLACK);
		Game game = new Game(10,10,2,4);
		game.addVehicle(true, 2, 3, 3, Color.BLUE);
		game.addVehicle(false, 2, 2, 1, Color.RED);
		game.addVehicle(true, 2, 0, 4, Color.GREEN);
		game.addVehicle(false, 3, 0, 0, Color.PINK);
		BoardView bv = new BoardView(game,50);
		BoardController bc = new BoardController(game,bv);
		bv.setController(bc);
        f.add(bv,BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
