import java.io.Serializable;
import java.util.Map;

public class MoveState implements Serializable{
	//required for saving the file
	private static final long serialVersionUID = 1L;
	private int[][] gameBoard;
    private Map<Integer, Vehicle> vehicleMap;

    public MoveState(int[][] gameBoard, Map<Integer, Vehicle> vehicleMap) {
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
