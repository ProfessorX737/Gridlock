import java.util.List;
import java.util.Map;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class LevelView extends JPanel {
	private int width;
	private int height;
	private int puzzleSize;
    private Map<Integer, JButton> buttons;
    
    public static final int SCROLL_SPEED = 16;

	public LevelView(List<PuzzleGame> puzzles, int width, int height, int puzzleSize) {
		this.width = width;
		this.height = height;
		this.buttons = new HashMap<>();
		this.puzzleSize = puzzleSize;

		JPanel main = new JPanel();
		main.setLayout(new GridLayout(puzzles.size(),1));
		main.setSize(new Dimension(this.width,this.puzzleSize*puzzles.size()));
		
		for(PuzzleGame puzzle : puzzles) {
			PuzzleView pv = new PuzzleView(puzzle,puzzleSize/GridlockGame.DEFAULT_BOARD_SIZE);
			JButton button = new JButton(" Puzzle " + Integer.toString(puzzle.getId()),this.createImageIcon(pv));
			button.setActionCommand(Integer.toString(puzzle.getId()));
			button.setHorizontalAlignment(SwingConstants.LEFT);
			this.buttons.put(puzzle.getId(), button);
			main.add(button);
		}
		
		JScrollPane scroll = new JScrollPane(main);
		scroll.setSize(new Dimension(this.width,this.height));
		scroll.setPreferredSize(new Dimension(this.width,this.height));
		scroll.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
		this.add(scroll);
	}
	
	private ImageIcon createImageIcon(JPanel panel) {
		BufferedImage image = new BufferedImage(panel.getWidth(),panel.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		panel.paint(g);
		g.dispose();
		return new ImageIcon(image);
	}
	
	public void setController(ActionListener al) {
		for(JButton b : this.buttons.values()) {
			b.addActionListener(al);
		}
	}
}
