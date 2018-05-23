import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LevelSelect extends JPanel {
	private int width;
	private int height;
	private int numLevels;
    private Map<Integer, JButton> levels;
    private String[] levelNames;
    
    public LevelSelect(int width, int height) {
    	this.width = width;
    	this.height = height;
    	this.numLevels = GridlockGame.NUM_LEVELS;
    	this.levelNames = GridlockGame.DISPLAY_LEVEL_NAMES;
    	this.setPreferredSize(new Dimension(this.width,this.height));
    	this.levels = new HashMap<>();
    	this.setLayout(new GridBagLayout());
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    	
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
    	for(int i = 0; i < this.numLevels; i++) {
    		JButton button = new JButton(this.levelNames[i]);
    		c.gridy += i;
    		this.add(button,c);
    		this.levels.put(i, button);
    	}
    }
}