import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LevelSelect extends JPanel {
	private int width;
	private int height;
	private int numLevels;
    private List<JButton> levels;
    private String[] levelNames;
    
    public LevelSelect(int width, int height) {
		this.width = width;
		this.height = height;
		this.numLevels = GridlockGame.NUM_LEVELS;
		this.levelNames = GridlockGame.DISPLAY_LEVEL_NAMES;
		this.setPreferredSize(new Dimension(this.width,this.height));
		this.levels = new ArrayList<>();
		this.setLayout(new GridBagLayout());
		this.setBorder(javax.swing.BorderFactory.createEtchedBorder());
			
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;

		for(int i = 0; i < this.numLevels; i++) {
			JButton button = new JButton(this.levelNames[i]);
			button.setActionCommand(GridlockGame.LEVEL_NAMES[i]);
			c.gridy += i;
			this.add(button,c);
			this.levels.add(button);
		}
    }
    
    public void setController(ActionListener al) {
		for(JButton b : this.levels) {
			b.addActionListener(al);
		}
    }
}