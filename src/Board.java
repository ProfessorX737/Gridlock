import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represent the current board of the game
 */
public class Board {
	//sizeRow and sizeCol keeps track of the size of the board
	private int sizeRow;
	private int sizeCol;
	//vehicleMap is a map of all the vehicles on the board with their ID as the key.
	private Map<Integer, Vehicle> vehicleMap;
	//a matrix which represents the board. Where their ID represents their location on the board
	private int[][] board;

	/**
	 * Constructor for the board, when only the size of the board is provided.
	 * @param sizeRow, the number of rows in the board
	 * @param sizeCol, the number of columns in the board
	 */
	public Board(int sizeRow, int sizeCol) {
		this.sizeRow = sizeRow;
		this.sizeCol = sizeCol;
		this.vehicleMap = new HashMap<Integer, Vehicle>();
		this.board = new int[sizeRow][sizeCol];
		for (int y = 0; y < sizeRow; y++) {
			for (int x = 0; x < sizeCol; x++) {
				board[y][x]= -1;
			}
		}
	}

	/**
	 * Constructor for the board, when the size of the board and map of vehicles is provided
	 * @param sizeRow, the number of rows in the board
	 * @param sizeCol, the number of columns in the board
	 * @param vehicleMap, a map of all the vehicles on the board
	 */
	public Board(int sizeRow, int sizeCol, Map<Integer, Vehicle> vehicleMap) {
		this.sizeRow = sizeRow;
		this.sizeCol = sizeCol;
		this.vehicleMap = vehicleMap;//does this work
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
	
	/**
	 * Constructor for the board, when the size of the board, map of the vehicles and
	 * a board is provided. Assumes the map of vehicles is consistent with the board.
	 * @param sizeRow, the number of rows in the board
	 * @param sizeCol, the number of columns in the board
	 * @param vehicleMap, a map of all the vehicles on the board with the ID as the key
	 * @param board, the representation of the vehicles on a matrix
	 */
	public Board(int sizeRow, int sizeCol, Map<Integer, Vehicle> vehicleMap, int[][] board) {
		this.sizeRow = sizeRow;
		this.sizeCol = sizeCol;
		this.vehicleMap = vehicleMap;
		this.board = board;
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
	
	//Used for printing out to the console
	private final static String red_car = "🚗";
	private final static String road = "⬛";
	private final static String wall = "🚧";
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
