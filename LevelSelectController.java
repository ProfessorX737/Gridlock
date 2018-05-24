import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelSelectController implements ActionListener {
	private JFrame levelSelect;
	private JFrame[] levelView;
	
	public LevelSelectController(JFrame levelSelect, JFrame[] levelViews) {
		this.levelSelect = levelSelect;
		this.levelView = levelViews;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		for(int i = 0; i < GridlockGame.NUM_LEVELS; i++) {
			if(action.equals(GridlockGame.LEVEL_NAMES[i])) {
				this.levelSelect.setVisible(false);
				this.levelView[i].setVisible(true);
			}
		}
	}

}
