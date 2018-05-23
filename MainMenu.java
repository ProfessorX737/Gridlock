import java.awt.Dimension;
import java.awt.Image;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu extends JFrame {
	private JPanel buttonPanel;
	private JButton playBtn;
	private JButton puzzlesBtn;
	private JButton exitBtn;
	
	public static final int BTN_WIDTH = 130;
	public static final int BTN_HEIGHT = 40;
	public static final int BTN_GAP = 5;
	public static final String MENU_BKG_PIC = "src/pictures/MainMenu_bt.png";
	public static final String MENU_BTN_PIC = "src/picutres/MainMenu_bg.png";
	
	public MainMenu() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int height = PuzzleView.DEFAULT_CELL_SIZE * GridlockGame.NUM_LEVELS;
		int width = height + ButtonPanel.WIDTH;
		this.setSize(new Dimension(width,height));
		this.setTitle("Gridlock");
		
		ImageIcon bkgImg = new ImageIcon(MENU_BKG_PIC);
		ImageIcon btnImg = new ImageIcon(MENU_BTN_PIC);
		
		bkgImg = new ImageIcon(bkgImg.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH));
		btnImg = new ImageIcon(bkgImg.getImage().getScaledInstance(BTN_WIDTH,BTN_HEIGHT,Image.SCALE_SMOOTH)); 
		
		this.playBtn = new JButton("Play");
		this.playBtn.setHorizontalTextPosition(JButton.CENTER);
		this.playBtn.setIcon(btnImg);
		this.playBtn.setSize(BTN_WIDTH, BTN_HEIGHT);
		//this.playBtn.setBorder(BorderFactory.createEmptyBorder());
		//this.playBtn.setContentAreaFilled(false);
		
		this.puzzlesBtn = new JButton("Puzzles");
		this.puzzlesBtn.setIcon(btnImg);
		
		this.exitBtn = new JButton("Puzzles");
		this.exitBtn.setIcon(btnImg);
		
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		this.buttonPanel.add(playBtn);
		this.buttonPanel.add(Box.createRigidArea(new Dimension(0,BTN_GAP)));
		this.buttonPanel.add(puzzlesBtn);
		this.buttonPanel.add(Box.createRigidArea(new Dimension(0,BTN_GAP)));
		this.buttonPanel.add(exitBtn);
		this.buttonPanel.add(Box.createRigidArea(new Dimension(0,BTN_GAP)));
		
		this.setVisible(true);
		this.add(buttonPanel);
	}
	
}
