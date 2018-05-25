import java.awt.*;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class LevelView extends JPanel {
	private int width;
	private int height;
	private int puzzleSize;
    private Map<Integer, JButton> buttons;
    private int cellSize;
    
    public static final int SCROLL_SPEED = 16;

	public LevelView(List<PuzzleGame> puzzles, int width, int height, int puzzleSize) {
		this.cellSize = PuzzleView.DEFAULT_CELL_SIZE;
		this.width = width * 27/40;
		this.height = height * 27/40;
		this.buttons = new HashMap<>();
		this.puzzleSize = puzzleSize;
//		this.setBackground(new Color(76,50,60, 80));
		this.setOpaque(false);

		JPanel main = new JPanel();
		main.setLayout(new GridLayout(puzzles.size(),1));
		main.setSize(new Dimension(this.width,this.puzzleSize*puzzles.size()));
		main.setOpaque(false);
//		main.setBackground(new Color(76,50,60));
		main.setBorder(BorderFactory.createEmptyBorder());
		
		for(PuzzleGame puzzle : puzzles) {
			PuzzleView pv = new PuzzleView(puzzle,puzzleSize/GridlockGame.DEFAULT_BOARD_SIZE);
			JButton button = new JButton(" Puzzle " + Integer.toString(puzzle.getId()),this.createImageIcon(pv));
			button.setActionCommand(Integer.toString(puzzle.getId()));
			button.setHorizontalAlignment(SwingConstants.LEFT);
			button.setBorder(BorderFactory.createLineBorder(new Color(76,50,60)));
			button.setFont(new Font("Arial", Font.PLAIN, cellSize * 7/25));
//			button.setBackground(new Color(76,50,60,200));
			button.setForeground(new Color(245,222,179));
			button.setOpaque(false);
			this.buttons.put(puzzle.getId(), button);
			main.add(button);
		}
		
		JScrollPane scroll = new JScrollPane(main);
		scroll.setSize(new Dimension(this.width,this.height));
		scroll.setPreferredSize(new Dimension(this.width,this.height));
		scroll.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
		scroll.getVerticalScrollBar().setOpaque(false);
		scroll.getVerticalScrollBar().setBackground(new Color(76,50,60));
		scroll.setOpaque(false);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(76,50,60)));
//		scroll.setBackground(new Color(76,50,60, 80));
//		scroll.getViewport().setOpaque(false);
		scroll.getViewport().setBackground(new Color(76,50,60,180));
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
