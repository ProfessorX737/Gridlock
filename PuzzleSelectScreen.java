import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class PuzzleSelectScreen extends JFrame implements RefreshableFrame {
	private GridlockGame game;
	private int level;
	private LevelView levelView;
	private String title;
	private BackBar backBar;
	
	public static final String TITLE = "Select A Puzzle";
	
	public PuzzleSelectScreen(GridlockGame game, int level) {
		this.setLayout(new BorderLayout());
		this.game = game;
		this.level = level;
		this.levelView = new LevelView(this.game.getPuzzles(this.level),MainMenu.WIDTH,MainMenu.HEIGHT,PuzzleView.DEFAULT_CELL_SIZE);
		this.backBar = new BackBar(TITLE);
		this.add(backBar, BorderLayout.NORTH);
		this.add(levelView, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(false);
	}
	
	public void setBackBarController(ActionListener al) {
		this.backBar.setController(al);
	}

	@Override
	public void refresh() {
		this.remove(this.levelView);
		this.levelView = new LevelView(this.game.getPuzzles(this.level),MainMenu.WIDTH,MainMenu.HEIGHT,PuzzleView.DEFAULT_CELL_SIZE);
		LevelController puzzleSelectController = new LevelController(this.game, this.level, this);
		this.levelView.setController(puzzleSelectController);
		this.add(this.levelView);
	}

}
