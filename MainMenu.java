import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame {

	private MenuButtons menuButtons;
	
	public static final int BTN_WIDTH = 100;
	public static final int BTN_HEIGHT = 30;
	public static final String MENU_BKG_PIC = "Assets/board.png";
	public static final String MENU_BTN_PIC = "Assets/button.png";
	
	public static final int HEIGHT = PuzzleView.DEFAULT_CELL_SIZE * (GridlockGame.NUM_LEVELS + 2);
	public static final int WIDTH = HEIGHT + ButtonPanel.WIDTH;
	
	public MainMenu() {
		this.setTitle("Gridlock");
//		this.setLayout(new GridBagLayout());
		this.setMinimumSize(GridlockGame.MINIMUM_SIZE);

		this.menuButtons = new MenuButtons();

		this.add(menuButtons);

		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setController(ActionListener al) {
		menuButtons.getPlayButton().addActionListener(al);
		menuButtons.getExitBtn().addActionListener(al);
		menuButtons.getMultiplayerBtn().addActionListener(al);
		this.setVisible(true);
	}
	
}
