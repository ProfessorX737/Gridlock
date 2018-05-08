import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

public class GridLock{
	public static void main(String args[]) {
		JFrame f = new JFrame("GridLock");
		f.setLayout(new BorderLayout());

		f.setBackground(Color.BLACK);

		JPanel container = new JPanel();
		ButtonPanel panelTwo = new ButtonPanel(container);
		panelTwo.add(new JLabel("2"));

		Game game = new Game(6,6,2,4);
		game.addVehicle(true, 2, 3, 3, Color.BLUE);
		game.addVehicle(false, 2, 2, 1, Color.RED);
		game.addVehicle(true, 2, 0, 4, Color.GREEN);
		game.addVehicle(false, 3, 0, 0, Color.PINK);
		BoardView bv = new BoardView(game,50);

		BoardController bc = new BoardController(game,bv);

		container.setLayout(new GridLayout(1,2));
		container.add(bv,BorderLayout.CENTER);
		container.add(panelTwo, BorderLayout.EAST);
		bv.setController(bc);
//        f.add(bv,BorderLayout.CENTER);
		f.add(container);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();

		// Button ui


		f.setVisible(true);
	}
}
