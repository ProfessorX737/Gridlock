import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;

public class MenuButtons extends JPanel {
	private final JButton playButton;
	private final JButton multiplayerBtn;
	private final JButton exitBtn;
	private final JLabel title;
	private int width;
	private int height;
	private Image background;

	public MenuButtons() {
		this.width = PuzzleView.DEFAULT_CELL_SIZE * 11;
		this.height = PuzzleView.DEFAULT_CELL_SIZE * 8;
		this.setPreferredSize(new Dimension(this.width,this.height));
		setOpaque(false);
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createEmptyBorder());

		ImageIcon buttonIcon = new ImageIcon(this.getClass().getResource("Assets/button.png"));
		buttonIcon = new ImageIcon(buttonIcon.getImage().getScaledInstance(PuzzleView.DEFAULT_CELL_SIZE * 3, PuzzleView.DEFAULT_CELL_SIZE, Image.SCALE_SMOOTH));

		ImageIcon label = new ImageIcon(this.getClass().getResource("Assets/textBg.png"));
		label = new ImageIcon(label.getImage().getScaledInstance(PuzzleView.DEFAULT_CELL_SIZE * 3, (int) Math.round(PuzzleView.DEFAULT_CELL_SIZE * 1.5), Image.SCALE_SMOOTH));

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5, 0, 5, 0);

		title = new JLabel(label);
		title.setText("Gridlock");
		title.setFont(new Font("Arial", Font.PLAIN, PuzzleView.DEFAULT_CELL_SIZE/(5/2)));
		title.setForeground(new Color(128,0,0));
		title.setHorizontalTextPosition(JLabel.CENTER);
		title.setVerticalTextPosition(JLabel.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder());
		this.add(title, c);
		c.gridy = 1;

		playButton = new JButton("Play", buttonIcon);
		playButton.setActionCommand("play");
		playButton.setHorizontalTextPosition(JButton.CENTER);
		playButton.setVerticalTextPosition(JButton.CENTER);
		playButton.setBorder(BorderFactory.createEmptyBorder());
		playButton.setFont(new Font("Arial", Font.PLAIN, 14));
		playButton.setForeground(new Color(245,222,179));
		this.add(playButton,c);

		multiplayerBtn = new JButton("Multiplayer", buttonIcon);
		multiplayerBtn.setActionCommand("multiplayer");
		multiplayerBtn.setHorizontalTextPosition(JButton.CENTER);
		multiplayerBtn.setVerticalTextPosition(JButton.CENTER);
		multiplayerBtn.setBorder(BorderFactory.createEmptyBorder());
		multiplayerBtn.setFont(new Font("Arial", Font.PLAIN, 14));
		multiplayerBtn.setForeground(new Color(245,222,179));
		c.gridy = 2;
		this.add(multiplayerBtn,c);

		exitBtn = new JButton("Exit", buttonIcon);
		exitBtn.setActionCommand("exit");
		exitBtn.setHorizontalTextPosition(JButton.CENTER);
		exitBtn.setVerticalTextPosition(JButton.CENTER);
		exitBtn.setBorder(BorderFactory.createEmptyBorder());
		exitBtn.setFont(new Font("Arial", Font.PLAIN, 14));
		exitBtn.setForeground(new Color(245,222,179));
		c.gridy = 3;
		this.add(exitBtn,c);

		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentShown(ComponentEvent e) {

			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});
	}


		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon bg = new ImageIcon(this.getClass().getResource("Assets/board.png"));
		bg = new ImageIcon(bg.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
		g.drawImage(bg.getImage(),0,0,this);
	}

	public JButton getPlayButton() {
		return playButton;
	}

	public JButton getMultiplayerBtn() {
		return multiplayerBtn;
	}

	public JButton getExitBtn() {
		return exitBtn;
	}

	public JLabel getTitle() {
		return title;
	}
}
