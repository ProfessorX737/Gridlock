import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BackBar extends JPanel {
	public final static int DEFAULT_HEIGHT = 25;
	public final static int DEFAULT_WIDTH = 200;

	private JLabel title;
	private JButton backButton;
	private int width;
	private int height;

	public BackBar(String title) {
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;
		this.setLayout(new BorderLayout(height,0));
		this.title = new JLabel();
		this.backButton = new JButton();
		
		this.backButton.setText("Back");
		this.title.setText(title);
		this.add(this.backButton, BorderLayout.WEST);
		this.add(this.title, BorderLayout.CENTER);
	}

	public BackBar(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.setLayout(new BorderLayout(height,0));
		this.title = new JLabel();
		this.backButton = new JButton();
		
		this.backButton.setText("Back");
		this.title.setText(title);
		this.add(this.backButton, BorderLayout.WEST);
		this.add(this.title, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(width,height));
	}
	
	public void setController(ActionListener al) {
		this.backButton.addActionListener(al);
	}
}
