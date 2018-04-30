import java.awt.Point;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * represent the current board of the game
 */
public class Board {
	private int sizeRow;
	private int sizeCol;
	private Map<Integer, Vehicle> vehicleMap;
	private int[][] board;

	static String red_car = "🚗";
	static String road = "⬛";
	static String wall = "🚧";

	public Board(int sizeRow, int sizeCol, Map<Integer, Vehicle> vehicleMap) {
		this.sizeRow = sizeRow;
		this.sizeCol = sizeCol;
		this.vehicleMap = vehicleMap;//need to fix this as it requires copy constructor
		board = new int[sizeRow][sizeCol];
		for (int y = 0; y < sizeRow; y++) {
			for (int x = 0; x < sizeCol; x++) {
				board[y][x]= -1;
			}
		}
		for (Map.Entry<Integer, Vehicle> entry : vehicleMap.entrySet()) {
			Collection<Point> takenPos = entry.getValue().getTakenPos();
			for(Point point : takenPos) {
				board[(int)point.getX()][(int)point.getY()] = entry.getKey();
			}
		}
	}
	
	public boolean isOccupied(int row, int col) {
		if (board[row][col] == -1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check whether the move is valid given vehicle id, direction and distance of move
	 * Distance is 0-North, 1-East, 2-South, 3-West
	 * @param id
	 * @param direction
	 * @param distance
	 */
	public boolean checkMove(int id, int direction, int distance) {
		Vehicle vehicle = vehicleMap.get(id);
		//Check that the direction is correct
		if (direction % 2 == 0 && vehicle.getIsVertical() == false) {
			return false;
		} else if (direction % 2 == 1 && vehicle.getIsVertical() == true) {
			return false;
		}
		//Check that it's within bounds
		int row = vehicle.getRow();
		int col = vehicle.getCol();
		int length = vehicle.getLength();
			switch(direction) {
				case 0:
					if (row - distance < 0) return false;
					break;
				case 1:
					if (col + length + distance > sizeCol) return false;
					break;
				case 2:
					if (row + length + distance > sizeRow) return false;
					break;
				case 3:
					if (col - distance < 0) return false;
					break;
			}
		//Check space is unoccupied
		for (int i = 1; i <= distance; i++) {
			switch(direction) {
				case 0:
					if (isOccupied(row - i, col) == false) return false;
					break;
				case 1:
					if (isOccupied(row, col + length + i) == false) return false;
					break;
				case 2:
					if (isOccupied(row + length + i, col) == false) return false;
					break;
				case 3:
					if (isOccupied(row, col - i) == false) return false;
					break;
			}
		}
		return true;
	}
	
	/**
	 * To move a vehicle specify the vehicle, direction and distance.
	 * @pre checkMove(id, direction, distance) == true
	 * @param id
	 * @param direction
	 */
	public void moveVehicle(int id, int direction, int distance) {
		//Remove from current spot
		Vehicle vehicle = vehicleMap.get(id);
		for (Point point : vehicle.getTakenPos()) {
			board[(int)point.getX()][(int)point.getY()] = -1;
		}
		//Update position
		int row = vehicle.getRow();
		int col = vehicle.getCol();
		switch(direction) {
			case 0:
				vehicle.setPos(row - distance, col);
				break;
			case 1:
				vehicle.setPos(row, col + distance);
				break;
			case 2:
				vehicle.setPos(row + distance, col);
				break;
			case 3:
				vehicle.setPos(row, col - distance);
				break;
		}
		//Update board
		for (Point point : vehicle.getTakenPos()) {
			board[(int)point.getX()][(int)point.getY()] = id;
		}
	}
	
	void showBoard() {
		for (int y = -1; y <= sizeRow; y++) {
			for (int x = -1; x <= sizeCol; x++) {
				if (y == -1 || y == sizeRow || x == -1 || x == sizeCol) {
					System.out.print(wall + "\t");
				} else if (board[y][x] == 0) {
					System.out.print(red_car + "\t");
				} else if (board[y][x] == -1) {
					System.out.print(road + "\t");
				} else {
					System.out.print(board[y][x] + "\t");
				}
			}
			System.out.println("");
		}
	}

}
