import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

/**
 * Represents the current state of the game
 */
public class PuzzleGame implements Serializable {
	//puzzle identification
	private int id;
	//required for saving the puzzle
	private static final long serialVersionUID = 1L;
	//sizeRow and sizeCol keeps track of the size of the board
	private int sizeRow;
	private int sizeCol;
	private int exitRow;
	private int exitCol;
	//vehicleMap is a map of all the vehicles on the board with their ID as the key.
	private Map<Integer, Vehicle> vehicleMap;
	//a matrix which represents the board. Where their ID represents their location on the board
	private int[][] board;
	//possible size of vehicles, or should this be added to vehicle class
	private static final int[] vehicleSize = new int[] {2, 3};
	//minimum moves to solve is the minimum required moves to solve this puzzle
	private int minMoves;
    //Used for printing out to the console
    // private PuzzleState puzzleState;
    private Stack<MoveState> undo;
    private Stack<MoveState> redo;
    private MoveState initialState;
    private int moves;
	private NetUIController nuic;

    /**
     * Constructor that only requires the size of the board.
     * @pre sizeRow > 0 && sizeCol > 0
     * @post board.size() == (sizeRow, sizeCol)
     * @param sizeRow height of the board
     * @param sizeCol width of the board
     */
    public PuzzleGame(int sizeRow, int sizeCol) {
		this.id = 0;
    		this.sizeRow = sizeRow;
    		this.sizeCol = sizeCol;
    		this.exitRow = 0;
    		this.exitCol = 0;
    		this.vehicleMap = new HashMap<>();
    		this.undo = new Stack<>();
    		this.redo = new Stack<>();
    		this.initBoard();
    		this.moves = 0;
    		this.minMoves= 0;
    }

	/**
	 * Constructor for the board, when only the size of the board is provided.
	 * @pre id > 0 && sizeRow > 0 && sizeCol > 0 && 0 <= exitRow < sizeRow && 0 <= exitCol < sizeCol
	 * @post board.size() == (sizeRow, sizeCol)
	 * @param sizeRow, the number of rows in the board
	 * @param sizeCol, the number of columns in the board
	 */
    public PuzzleGame(int id, int sizeRow, int sizeCol, int exitRow, int exitCol) {
		this.id = id;
        this.sizeRow = sizeRow;
        this.sizeCol = sizeCol;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.vehicleMap = new HashMap<>();
        this.undo = new Stack<>();
        this.redo = new Stack<>();
        this.initBoard();
        this.moves = 0;
		this.minMoves = 0;
    }

	/**
	 * Constructor for the board, when only the size of the board is provided.
	 * @pre id > 0 && sizeRow > 0 && sizeCol > 0 && 0 <= exitRow < sizeRow && 0 <= exitCol < sizeCol
	 * @post board.size() == (sizeRow, sizeCol)
	 * @param sizeRow, the number of rows in the board
	 * @param sizeCol, the number of columns in the board
	 */
    public PuzzleGame(int sizeRow, int sizeCol, int exitRow, int exitCol) {
		this.id = 0;
        this.sizeRow = sizeRow;
        this.sizeCol = sizeCol;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.vehicleMap = new HashMap<>();
        this.undo = new Stack<>();
        this.redo = new Stack<>();
        this.initBoard();
        this.moves = 0;
		this.minMoves = 0;
    }

	/**
	 * Copy constructor for puzzleGame
	 * @pre g != null
	 * @post true
	 * @param g, the Puzzle game to copy
	 */
	public PuzzleGame(PuzzleGame g) {
		this.sizeRow = g.sizeRow;
		this.sizeCol = g.sizeCol;
		this.exitRow = g.exitRow;
		this.exitCol = g.exitCol;
		this.vehicleMap = new HashMap<>();
		for(Vehicle v : g.vehicleMap.values()) {
			this.vehicleMap.put(v.getID(), new Vehicle(v));
		}
		this.board = this.cloneBoard(g.board);
		this.minMoves = g.minMoves;
        this.undo = new Stack<>();
        this.redo = new Stack<>();
	}
	
	/**
	 * Initialize board with -1 on every tile
	 * @pre true
	 * @post true
	 */
	private void initBoard() {
		this.board = new int[sizeRow][sizeCol];
		for (int y = 0; y < sizeRow; y++) {
			for (int x = 0; x < sizeCol; x++) {
				board[y][x]= -1;
			}
		}
	}
	
	///**
	// * Check whether the vehicle can be added to the location.
	// * @pre v != null
	// * @post true
	// * @param vehicle to be tested
	// * @return true if the vehicle can be added otherwise false
	// */
	//public boolean canAddVehicle(Vehicle v) {
	//	boolean isVertical = v.getIsVertical();
	//	int length = v.getLength();
	//	int row = v.getRow();
	//	int col = v.getCol();
	//	return canAddVehicle(isVertical, length, row, col);
	//}
	
	/**
	 * Check whether the vehicle can be added to the location.
	 * @pre length > 0 && 0 <= row < height && 0 <= col < width
	 * @post true
	 * @param isVertical
	 * @param length
	 * @param row
	 * @param col
	 * @return true if a vehicle of the specified parameters can be added otherwise returns false
	 */
	public boolean canAddVehicle(boolean isVertical, int length, int row, int col) {
		if (isVertical){
			for (int i = 0; i < length; i++) {
				if(this.isOutOfBounds(row + i, col)) return false;
				if (isOccupied(row + i, col)) return false;
			}
		} else {
			for (int i = 0; i < length; i++) {
				if(this.isOutOfBounds(row, col + i)) return false;
				if (isOccupied(row, col + i)) return false;
			}
		}
		return true;
	}
	
	/**
	 * Add vehicle to the board. Adds by reference so don't change the vehicle after adding it,
	 * unless through GamePuzzle methods.
	 * @pre vehicle != null
	 * @post true
	 * @param vehicle
	 */
	public void addVehicle(Vehicle vehicle) {
		int id = vehicleMap.size();
		vehicle.setID(id);
		this.vehicleMap.put(id, vehicle);
		this.fillVehicleSpace(vehicle, id);
	}

	/**
	 * Check whether the current location is occupied.
	 * @pre 0 <= row < height && 0 <= col < width
	 * @pre true
	 * @param row
	 * @param col
	 * @return true if the current cell is occupied, also returns true if the cell does not exist
	 */
	public boolean isOccupied(int row, int col) {
		if(this.isOutOfBounds(row, col)) return true;
		if (board[row][col] == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check whether the location is out of bounds of the board.
	 * @pre true
	 * @post true
	 * @param row
	 * @param col
	 * @return true if the location is outside the bounds of the board
	 */
	public boolean isOutOfBounds(int row, int col) {
		if(row < this.sizeRow && col < this.sizeCol && row >= 0 && col >= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the number of spaces the vehicle and be moved up.
	 * @pre vehicleMap.contains(id)
	 * @post true
	 * @param id of the vehicle
	 * @return number of spaces the vehicle can be moved
	 */
	public int canMoveUp(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(!v.getIsVertical()) return 0;
		int row = v.getRow() - 1;
		int col = v.getCol();
		int numLegal = 0;
		while(!this.isOccupied(row, col)) {
			row--;
			numLegal++;
		}
		if(this.exitRow == 0 && id == 0 && row == -1) {
			return numLegal + 1;
		}
		return numLegal;
	}

	/**
	 * Returns the number of spaces the vehicle and be moved down.
	 * @pre vehicleMap.contains(id)
	 * @post true
	 * @param id of the vehicle
	 * @return number of spaces the vehicle can be moved
	 */
	public int canMoveDown(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(!v.getIsVertical()) return 0;
		int row = v.getRow() + v.getLength();
		int col = v.getCol();
		int numLegal = 0;
		while(!this.isOccupied(row, col)) {
			row++;
			numLegal++;
		}
		if(this.exitRow == this.sizeRow - 1 && id == 0 && row == this.sizeRow) {
			return numLegal + 1;
		}
		return numLegal;
	}
	
	/**
	 * Returns the number of spaces the vehicle and be moved left.
	 * @pre vehicleMap.contains(id)
	 * @post true
	 * @param id of the vehicle
	 * @return number of spaces the vehicle can be moved
	 */
	public int canMoveLeft(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(v.getIsVertical()) return 0;
		int row = v.getRow();
		int col = v.getCol() - 1;
		int numLegal = 0;
		while(!this.isOccupied(row, col)) {
			col--;
			numLegal++;
		}
		if(this.exitCol == 0 && id == 0 && col == -1) {
			return numLegal + 1;
		}
		return numLegal;
	}

	/**
	 * Returns the number of spaces the vehicle and be moved right.
	 * @pre vehicleMap.contains(id)
	 * @post true
	 * @param id of the vehicle
	 * @return number of spaces the vehicle can be moved
	 */
	public int canMoveRight(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(v.getIsVertical()) return 0;
		int row = v.getRow();
		int col = v.getCol() + v.getLength();
		int numLegal = 0;
		while(!this.isOccupied(row, col)) {
			col++;
			numLegal++;
		}
		if(this.exitCol == this.sizeCol - 1 && id == 0 && col == this.sizeCol) {
			return numLegal + 1;
		}
		return numLegal;
	}
	
	/**
	 * Fill the space occupied by the vehicle on the board, with the given id
	 * @pre v != null
	 * @post getVehicleIDAtLocation() == id
	 * @param v
	 * @param id
	 */
	private void fillVehicleSpace(Vehicle v, int id) {
		int row = v.getRow();
		int col = v.getCol();
		if(v.getIsVertical()) {
			for(int i = 0; i < v.getLength(); i++) {
				if(this.isOutOfBounds(row+i, col)) return;
				this.board[row+i][col] = id;
			}
		} else {
			for(int i = 0; i < v.getLength(); i++) {
				if(this.isOutOfBounds(row, col+i)) return;
				this.board[row][col+i] = id;
			}
		}
	}
	
	/**
	 * Check whether the current puzzle is solved
	 * @pre main car has id 0 and is added to the class
	 * @post true
	 * @return true if the main car has reached the exit otherwise return false
	 */
	public boolean isSolved() {
		if(this.board[exitRow][exitCol] == 0) {
			return true;
		}
		return false;
	}

//	private int[][] cloneBoard(int[][] board) {
//		int[][] clone = new int[this.sizeRow][];
//		for(int i = 0; i < this.sizeRow; i++) {
//			clone[i] = board[i].clone();
//		}
//		return clone;
//	}

	/**
	 * Returns clone of the board.
	 * @pre board != null
	 * @post true
	 * @param board
	 * @return
	 */
	private int[][] cloneBoard(int[][] board) {
		int[][] clone = new int[this.sizeRow][this.sizeCol];
		for(int i = 0; i < this.sizeRow; i++) {
			for(int k = 0; k < this.sizeCol; k++) {
				clone[i][k] = board[i][k];
			}
		}
		return clone;
	}
	
	///**
	// * Returns a random vehicle from the map of vehicles.
	// * Returns the reference to the vehicle.
	// * @pre vehicleMap.size() >= 1
	// * @post true
	// * @return
	// */
	//public Vehicle getRandomVehicle() {
	//	Random rand = new Random();
	//	return vehicleMap.get(rand.nextInt(vehicleMap.size()));
	//}
	
	/**
	 * Returns the vehicle given the vehicle ID.
	 * Returns the reference to the vehicle, modifying the vehicle will change the game.
	 * @pre vehicleMap.contains(id)
	 * @post true
	 * @param id, of the vehicle
	 * @return, reference to vehicle
	 */
	public Vehicle getVehicle(int id) {
		return vehicleMap.get(id);
	}
	
	/**
	 * Get the id of the vehicle at the location.
	 * @pre this.isOutOfBounds(row,col) == false
	 * @post true
	 * @return the vehicle id if the position, if not valid return -1
	 */
	public int getVehicleIDAtLocation(int row, int col) {
		if(!this.isOutOfBounds(row, col)) {
			return board[row][col];
		} 	
		return -1;
	}
	
	///**
	// * @pre this.isOutOfBounds(row,col) == false
	// * @pre board[row][col] is in this.vehicleMap.keys()
	// * @return 
	// */
	//public Vehicle getVehicleAtLocation(int row, int col) {
	//	return this.vehicleMap.get(board[row][col]);
	//}
	
	/**
	 * Get the vehicle orientation.
	 * @pre puzzle.containsVehicle(id) == true
	 * @post true
	 * @param id of the vehicle
	 * @return true if the vehicle is vertical otherwise false
	 */
	public boolean isVehicleVertical(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(v == null) return false;
		if(v.getIsVertical()) return true;
		return false;
	}
	
	/**
	 * Return the main vehicle on the board.
	 * @pre true
	 * @post true
	 * @return the main vehicle which has an id of 0
	 */
	public Vehicle getMainVehicle() {
		return vehicleMap.get(0);
	}
	
	///**
	// * Returns the row of the vehicle
	// * @pre vehicleMap.contains(id)
	// * @post true
	// * @param id, of the vehicle
	// * @return the row at which the vehicle head occupies
	// */
	//public int getVehicleRow(int id) {
	//	return vehicleMap.get(id).getRow();
	//}
	
	///**
	// * Return the row of the vehicle
	// * @pre vehicleMap.contains(id)
	// * @post true
	// * @param id, of the vehicle
	// * @return the column at which the vehicle head occupies
	// */
	//public int getVehicleCol(int id) {
	//	return vehicleMap.get(id).getCol();
	//}
	
	/**
	 * Returns a list of all the vehicles on the board.
	 * @pre true
	 * @post true
	 * @return list of vehicles on the board.
	 */
	public Collection<Vehicle> getVehicles() {
		return vehicleMap.values();
	}
	
	/**
	 * Returns the height of the board
	 * @pre true
	 * @post true
	 * @return height of the board
	 */
	public int getNumRows() {
		return this.sizeRow;
	}
	
	/**
	 * Returns the width of the board
	 * @pre true
	 * @post true
	 * @return
	 */
	public int getNumCols() {
		return this.sizeCol;
	}
	
	/**
	 * Returns the board
	 * @pre true
	 * @post true
	 * @return
	 */
	public int[][] getBoard() {
		return this.board;
	}
	
	/**
	 * Returns the exit row of the board
	 * @pre true
	 * @post true
	 * @return
	 */
	public int getExitRow() {
		return this.exitRow;
	}
	
	/**
	 * Returns the exit column of the board
	 * @pre true
	 * @post true
	 * @return
	 */
	public int getExitCol() {
		return this.exitCol;
	}
	
	/**
	 * Get the minimum number of moves required to solve this board
	 * @pre true
	 * @post true
	 * @return 
	 */
	public int getMinMoves() {
		return this.minMoves;
	}
	
	/**
	 * Set the minimum number of moves required to solve the board
	 * @pre minMoves > 0
	 * @post true
	 * @param minMoves
	 */
	public void setMinMoves(int minMoves) {
		this.minMoves = minMoves;
	}
	
	/**
	 * Return the id of the board
	 * @pre true
	 * @post true
	 * @return
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Set the id of the board
	 * @pre id >= 0
	 * @post true
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Set the exit row of the board
	 * @pre 0 <= row < height
	 * @post true
	 * @param row
	 */
	public void setExitRow(int row) {
		this.exitRow = row;
	}
	
	/**
	 * Set the exit column of the board
	 * @pre 0 <= col < width
	 * @post true
	 * @param col
	 */
	public void setExitCol(int col) {
		this.exitCol = col;
	}
	
	//public int getVehicleMapSize() {
	//	return vehicleMap.size();
	//}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		result = prime * result + exitCol;
		result = prime * result + exitRow;
		result = prime * result + sizeCol;
		result = prime * result + sizeRow;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuzzleGame other = (PuzzleGame) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		if (exitCol != other.exitCol)
			return false;
		if (exitRow != other.exitRow)
			return false;
		if (sizeCol != other.sizeCol)
			return false;
		if (sizeRow != other.sizeRow)
			return false;
		return true;
	}

	//Used for printing out to the console
	private final static String red_car = "r";
	private final static String road = "-";
	private final static String wall = "W";

	/**
	 * Prints out the board onto the console.
	 * The red car is represented by r while other vehicles are represented by their id.
	 * @pre true
	 * @post true
	 */
	public void showBoard() {
		System.out.println("Exit at row: " + exitRow + ", col: " + exitCol);
		for(int i = -1; i < sizeCol; i++) {
			System.out.printf("%d\t",i);
		}
		System.out.println("");
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
			System.out.printf("%n%d",y+1);
		}
	}
	
	/**
	 * Remove vehicle from the location on the board.
	 * @pre 0 <= row < height && 0 <= col < width
	 * @post vehicleMap.contains(getVehicle(row,col)) == false
	 * @param row
	 * @param col
	 */
	public void removeVehicleAtLocation(int row, int col) {
		int id  = getVehicleIDAtLocation(row, col);
		if (id > -1) {
			removeVehicle(id);
		}
	}
	
	/**
	 * Remove vehicle from the vehicleMap and the board.
	 * @pre vehicleMap.contains(id)
	 * @post vehicmeMap.contains(id) == false
	 */
	public void removeVehicle(int id) {
		//remove from the board
		this.fillVehicleSpace(vehicleMap.get(id), -1);
		//remove from the map
		vehicleMap.remove(id);
	}
	
	///**
	// * This might need to be changed
	// * Will be very expensive if we had to recalculate this every time
	// * Generates all possible vehicle spaces
	// */
	//public List<Vehicle> getPossibleVehicle() {
	//	//number of empty spaces found so far
	//	int emptySpace = 0;
	//	//placeholderID which will be changed it it is the best choice
	//	int placeHolderID = 0xDEADBEEF;
	//	List<Vehicle> possibleVehicle = new ArrayList<Vehicle>();
	//	//exclude spaces that directly block the main vehicle
	//	boolean mainIsVertical = vehicleMap.get(0).getIsVertical();
	//	int mainRow = vehicleMap.get(0).getRow();
	//	int mainCol = vehicleMap.get(0).getCol();
	//	
	//	//go through the board vertically and find all spaces which are 2 or 3 in length
	//	for (int i = sizeCol - 1; i >= 0; i--) {
	//		for (int j = sizeRow - 1; j >= 0; j--) {
	//			if (mainIsVertical == true && mainCol == i) {
	//				//don't return vehicles that directly get in the way
	//				continue;
	//			}
	//			if (!isOccupied(j, i)) {
	//				emptySpace++;
	//				for (int k = 0; k < vehicleSize.length; k++) {
	//					if (emptySpace >= vehicleSize[k]) {
	//						possibleVehicle.add(new Vehicle(placeHolderID, true, vehicleSize[k], j, i, Color.ORANGE));
	//					}
	//				}
	//			} else {
	//				emptySpace = 0;
	//			}
	//		}
	//		emptySpace = 0;
	//	}
	//	
	//	//go through the board horizontally and find empty spaces
	//	emptySpace = 0;
	//	for (int j = sizeRow - 1; j >= 0 ; j--) {
	//		for (int i = sizeCol - 1; i >= 0; i--) {
	//			if (mainIsVertical == false && mainRow == j) {
	//				//don't return vehicles that directly get in the way
	//				continue;
	//			}
	//			if (!isOccupied(j, i)) {
	//				emptySpace++;
	//				for (int k = 0; k < vehicleSize.length; k++) {
	//					if (emptySpace >= vehicleSize[k]) {
	//						possibleVehicle.add(new Vehicle(placeHolderID, false, vehicleSize[k], j, i, Color.ORANGE));
	//					}
	//				}
	//			} else {
	//				emptySpace = 0;
	//			}
	//		}
	//		emptySpace = 0;
	//	}
	//	return possibleVehicle;
	//}
	
	/**
	 * Returns a list of possible vehicles that intersect with the path of the current vehicles.
	 * @pre true
	 * @post true
	 * @return
	 */
	public List<Vehicle> getPossibleIntersects() {
		Set<Vehicle> intersects = new HashSet<>();
		for(Vehicle v : this.vehicleMap.values()) {
			intersects.addAll(this.getPossibleIntersects(v.getID()));
		}
		return new ArrayList<Vehicle>(intersects);
	}

	/**
	 * Returns a set of possible vehicles that intersect with the path of the current vehicle
	 * @pre vehicleMap.contains(vID)
	 * @post true
	 * @param vId
	 * @return
	 */
	public Set<Vehicle> getPossibleIntersects(int vId) {
		Set<Vehicle> intersects = new HashSet<>();
		intersects.addAll(this.getPossibleIntersects(vId, 2));
		intersects.addAll(this.getPossibleIntersects(vId, 3));
		return intersects;
	}
	
	/**
	 * Returns a set of possible vehicles of give length that intersect with the path of the current vehicle
	 * @pre vehicleMap.contains(vID)
	 * @post true
	 * @param vId
	 * @param vLength
	 * @return
	 */
	public Set<Vehicle> getPossibleIntersects(int vId, int vLength) {
		Set<Vehicle> possibleVehicles = new HashSet<>();
		int newId = this.vehicleMap.size();
		Vehicle v = this.vehicleMap.get(vId);
		if(v.getIsVertical()) {
			for(int i = 1; i <= this.canMoveUp(vId); i++) {
				int row = v.getRow() - i;
				if(!isClearVerticalPath(row,v.getRow()-1,v.getCol())) break;
				for(int l = 0; l < vLength; l++) {
					int col = v.getCol() - l;
					if(this.canAddVehicle(false, vLength, row, col)) {
						possibleVehicles.add(new Vehicle(newId,false,vLength,row,col,Color.ORANGE));
					}
				}
			}
			int rowBelowV = v.getRow() + v.getLength();
			for(int i = 0; i < this.canMoveDown(vId); i++) {
				int row = rowBelowV + i;
				if(!isClearVerticalPath(rowBelowV,row,v.getCol())) break;
				for(int l = 0; l < vLength; l++) {
					int col = v.getCol() - l;
					if(this.canAddVehicle(false, vLength, row, col)) {
						possibleVehicles.add(new Vehicle(newId,false,vLength,row,col,Color.ORANGE));
					}
				}
			}
		} else {
			for(int i = 1; i <= this.canMoveLeft(vId); i++) {
				int col = v.getCol() - i;
				if(!isClearHorizontalPath(col,v.getCol()-1,v.getRow())) break;
				for(int l = 0; l < vLength; l++) {
					int row = v.getRow() - l;
					if(this.canAddVehicle(true, vLength, row, col)) {
						possibleVehicles.add(new Vehicle(newId,true,vLength,row,col,Color.ORANGE));
					}
				}
			}
			int colRightOfV = v.getCol() + v.getLength();
			for(int i = 0; i < this.canMoveRight(vId); i++) {
				int col = colRightOfV + i;
				if(!isClearHorizontalPath(colRightOfV,col,v.getRow())) break;
				for(int l = 0; l < vLength; l++) {
					int row = v.getRow() - l;
					if(this.canAddVehicle(true, vLength, row, col)) {
						possibleVehicles.add(new Vehicle(newId,true,vLength,row,col,Color.ORANGE));
					}
				}
			}
		}
		return possibleVehicles;
	}
	
	/**
	 * Checks whether the vertical path is clear
	 * @pre 0 <= fromRow < height && 0 <= toRow < height && 0 <= col < width
	 * @post true
	 * @param fromRow
	 * @param toRow
	 * @param col
	 * @return true if there is nothing from fromRow to toRow in that column otherwise returns false
	 */
	private boolean isClearVerticalPath(int fromRow, int toRow, int col) {
		for(int i = fromRow; i <= toRow; i++) {
			if(this.isOccupied(i, col)) return false;
		}
		return true;
	}

	/**
	 * Checks whether the horizontal path is clear
	 * @pre 0 <= fromCol < width && 0 <= toCol < width && 0 <= row < height
	 * @post true
	 * @param fromCol
	 * @param toCol
	 * @param row
	 * @return true if there is nothing from fromCol to toCol in the give row otherwise returns false
	 */
	private boolean isClearHorizontalPath(int fromCol, int toCol, int row) {
		for(int i = fromCol; i <= toCol; i++) {
			if(this.isOccupied(row, i)) return false;
		}
		return true;
	}

    /**
     * Add vehicle to the board.
     * @pre length > 0 && 0 <= row < height && 0 <= col < width
     * @param vehicleMap.contains(vehicleLocation(row,col)) == true
     * @param isVertical
     * @param length
     * @param row
     * @param col
     * @param main
     */
    public void addVehicle(boolean isVertical, int length, int row, int col, Color color){
        int id = vehicleMap.size();
        Vehicle v = new Vehicle(id, isVertical, length, row, col, color);
        this.vehicleMap.put(id, v);
        this.fillVehicleSpace(v, id);
    }

    /**
     * To move a vehicle specify the vehicle, direction and distance.
     * @param id
     * @pre checkMove(id, direction, distance) == true
     */
    public void moveVehicleState(int id, int newRow, int newCol) {
        // Todo currently clicking on the vehicle counts as a move
        // Need to ensure it is not
        Vehicle v = this.vehicleMap.get(id);
        if (v.getRow() != newRow || v.getCol() != newCol){
            redo.removeAllElements();
            undo.add(new MoveState(copyBoard(this.board), copyVehicleMap(this.vehicleMap)));
            this.fillVehicleSpace(v, -1);
            v.setPos(newRow, newCol);
            this.fillVehicleSpace(v, id);
            moves += 1;

			if (isSolved()) {
				System.out.println("PUZZLE SOLVED");
				System.out.println("PUZZLE SOLVED");
				System.out.println("PUZZLE SOLVED");
				System.out.println("PUZZLE SOLVED");
				//show the pop up
				int i = JOptionPane.showConfirmDialog(null, "You won the game in " + this.moves + " moves!\n" + "Continue to play？","Result:", JOptionPane.YES_NO_OPTION);
			    if (i==JOptionPane.OK_OPTION) {
			        System.out.println("play next puzzle");
					MainMenu menu = new MainMenu();
					GridlockGame game = new GridlockGame();
					MenuController menuContr = new MenuController(game,menu);
					menu.setController(menuContr);
					menuContr.goToMainMenu();
			    } else {
					System.out.println("exit");
					System.exit(0);
				}
				if (nuic != null) {
					nuic.puzzleDone();
				}
			}
        }
    }
    
    /**
     * Change the orientation of the specified vehicle
     * @pre vehicleMap.contains(id)
     * @post vehicleMap.getIsVertical(id) == isVertical
     * @param id
     * @param isVertical
     */
    public void changeIsVertical(int id, boolean isVertical) {
    		Vehicle v = this.vehicleMap.get(id);
    		this.fillVehicleSpace(v, -1);
    		v.setIsVertical(isVertical);
    		this.fillVehicleSpace(v, id);
    }

    /**
     * Move vehicle to new location.
     * @pre vehicleMap.contains(id)
     * @post vehicleMap.getVehicle(id).getLocation(newRow, newCol)  
     * @param id
     * @param newRow
     * @param newCol
     */
    public void moveVehicle(int id, int newRow, int newCol) {
        Vehicle v = this.vehicleMap.get(id);
		this.fillVehicleSpace(v, -1);
		v.setPos(newRow, newCol);
		this.fillVehicleSpace(v, id);

		if (isSolved()){
			System.out.println("PUZZLE SOLVED");
			System.out.println("PUZZLE SOLVED");
			System.out.println("PUZZLE SOLVED");
			System.out.println("PUZZLE SOLVED");
			if (nuic != null){
				nuic.puzzleDone();
			}
		}
    }

    //private void printBoard(int[][] board) {
    //    for (int y = -1; y <= sizeRow; y++) {
    //        for (int x = -1; x <= sizeCol; x++) {
    //            if (y == -1 || y == sizeRow || x == -1 || x == sizeCol) {
    //                System.out.print(wall + "\t");
    //            } else if (board[y][x] == 0) {
    //                System.out.print(red_car + "\t");
    //            } else if (board[y][x] == -1) {
    //                System.out.print(road + "\t");
    //            } else {
    //                System.out.print(board[y][x] + "\t");
    //            }
    //        }
    //        System.out.println("");
    //    }
    //}

    /**
     * Resets the board to the starting state
     */
    public void reset() {
        this.board = this.copyBoard(initialState.getGameBoard());
        this.vehicleMap = this.copyVehicleMap(initialState.getVehicleMap());
        undo.removeAllElements();
        redo.removeAllElements();
        moves = 0;
    }

    /**
     * Redo a move that has previously been undone
     */
    public void redo() {

        if (!redo.empty()) {
            undo.add(new MoveState(copyBoard(this.board), copyVehicleMap(this.vehicleMap)));
            MoveState ps = redo.pop();
            this.board = ps.getGameBoard();
            this.vehicleMap = ps.getVehicleMap();
            moves += 1;
        }
    }

    /**
     * Reverse a move made by the user
     */
    public void undo() {
        if (!undo.empty()) {
            redo.add(new MoveState(copyBoard(this.board), copyVehicleMap(this.vehicleMap)));
            MoveState ps = undo.pop();
            this.board = ps.getGameBoard();
            this.vehicleMap = ps.getVehicleMap();
            moves -=1;
        }
    }

    /**
     * Copy constructor for the board
     * @pre board != null
     * @post true
     * @param board
     * @return return a copy of the board
     */
    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[sizeRow][sizeCol];
        for (int y = 0; y < sizeRow; y++) {
            System.arraycopy(board[y], 0, copy[y], 0, sizeCol);
        }
        return copy;
    }

    /**
     * Copy constructor for vehicle map 
     * @pre map != null
     * @post true
     * @param map
     * @return
     */
    private Map<Integer, Vehicle> copyVehicleMap(Map<Integer,Vehicle> map) {
        Map<Integer, Vehicle> copy = new HashMap<>();
        for (Vehicle v : map.values()) {
            copy.put(v.getID(), new Vehicle(v));
        }
        return copy;
    }

    /**
     * Generate the initial state 
     */
    public void initState() {
        initialState = new MoveState(copyBoard(getBoard()), copyVehicleMap(this.vehicleMap));
    }

    /**
     * Return the moves
     * @return
     */
    public int getMoves(){
        return moves;
    }

	public String getStringRep(){

		// More efficient string appending
		StringBuilder sb = new StringBuilder();
		int vert;
		int length;
		int row;
		int col;
		String colour;
		int id;

		for (Vehicle v: getVehicles()){
			vert = ( v.getIsVertical() ) ? 0 : 1;
			length = v.getLength();
			row = v.getRow();
			col = v.getCol();
			id = v.getID();
			colour = (v.getColor().getRGB() == Color.RED.getRGB()) ? "red" : "orange" ;
			sb.append("- " + Integer.toString(vert));
			sb.append(" " + Integer.toString(length));
			sb.append(" " + Integer.toString(row));
			sb.append(" " + Integer.toString(col));
			sb.append(" " + colour);
			sb.append(" " + Integer.toString(id));
		}
		return sb.toString();
	}

	public void setNUIController(NetUIController nuic){
    	this.nuic = nuic;
	}
}
