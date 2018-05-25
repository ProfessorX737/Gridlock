import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame {
	private JPanel buttonPanel;
	private JButton playBtn;
	private JButton puzzlesBtn;
	private JButton multiplayerBtn;
	private JButton exitBtn;
	
	public static final int BTN_WIDTH = 100;
	public static final int BTN_HEIGHT = 30;
	public static final String MENU_BKG_PIC = "pictures/MainMenu_bg.png";
	public static final String MENU_BTN_PIC = "pictures/MainMenu_bt.png";
	
	public static final int HEIGHT = PuzzleView.DEFAULT_CELL_SIZE * GridlockGame.NUM_LEVELS + BackBar.DEFAULT_HEIGHT;
	public static final int WIDTH = HEIGHT + ButtonPanel.WIDTH;
	
	public MainMenu() {
		this.setTitle("Gridlock");
		this.setLayout(new BorderLayout());
        ImageIcon btnImg = new ImageIcon(this.getClass().getResource(MENU_BTN_PIC));
		btnImg = new ImageIcon(btnImg.getImage().getScaledInstance(BTN_WIDTH,BTN_HEIGHT,Image.SCALE_SMOOTH)); 
		
		try {
			this.setContentPane(new Background(MENU_BKG_PIC, WIDTH, HEIGHT));
		} catch(IOException e) {
			System.out.println("Could not open menu background image");
		}

		JLabel gameName = new JLabel("Gridlock");
		gameName.setFont(new Font(Font.SERIF,Font.PLAIN,30));

		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 30;
		this.buttonPanel.add(gameName, c);
		
		c.ipady = 2;
		this.playBtn = new JButton("Play");
		this.playBtn.setIcon(btnImg);
		this.playBtn.setActionCommand("play");
		this.playBtn.setBackground(Color.lightGray);
		this.playBtn.setHorizontalTextPosition(JButton.CENTER);
		this.playBtn.setBorder(BorderFactory.createEmptyBorder());
		this.playBtn.setContentAreaFilled(false);
		c.gridy = 1;
		this.buttonPanel.add(playBtn,c);
		
		this.puzzlesBtn = new JButton("Puzzles");
		this.puzzlesBtn.setIcon(btnImg);
		this.puzzlesBtn.setActionCommand("puzzles");
		this.puzzlesBtn.setBackground(Color.lightGray);
		this.puzzlesBtn.setHorizontalTextPosition(JButton.CENTER);
		this.puzzlesBtn.setBorder(BorderFactory.createEmptyBorder());
		this.puzzlesBtn.setContentAreaFilled(false);
		c.gridy = 2;
		this.buttonPanel.add(puzzlesBtn,c);
		
		this.multiplayerBtn = new JButton("Multiplayer");
		this.multiplayerBtn.setIcon(btnImg);
		this.multiplayerBtn.setActionCommand("multiplayer");
		this.multiplayerBtn.setHorizontalTextPosition(JButton.CENTER);
		this.multiplayerBtn.setBorder(BorderFactory.createEmptyBorder());
		this.multiplayerBtn.setContentAreaFilled(false);
		c.gridy = 3;
		this.buttonPanel.add(this.multiplayerBtn, c);
		
		this.exitBtn = new JButton("Exit");
		this.exitBtn.setIcon(btnImg);
		this.exitBtn.setActionCommand("exit");
		this.exitBtn.setBackground(Color.LIGHT_GRAY);
		this.exitBtn.setHorizontalTextPosition(JButton.CENTER);
		this.exitBtn.setBorder(BorderFactory.createEmptyBorder());
		this.exitBtn.setContentAreaFilled(false);
		c.gridy = 4;
		this.buttonPanel.add(exitBtn,c);
		
		this.buttonPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.buttonPanel.setOpaque(false);
		this.add(buttonPanel,BorderLayout.CENTER);
		
		this.setLocationRelativeTo(null);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setController(ActionListener al) {
		this.playBtn.addActionListener(al);
		this.puzzlesBtn.addActionListener(al);
		this.exitBtn.addActionListener(al);
		this.multiplayerBtn.addActionListener(al);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
