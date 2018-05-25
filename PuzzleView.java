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
    private ImageIcon vMainBlock;
    private ImageIcon hMainBlock;
    private ImageIcon vBlock;
    private ImageIcon hBlock;

    public static final int DEFAULT_CELL_SIZE = 50;
    public static final String VERTICAL_MAIN_BLOCK = "Assets/vMainBlock.png";
    public static final String HORIZONTAL_MAIN_BLOCK = "Assets/hMainBlock.png";
    public static final String VERTICAL_BLOCK = "Assets/vBlock.png";
    public static final String HORIZONTAL_BLOCK = "Assets/hBlock.png";

    public PuzzleView(PuzzleGame puzzleGame) {
        this.setLayout(null);
        this.puzzleGame = puzzleGame;
        this.cellLength = DEFAULT_CELL_SIZE;
        this.vehicles = new HashMap<>();
        this.setBackground(Color.white);
        this.vMainBlock = new ImageIcon(this.getClass().getResource(VERTICAL_MAIN_BLOCK));
        this.hMainBlock = new ImageIcon(this.getClass().getResource(HORIZONTAL_MAIN_BLOCK));
        this.vBlock = new ImageIcon(this.getClass().getResource(VERTICAL_BLOCK));
        this.hBlock = new ImageIcon(this.getClass().getResource(HORIZONTAL_BLOCK));
        this.updateSize(this.cellLength);
        this.setOpaque(false);
    }

    public PuzzleView(PuzzleGame puzzleGame, int cellLength) {
        this.setLayout(null);
        this.puzzleGame = puzzleGame;
        this.cellLength = cellLength;
        this.vehicles = new HashMap<>();
//        this.setBackground(Color.white);
        this.setLayout(null);
        this.vMainBlock = new ImageIcon(this.getClass().getResource(VERTICAL_MAIN_BLOCK));
        this.hMainBlock = new ImageIcon(this.getClass().getResource(HORIZONTAL_MAIN_BLOCK));
        this.vBlock = new ImageIcon(this.getClass().getResource(VERTICAL_BLOCK));
        this.hBlock = new ImageIcon(this.getClass().getResource(HORIZONTAL_BLOCK));
        this.updateSize(this.cellLength);
        this.setOpaque(false);
    }

    @Override
    public void updateSize(int cellSize) {
        this.cellLength = cellSize;
        this.width = puzzleGame.getNumCols() * this.cellLength;
        this.height = puzzleGame.getNumRows() * this.cellLength;
        this.setSize(this.width,this.height);
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.removeAll();
        draw();
    }


    @Override
    public void draw() {
        this.vehicles.clear();
        ImageIcon block;
        for (Vehicle v : puzzleGame.getVehicles()) {
            int vWidth = this.cellLength;
            int vHeight = this.cellLength;
            if (v.getIsVertical()) {
                vHeight = this.cellLength * v.getLength();
                if(v.getID() == 0) block = this.vMainBlock;
                else block = this.vBlock;
            } else {
                vWidth = this.cellLength * v.getLength();
                if(v.getID() == 0) block = this.hMainBlock;
                else block = this.hBlock;
            }
            block = new ImageIcon(block.getImage().getScaledInstance(vWidth, vHeight, Image.SCALE_SMOOTH));
            JComponent dc = new JLabel("", block, JLabel.CENTER);
            dc.setPreferredSize(new Dimension(vWidth, vHeight));
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

    public Map<Integer, JComponent> getVehicles() {
        return vehicles;
    }

    public PuzzleGame getPuzzleGame() {
        return puzzleGame;
    }
}
