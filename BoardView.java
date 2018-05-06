import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JComponent;

public class BoardView extends JPanel {
	private Game game;
	private int width;
	private int height;
	private int cellLength;
	private Map<Integer,JComponent> vehicles;

	public BoardView(Game game, int cellLength) {
		this.game = game;
		this.cellLength = cellLength;
		this.width = game.getNumCols() * cellLength;
		this.height = game.getNumRows() * cellLength;
		this.vehicles = new HashMap<>();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(this.width, this.height));
		//this.setSize(new Dimension(this.width, this.height));
		this.setBackground(Color.white);

		for(Vehicle v : game.getVehicles()) {
			JComponent dc = new JPanel();
			int vWidth = this.cellLength;
			int vHeight = this.cellLength;
			if(v.getIsVertical()) {
				vHeight = this.cellLength * v.getLength();
			} else {
				vWidth = this.cellLength * v.getLength();
			}
			dc.setPreferredSize(new Dimension(vWidth,vHeight));
			dc.setBackground(v.getColor());
			dc.setBorder(new LineBorder(Color.black, 2));
			dc.setBounds(v.getCol() * this.cellLength, v.getRow() * this.cellLength, vWidth, vHeight);
			this.vehicles.put(v.getID(),dc);
			this.add(dc);
		}
	}

	public void setController(MouseAdapter controller) {
		this.addMouseListener(controller);
		this.addMouseMotionListener(controller);
	}
	
	public int getCellLength() {
		return this.cellLength;
	}
	
	public void setVehicleLocation(int id, int x, int y) {
		this.vehicles.get(id).setLocation(x, y);
	}
	
	public JComponent getVehicle(int id) {
		return this.vehicles.get(id);
	}
}
