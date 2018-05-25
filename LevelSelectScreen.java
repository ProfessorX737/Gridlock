import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LevelSelectScreen extends JFrame {
	private BackBar backBar;
	private LevelSelect view;
	private int width;
	private int height;
	private JButton backButton;

	public static final int DEFAULT_WIDTH = MainMenu.WIDTH;
	public static final int DEFAULT_HEIGHT = MainMenu.HEIGHT;
	public static final String TITLE = "Select a Level";
	
	public LevelSelectScreen() {
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;

		this.setMinimumSize(GridlockGame.MINIMUM_SIZE);
		this.view = new LevelSelect();
		this.backBar = new BackBar(this.view);
		this.add(this.backBar);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}
	
	public void setBackBarController(ActionListener al) {
	    backBar.setController(al);
	}
	
	public void setLevelSelectController(ActionListener al) {
		this.view.setController(al);
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		ImageIcon bg = new ImageIcon(this.getClass().getResource("Assets/board.png"));
		bg = new ImageIcon(bg.getImage().getScaledInstance(this.getWidth(), this.getWidth(), Image.SCALE_SMOOTH));
		g.drawImage(bg.getImage(), 0, 0, null);
	}
}
