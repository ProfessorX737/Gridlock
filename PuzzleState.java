import javax.swing.tree.TreeNode;
import java.util.Collection;
import java.util.Map;

public class PuzzleState {
    private int [][] gameBoard;
    private Map<Integer, Vehicle> vehicleMap;

    public PuzzleState(int[][] gameBoard, Map<Integer, Vehicle> vehicleMap) {
        this.gameBoard = gameBoard;
        this.vehicleMap = vehicleMap;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public Map<Integer, Vehicle> getVehicleMap() {
        return vehicleMap;
    }
}
