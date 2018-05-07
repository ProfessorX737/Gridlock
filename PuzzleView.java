import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JComponent;

/**
 * The V in the MVC, creates all the visual vehicle components from the vehicles stored in the game
 * @author ProfessorX
 *
 */
public class PuzzleView extends JPanel {
	private PuzzleGame puzzleGame;
	private int width;
	private int height;
	private int cellLength;
	private Map<Integer,JComponent> vehicles;

	public PuzzleView(PuzzleGame puzzleGame, int cellLength) {
		this.puzzleGame = puzzleGame;
		this.cellLength = cellLength;
		this.width = puzzleGame.getNumCols() * cellLength;
		this.height = puzzleGame.getNumRows() * cellLength;
		this.vehicles = new HashMap<>();
		this.setLayout(null);
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.setBackground(Color.white);

		for(Vehicle v : puzzleGame.getVehicles()) {
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
