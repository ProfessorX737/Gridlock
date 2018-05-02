import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represent the current board of the game
 */
public class Board {
	//sizeRow and sizeCol keeps track of the size of the board
	private int sizeRow;
	private int sizeCol;
	private int exitRow;
	private int exitCol;
	//vehicleMap is a map of all the vehicles on the board with their ID as the key.
	private Map<Integer, Vehicle> vehicleMap;
	//a matrix which represents the board. Where their ID represents their location on the board
	private int[][] board;

	/**
	 * Constructor for the board, when only the size of the board is provided.
	 * @param sizeRow, the number of rows in the board
	 * @param sizeCol, the number of columns in the board
	 */
	public Board(int sizeRow, int sizeCol, int exitRow, int exitCol) {
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
	public Board(int sizeRow, int sizeCol, int exitRow, int exitCol, Map<Integer, Vehicle> vehicleMap) {
		this.sizeRow = sizeRow;
		this.sizeCol = sizeCol;
		this.exitRow = exitRow;
		this.exitCol = exitCol;
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
	public Board(int sizeRow, int sizeCol, int exitRow, int exitCol, Map<Integer, Vehicle> vehicleMap, int[][] board) {
		this.sizeRow = sizeRow;
		this.sizeCol = sizeCol;
		this.vehicleMap = vehicleMap;
		this.board = board;
	}
	
	/**
	 * Add vehicle to the board.
	 * @param isVertical
	 * @param length
	 * @param row
	 * @param col
	 * @param color
	 */
	public void addVehicle(boolean isVertical, int length, int row, int col, String color) {
		int id = vehicleMap.size();
		Vehicle vehicle = new Vehicle(id, isVertical, length, row, col, color);
		vehicleMap.put(id, vehicle);
		Collection<Point> takenPos = vehicle.getTakenPos();
		for(Point point : takenPos) {
			board[(int)point.getX()][(int)point.getY()] = id;
		}
	}

	/**
	 * @pre row < this.sizeRow, col < this.sizeCol
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOccupied(int row, int col) {
		if (board[row][col] == -1) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isOutOfBounds(int row, int col) {
		if(row < this.sizeRow && col < this.sizeCol && row >= 0 && col >= 0) {
			return false;
		}
		return true;
	}
	
	public int canMoveUp(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(!v.getIsVertical()) return 0;
		int row = v.getRow() - 1;
		int col = v.getCol();
		int numLegal = 0;
		while(!this.isOutOfBounds(row, col)) {
			if(this.isOccupied(row,col)) break;
			row--;
			numLegal++;
		}
		return numLegal;
	}

	public int canMoveDown(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(!v.getIsVertical()) return 0;
		int row = v.getRow() + v.getLength();
		int col = v.getCol();
		int numLegal = 0;
		while(!this.isOutOfBounds(row, col)) {
			if(this.isOccupied(row, col)) {
				break;
			}
			row++;
			numLegal++;
		}
		return numLegal;
	}
	
	public int canMoveLeft(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(v.getIsVertical()) return 0;
		int row = v.getRow();
		int col = v.getCol() - 1;
		int numLegal = 0;
		while(!this.isOutOfBounds(row, col)) {
			if(this.isOccupied(row, col)) break;
			col--;
			numLegal++;
		}
		return numLegal;
	}

	public int canMoveRight(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(v.getIsVertical()) return 0;
		int row = v.getRow();
		int col = v.getCol() + v.getLength();
		int numLegal = 0;
		while(!this.isOutOfBounds(row, col)) {
			if(this.isOccupied(row, col)) break;
			col++;
			numLegal++;
		}
		return numLegal;
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
	public void moveVehicle(int id, int newRow, int newCol) {
		//Remove from current spot
		Vehicle vehicle = vehicleMap.get(id);
		for (Point point : vehicle.getTakenPos()) {
			board[(int)point.getX()][(int)point.getY()] = -1;
		}
		//Update position
		vehicle.setPos(newRow, newCol);
		//Update board
		for (Point point : vehicle.getTakenPos()) {
			board[(int)point.getX()][(int)point.getY()] = id;
		}
	}
	
	public boolean isSolved() {
		Vehicle vehicle = vehicleMap.get(0);
		Collection<Point> points = vehicle.getTakenPos();
		return points.contains(new Point(exitRow, exitCol));
	}
	
	public int getVehicleAtLocation(int row, int col) {
		return board[row][col];
	}
	
	public Vehicle getMainVehicle() {
		return vehicleMap.get(0);
	}
	
	public Collection<Vehicle> getVehicles() {
		return vehicleMap.values();
	}
	
	//Used for printing out to the console
	private final static String red_car = "r";
	private final static String road = "-";
	private final static String wall = "W";

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
