import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelSelectController implements ActionListener {
	private JFrame levelSelect;
	private JFrame[] levelView;
	private GridlockGame game;
	
	public LevelSelectController(JFrame levelSelect, JFrame[] levelViews) {
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
				this.levelView[i].setVisible(true);
			}
		}
	}

	private JFrame createFrame(Container view) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(view,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
        return frame;
	}

}
