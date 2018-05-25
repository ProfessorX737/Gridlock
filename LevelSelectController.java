import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelSelectController implements ActionListener {
	private JFrame levelSelect;
	private PuzzleSelectScreen[] levelView;
	private GridlockGame game;
	
	public LevelSelectController(JFrame levelSelect, PuzzleSelectScreen[] levelViews) {
		this.levelSelect = levelSelect;
		this.levelView = levelViews;
	}

	public LevelSelectController(JFrame levelSelect, GridlockGame game) {
		this.levelSelect = levelSelect;
		this.game = game;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		for(int i = 0; i < GridlockGame.NUM_LEVELS; i++) {
			if(action.equals(GridlockGame.LEVEL_NAMES[i])) {
				this.levelSelect.setVisible(false);
				this.levelView[i].refresh();
				this.levelView[i].setVisible(true);
			}
		}
	}
}
