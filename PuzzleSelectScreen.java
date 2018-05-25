import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class PuzzleSelectScreen extends JFrame implements RefreshableFrame {
	private GridlockGame game;
	private int level;
	private LevelView levelView;
	private String title;
	private BackBar backBar;
	private JFrame menu;
	
	public PuzzleSelectScreen(GridlockGame game, int level, JFrame menu) {
		this.setMinimumSize(GridlockGame.MINIMUM_SIZE);
//		this.setLayout(new BorderLayout());
		this.game = game;
		this.level = level;
		this.levelView = new LevelView(this.game.getPuzzles(this.level),MainMenu.WIDTH,MainMenu.HEIGHT,PuzzleView.DEFAULT_CELL_SIZE);
		this.backBar = new BackBar(levelView);
		this.add(this.backBar);
//		this.add(backBar, BorderLayout.NORTH);
//		this.add(levelView, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(false);
		this.menu = menu;
	}
	
	public void setBackBarController(ActionListener al) {
		this.backBar.setController(al);
	}

	@Override
	public void refresh() {
//		this.remove(this.levelView);
//		this.remove(this.backBar);
		this.levelView = new LevelView(this.game.getPuzzles(this.level),MainMenu.WIDTH,MainMenu.HEIGHT,PuzzleView.DEFAULT_CELL_SIZE);
		LevelController puzzleSelectController = new LevelController(this.game, this.level, this, menu);
		this.levelView.setController(puzzleSelectController);
//		this.add(this.levelView);
//		this.backBar = new BackBar(this.levelView);
//        this.add(this.backBar);
//        this.pack();
        backBar.updateContent(levelView);
		System.out.println("Refreshing puzzle select");
	}

}
