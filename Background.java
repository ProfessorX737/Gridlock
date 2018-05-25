import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Background extends JPanel {
	private Image background;

	public Background(String filename, int width, int height) throws IOException {
        ImageIcon bkgIcon = new ImageIcon(this.getClass().getResource(filename));
		background = bkgIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
	}
	
	public Background(String filename) throws IOException {
        ImageIcon bkgIcon = new ImageIcon(this.getClass().getResource(filename));
        this.background = bkgIcon.getImage();
	}
		
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.background,0,0,this);
	}
}
