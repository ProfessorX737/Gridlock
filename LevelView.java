import java.util.List;
import java.util.Map;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LevelView extends JPanel {
	private int width;
	private int height;
	private int puzzleSize;
    private Map<Integer, JButton> buttons;

	public LevelView(List<PuzzleGame> puzzles, int width, int height, int puzzleSize) {
		this.width = width;
		this.height = height;
		this.buttons = new HashMap<>();
		this.puzzleSize = puzzleSize;

		this.setLayout(new GridLayout(5,2));
		this.setPreferredSize(new Dimension(this.width, this.height));
		
		for(PuzzleGame puzzle : puzzles) {
			PuzzleView pv = new PuzzleView(puzzle,this.puzzleSize);
			JButton button = new JButton(" Puzzle " + Integer.toString(puzzle.getId()),this.createImageIcon(pv));
			this.buttons.put(puzzle.getId(), button);
			this.add(button);
		}

	}
	
	private ImageIcon createImageIcon(JPanel panel) {
		BufferedImage image = new BufferedImage(panel.getWidth(),panel.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		panel.paint(g);
		g.dispose();
		return new ImageIcon(image);
	}
}
