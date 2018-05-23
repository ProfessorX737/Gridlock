import java.util.List;
import java.util.Map;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class LevelView extends JPanel {
	private int puzzleSize;
    private Map<Integer, JButton> buttons;

	public LevelView(List<PuzzleGame> puzzles, int puzzleSize) {
		this.buttons = new HashMap<>();
		this.puzzleSize = puzzleSize;

		JPanel main = new JPanel();
		main.setLayout(new GridLayout(puzzles.size(),1));
		main.setSize(new Dimension(puzzleSize*3,this.puzzleSize*puzzles.size()));
        //main.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		
		for(PuzzleGame puzzle : puzzles) {
			PuzzleView pv = new PuzzleView(puzzle,puzzleSize/GridlockGame.DEFAULT_BOARD_SIZE);
			JButton button = new JButton(" Puzzle " + Integer.toString(puzzle.getId()),this.createImageIcon(pv));
			button.setHorizontalAlignment(SwingConstants.LEFT);
			this.buttons.put(puzzle.getId(), button);
			main.add(button);
		}
		
		JScrollPane scroll = new JScrollPane(main);
		scroll.setPreferredSize(new Dimension(puzzleSize*3,puzzleSize*3));
		this.add(scroll);
	}
	
	private ImageIcon createImageIcon(JPanel panel) {
		BufferedImage image = new BufferedImage(panel.getWidth(),panel.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		panel.paint(g);
		g.dispose();
		return new ImageIcon(image);
	}
}
