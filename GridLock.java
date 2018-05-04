import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class GridLock{
	public static void main(String args[]) {
		JFrame f = new JFrame("GridLock");
		f.setLayout(new BorderLayout());
		
		f.setPreferredSize(new Dimension(400,300));
		f.setLayout(new BorderLayout());
        f.add(new GridlockBoard(), BorderLayout.CENTER);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
