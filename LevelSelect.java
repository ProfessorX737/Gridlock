import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class LevelSelect extends JPanel {
	private int width;
	private int height;
	private int numLevels;
    private List<JButton> levels;
    private String[] levelNames;
    
    public LevelSelect() {
    	this.width = PuzzleView.DEFAULT_CELL_SIZE * 11;
    	this.height = PuzzleView.DEFAULT_CELL_SIZE * 8;
    	this.numLevels = GridlockGame.NUM_LEVELS;
    	this.levelNames = GridlockGame.DISPLAY_LEVEL_NAMES;
    	this.setPreferredSize(new Dimension(this.width,this.height));
    	setOpaque(false);
    	this.levels = new ArrayList<>();
    	this.setLayout(new GridBagLayout());
    	this.setBorder(BorderFactory.createEmptyBorder());

		ImageIcon buttonIcon = new ImageIcon(this.getClass().getResource("Assets/button.png"));
		buttonIcon = new ImageIcon(buttonIcon.getImage().getScaledInstance(PuzzleView.DEFAULT_CELL_SIZE * 3, PuzzleView.DEFAULT_CELL_SIZE, Image.SCALE_SMOOTH));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 5, 0);
    	for(int i = 0; i < this.numLevels; i++) {
    		JButton button = new JButton(this.levelNames[i], buttonIcon);
    		button.setActionCommand(GridlockGame.LEVEL_NAMES[i]);
    		button.setHorizontalTextPosition(JButton.CENTER);
    		button.setVerticalTextPosition(JButton.CENTER);
    		button.setBorder(BorderFactory.createEmptyBorder());
			button.setFont(new Font("Arial", Font.PLAIN, 14));
			button.setForeground(new Color(245,222,179));
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