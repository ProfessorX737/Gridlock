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
		Game game = new Game(5,5,2,4);
		game.addVehicle(true, 2, 1, 0, Color.RED);
		game.addVehicle(false, 2, 1, 1, Color.BLUE);
		BoardView bv = new BoardView(game,50);
		BoardController bc = new BoardController(game,bv);
		bv.setController(bc);
        f.add(bv,BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
