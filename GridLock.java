import java.awt.*;

import javax.swing.*;
import java.awt.CardLayout;

import javax.swing.*;

public class GridLock { 	
	public static void main(String[] args) {
		JFrame f = new JFrame("GridLock");
		String fileName = "puzzle1.txt";
		f.add(new Animation(f,fileName));
		f.setSize(400, 400);                       // set the size of the window
        f.setLocationRelativeTo(null);             // set the window to center of the screen
        f.setVisible(true);                        // indicate window
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit program when click the close button of window
	}
	
	public static void board(String fileName) {
		JFrame f = new JFrame("GridLock");
		JPanel container = new JPanel();
		ButtonPanel panelTwo = new ButtonPanel(container, f);
		
		f.setLayout(new BorderLayout());
		f.setBackground(Color.BLACK);
		
		PuzzleGame puzzleGame = new PuzzleGame(6,6,2,5);
		
		//map the text file
		Map m = new Map(fileName);
		
		//add the vehicle by reading the text file
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 6; x++) {
				//0: red, IsVertical, Length is 2
				if(m.getMap(x, y).equals("0")) {
					puzzleGame.addVehicle(false, 2, y, x, Color.RED);
				}
				//1: orange, IsVertical, Length is 2 
				if(m.getMap(x, y).equals("1")) {
					puzzleGame.addVehicle(false, 2, y, x, Color.ORANGE);
				}
				//2: orange, NotVertical, Length is 2
				if(m.getMap(x, y).equals("2")) {
					puzzleGame.addVehicle(true, 2, y, x, Color.ORANGE);
				}
				//3: orange, IsVertical, Length is 3
				if(m.getMap(x, y).equals("3")) {
					puzzleGame.addVehicle(false, 3, y, x, Color.ORANGE);
				}
				//4: orange, NotVertical, Length is 3
				if(m.getMap(x, y).equals("4")) {
					puzzleGame.addVehicle(true, 3, y, x, Color.ORANGE);
				}
			}
		}

		PuzzleView bv = new PuzzleView(puzzleGame,50);
		PuzzleController bc = new PuzzleController(puzzleGame,bv);


		container.setLayout(new GridLayout(1,2));
		container.add(bv,BorderLayout.CENTER);
		container.add(panelTwo, BorderLayout.EAST);

		
		bv.setController(bc);
		f.add(container, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);             // set the window to center of the screen
		f.setVisible(true);
	}
}