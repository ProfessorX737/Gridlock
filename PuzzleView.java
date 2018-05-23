import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.Map;

/**
 * The V in the MVC, creates all the visual vehicle components from the vehicles stored in the game
 *
 * @author ProfessorX
 */
public class PuzzleView extends JPanel implements Board{
    private PuzzleGame puzzleGame;
    private int width;
    private int height;
    private int cellLength;
    private Map<Integer, JComponent> vehicles;
    public static final int DEFAULT_CELL_SIZE = 50;

    public PuzzleView(PuzzleGame puzzleGame) {
        this.setLayout(null);
        this.puzzleGame = puzzleGame;
        this.cellLength = DEFAULT_CELL_SIZE;
        this.vehicles = new HashMap<>();
        this.setBackground(Color.white);
        this.updateSize(this.cellLength);
    }

    public PuzzleView(PuzzleGame puzzleGame, int cellLength) {
        this.setLayout(null);
        this.puzzleGame = puzzleGame;
        this.cellLength = cellLength;
        this.vehicles = new HashMap<>();
//        this.setBackground(Color.white);
        this.setLayout(null);
        this.updateSize(this.cellLength);
        this.setOpaque(false);
    }

    @Override
    public void updateSize(int cellSize) {
        this.cellLength = cellSize;
        this.width = (puzzleGame.getNumCols() + 0) * this.cellLength;
        this.height = (puzzleGame.getNumRows() + 0) * this.cellLength;
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.removeAll();
        draw();
    }


    @Override
    public void draw() {
        this.vehicles.clear();
        ImageIcon block;
        for (Vehicle v : puzzleGame.getVehicles()) {
            System.out.println("Drawing vehicle");
            //JComponent dc = new JPanel();
            int vWidth = this.cellLength;
            int vHeight = this.cellLength;
            if (v.getIsVertical()) {
                vHeight = this.cellLength * v.getLength();
                if (v.getMain()) block = new ImageIcon(this.getClass().getResource("Assets/vMainBlock.png"));
                else block = new ImageIcon(this.getClass().getResource("Assets/vBlock.png"));
            } else {
                if (v.getMain()) block = new ImageIcon(this.getClass().getResource("Assets/hMainBlock.png"));
                else block = new ImageIcon(this.getClass().getResource("Assets/hBlock.png"));
                vWidth = this.cellLength * v.getLength();
            }
            block = new ImageIcon(block.getImage().getScaledInstance(vWidth, vHeight, Image.SCALE_SMOOTH));
            //JLabel test = new JLabel("", block, JLabel.CENTER);
            JComponent dc = new JLabel("", block, JLabel.CENTER);
            // test.setSize(new Dimension(vWidth, vHeight));
            // test.setBounds(v.getCol() * this.cellLength, v.getRow() * this.cellLength, vWidth, vHeight);
            // this.add(test);
            dc.setPreferredSize(new Dimension(vWidth, vHeight));
            //dc.setBackground(v.getColor());
            //dc.setBorder(new LineBorder(Color.black, 2));
            dc.setBounds((v.getCol() + 0) * this.cellLength, (v.getRow() + 0) * this.cellLength, vWidth, vHeight);
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

    public Map<Integer, JComponent> getVehicles() {
        return vehicles;
    }

    public PuzzleGame getPuzzleGame() {
        return puzzleGame;
    }
}
