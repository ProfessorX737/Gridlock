import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class LevelSelectScreen extends JFrame {
	private BackBar backBar;
	private LevelSelect view;
	private int width;
	private int height;

	public static final int DEFAULT_WIDTH = MainMenu.WIDTH;
	public static final int DEFAULT_HEIGHT = MainMenu.HEIGHT;
	public static final String TITLE = "Select a Level";
	
	public LevelSelectScreen() {
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
		this.setLayout(new BorderLayout());
		
		this.view = new LevelSelect(this.width, this.height);
		this.backBar = new BackBar(TITLE);
		this.add(this.backBar,BorderLayout.NORTH);
		this.add(this.view, BorderLayout.CENTER);

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(false);
	}
	
	public void setBackBarController(ActionListener al) {
		this.backBar.setController(al);
	}
	
	public void setLevelSelectController(ActionListener al) {
		this.view.setController(al);
	}
}
