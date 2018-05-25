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

		this.view = new LevelSelect();
		/*this.setPreferredSize(new Dimension(PuzzleView.DEFAULT_CELL_SIZE * 11, PuzzleView.DEFAULT_CELL_SIZE * 8));

		ImageIcon back = new ImageIcon(this.getClass().getResource("Assets/back.png"));
		back = new ImageIcon(back.getImage().getScaledInstance((int) Math.round(PuzzleView.DEFAULT_CELL_SIZE * 1.5),
				PuzzleView.DEFAULT_CELL_SIZE, Image.SCALE_SMOOTH));

		this.backButton = new JButton(back);
		this.backButton.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(view, c);
		c.anchor = GridBagConstraints.NORTHWEST;
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 0, 0);
		this.add(backButton, c);*/
		this.backBar = new BackBar(this.view);
		this.add(this.backBar);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
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
