import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * The V in the MVC, creates all the visual vehicle components from the vehicles stored in the game
 *
 * @author ProfessorX
 */
public class PuzzleView extends JPanel {
    private PuzzleGame puzzleGame;
    private int width;
    private int height;
    private int cellLength;
    private Map<Integer, JComponent> vehicles;

    public PuzzleView(PuzzleGame puzzleGame, int cellLength) {
        this.setLayout(null);
        this.puzzleGame = puzzleGame;
        this.cellLength = cellLength;
        this.vehicles = new HashMap<>();
        this.setBackground(Color.white);
        this.updateSize(this.cellLength);
    }

    public void updateSize(int cellLength){
        this.cellLength = cellLength;
        this.width = puzzleGame.getNumCols() * this.cellLength;
        this.height = puzzleGame.getNumRows() * this.cellLength;
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setSize(new Dimension(this.width, this.height));
        this.removeAll();
        this.vehicles.clear();
        for (Vehicle v : puzzleGame.getVehicles()) {
            JComponent dc = new JPanel();
            int vWidth = this.cellLength;
            int vHeight = this.cellLength;
            if (v.getIsVertical()) {
                vHeight = this.cellLength * v.getLength();
            } else {
                vWidth = this.cellLength * v.getLength();
            }
            dc.setPreferredSize(new Dimension(vWidth, vHeight));
            dc.setBackground(v.getColor());
            dc.setBorder(new LineBorder(Color.white, 2));
            dc.setBounds(v.getCol() * this.cellLength, v.getRow() * this.cellLength, vWidth, vHeight);
            this.vehicles.put(v.getID(), dc);
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

    public Map<Integer,JComponent> getVehicles() {
        return vehicles;
    }
}
