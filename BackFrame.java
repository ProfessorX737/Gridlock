import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BackFrame extends JPanel {
	private JPanel topBar;
	private JLabel title;
	private JButton backButton;

	public BackFrame(String title, JComponent main, int topBarHeight) {
		this.setLayout(new BorderLayout(topBarHeight,topBarHeight));

		this.topBar = new JPanel();
		this.backButton = new JButton();
		this.title = new JLabel();
		
		this.backButton.setText("Back");
		this.title.setText(title);

		topBar.setLayout(new BorderLayout(topBarHeight,0));
		topBar.add(backButton, BorderLayout.WEST);
		topBar.add(this.title, BorderLayout.CENTER);
		topBar.setSize(new Dimension(main.getWidth(),topBarHeight));
		this.add(topBar, BorderLayout.PAGE_START);
		this.add(main, BorderLayout.CENTER);

		this.setSize(new Dimension(main.getWidth(),main.getHeight() + topBarHeight));
	}
}
