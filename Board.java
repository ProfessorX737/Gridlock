import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements ActionListener{

	private Timer timer;
	
	private Map m;

	public Board() {
		
		m = new Map();
		
		timer = new Timer(25, this);
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		
		for(int y = 0; y < 6; y++) {
			for(int x = 0; x < 6; x++) {
				if(m.getMap(x, y).equals("c")) {
					g.drawImage(m.getCar(), x * 32, y * 32, null);
				}
				if(m.getMap(x, y).equals("0")) {
					g.drawImage(m.getVehicle(), x * 32, y * 32, null);
				}
			}
		}
		
	}
}
