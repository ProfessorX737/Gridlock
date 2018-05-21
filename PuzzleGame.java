import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.awt.*;
import java.io.Serializable;
import java.util.*;

/**
 * Represent the current board of the game
 */
public class PuzzleGame implements Serializable {
	//puzzle identification
	private int id;
	//required for saving
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

	/**
	 * Constructor for the board, when only the size of the board is provided.
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
	 * Constructor for the board, when the size of the board and map of vehicles is provided
	 * @param sizeRow, the number of rows in the board
	 * @param sizeCol, the number of columns in the board
	 * @param vehicleMap, a map of all the vehicles on the board
	 */
	public PuzzleGame(int sizeRow, int sizeCol, int exitRow, int exitCol, Map<Integer, Vehicle> vehicleMap) {
		this.sizeRow = sizeRow;
		this.sizeCol = sizeCol;
		this.exitRow = exitRow;
		this.exitCol = exitCol;
		this.vehicleMap = vehicleMap;//does this work
		this.initBoard();
		for(Vehicle v : this.vehicleMap.values()) {
			this.fillVehicleSpace(v, v.getID());
		}
		this.minMoves = 0;
	}

	/**
	 * Copy constructor
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
	
	private void initBoard() {
		this.board = new int[sizeRow][sizeCol];
		for (int y = 0; y < sizeRow; y++) {
			for (int x = 0; x < sizeCol; x++) {
				board[y][x]= -1;
			}
		}
	}
	
	/**
	 * Check whether the vehicle can be added to the location.
	 * @param row
	 * @param col
	 * @param vehicle
	 * @return
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
	 * @param vehicle
	 */
	public void addVehicle(Vehicle vehicle) {
		int id = vehicleMap.size();
		vehicle.setID(id);
		this.vehicleMap.put(id, vehicle);
		this.fillVehicleSpace(vehicle, id);
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
		while(!this.isOutOfBounds(row, col)) {
			if(this.isOccupied(row,col)) break;
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
		while(!this.isOutOfBounds(row, col)) {
			if(this.isOccupied(row, col)) {
				break;
			}
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
		while(!this.isOutOfBounds(row, col)) {
			if(this.isOccupied(row, col)) break;
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
		while(!this.isOutOfBounds(row, col)) {
			if(this.isOccupied(row, col)) break;
			col++;
			numLegal++;
		}
		if(this.exitCol == this.sizeCol - 1 && id == 0 && col == this.sizeCol) {
			return numLegal + 1;
		}
		return numLegal;
	}
	
	
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
	 * @pre main car has id 0 and is added to the class
	 * @return
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

	private int[][] cloneBoard(int[][] board) {
		int[][] clone = new int[this.sizeRow][this.sizeCol];
		for(int i = 0; i < this.sizeRow; i++) {
			for(int k = 0; k < this.sizeCol; k++) {
				clone[i][k] = board[i][k];
			}
		}
		return clone;
	}
	
	/**
	 * Returns a random vehicle from the map of vehicles.
	 * Returns the reference to the vehicle.
	 * @pre vehicleMap.size() >= 1
	 * @post true
	 * @return
	 */
	public Vehicle getRandomVehicle() {
		Random rand = new Random();
		return vehicleMap.get(rand.nextInt(vehicleMap.size()));
	}
	
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
	 * @pre this.isOutOfBounds(row,col) == false
	 * @return the vehicle id if the position, if not valid return -1
	 */
	public int getVehicleIDAtLocation(int row, int col) {
		if(!this.isOutOfBounds(row, col)) {
			return board[row][col];
		} 	
		return -1;
	}
	
	/**
	 * @pre this.isOutOfBounds(row,col) == false
	 * @pre board[row][col] is in this.vehicleMap.keys()
	 * @return 
	 */
	public Vehicle getVehicleAtLocation(int row, int col) {
		return this.vehicleMap.get(board[row][col]);
	}
	
	public boolean isVehicleVertical(int id) {
		Vehicle v = this.vehicleMap.get(id);
		if(v == null) return false;
		if(v.getIsVertical()) return true;
		return false;
	}
	
	public Vehicle getMainVehicle() {
		return vehicleMap.get(0);
	}
	
	/**
	 * Returns the row of the vehicle
	 * @pre vehicleMap.contains(id)
	 * @post true
	 * @param id, of the vehicle
	 * @return the row at which the vehicle head occupies
	 */
	public int getVehicleRow(int id) {
		return vehicleMap.get(id).getRow();
	}
	
	/**
	 * Return the row of the vehicle
	 * @pre vehicleMap.contains(id)
	 * @post true
	 * @param id, of the vehicle
	 * @return the column at which the vehicle head occupies
	 */
	public int getVehicleCol(int id) {
		return vehicleMap.get(id).getCol();
	}
	
	public Collection<Vehicle> getVehicles() {
		return vehicleMap.values();
	}
	
	public int getNumRows() {
		return this.sizeRow;
	}
	
	public int getNumCols() {
		return this.sizeCol;
	}
	
	public int[][] getBoard() {
		return this.board;
	}
	
	public int getExitRow() {
		return this.exitRow;
	}
	public int getExitCol() {
		return this.exitCol;
	}
	
	public int getMinMoves() {
		return this.minMoves;
	}
	
	public void setMinMoves(int minMoves) {
		this.minMoves = minMoves;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
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
	 * Remove vehicle from the vehicleMap and the board.
	 */
	public void removeVehicle(int id) {
		//remove from the board
		this.fillVehicleSpace(vehicleMap.get(id), -1);
		//remove from the map
		vehicleMap.remove(id);
	}
	
	/**
	 * This might need to be changed
	 * Will be very expensive if we had to recalculate this every time
	 * Generates all possible vehicle spaces
	 */
	public List<Vehicle> getPossibleVehicle() {
		//number of empty spaces found so far
		int emptySpace = 0;
		//placeholderID which will be changed it it is the best choice
		int placeHolderID = 0xDEADBEEF;
		List<Vehicle> possibleVehicle = new ArrayList<Vehicle>();
		//exclude spaces that directly block the main vehicle
		boolean mainIsVertical = vehicleMap.get(0).getIsVertical();
		int mainRow = vehicleMap.get(0).getRow();
		int mainCol = vehicleMap.get(0).getCol();
		
		//go through the board vertically and find all spaces which are 2 or 3 in length
		for (int i = sizeCol - 1; i >= 0; i--) {
			for (int j = sizeRow - 1; j >= 0; j--) {
				if (mainIsVertical == true && mainCol == i) {
					//don't return vehicles that directly get in the way
					continue;
				}
				if (!isOccupied(j, i)) {
					emptySpace++;
					for (int k = 0; k < vehicleSize.length; k++) {
						if (emptySpace >= vehicleSize[k]) {
							possibleVehicle.add(new Vehicle(placeHolderID, true, vehicleSize[k], j, i, Color.ORANGE));
						}
					}
				} else {
					emptySpace = 0;
				}
			}
			emptySpace = 0;
		}
		
		//go through the board horizontally and find empty spaces
		emptySpace = 0;
		for (int j = sizeRow - 1; j >= 0 ; j--) {
			for (int i = sizeCol - 1; i >= 0; i--) {
				if (mainIsVertical == false && mainRow == j) {
					//don't return vehicles that directly get in the way
					continue;
				}
				if (!isOccupied(j, i)) {
					emptySpace++;
					for (int k = 0; k < vehicleSize.length; k++) {
						if (emptySpace >= vehicleSize[k]) {
							possibleVehicle.add(new Vehicle(placeHolderID, false, vehicleSize[k], j, i, Color.ORANGE));
						}
					}
				} else {
					emptySpace = 0;
				}
			}
			emptySpace = 0;
		}
		return possibleVehicle;
	}
	
	public List<Vehicle> getPossibleIntersects() {
		List<Vehicle> intersects = new ArrayList<>();
		for(Vehicle v : this.vehicleMap.values()) {
			intersects.addAll(this.getPossibleIntersects(v.getID()));
		}
		return intersects;
	}
	
	public List<Vehicle> getPossibleIntersects(int vId) {
		List<Vehicle> intersects = new ArrayList<>();
		intersects.addAll(this.getPossibleIntersects(vId, 2));
		intersects.addAll(this.getPossibleIntersects(vId, 3));
		return intersects;
	}
	
	public List<Vehicle> getPossibleIntersects(int vId, int vLength) {
		List<Vehicle> possibleVehicles = new ArrayList<>();
		int newId = this.vehicleMap.size();
		Vehicle v = this.vehicleMap.get(vId);
		if(v.getIsVertical()) {
			for(int i = 1; i <= this.canMoveUp(vId); i++) {
				int row = v.getRow() - i;
				for(int l = 0; l < vLength; l++) {
					int col = v.getCol() - l;
					if(this.canAddVehicle(false, vLength, row, col)) {
						possibleVehicles.add(new Vehicle(newId,false,vLength,row,col,Color.ORANGE));
					}
				}
			}
			for(int i = 0; i < this.canMoveDown(vId); i++) {
				int row = v.getRow() + v.getLength() + i;
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
				for(int l = 0; l < vLength; l++) {
					int row = v.getRow() - l;
					if(this.canAddVehicle(true, vLength, row, col)) {
						possibleVehicles.add(new Vehicle(newId,true,vLength,row,col,Color.ORANGE));
					}
				}
			}
			for(int i = 0; i < this.canMoveRight(vId); i++) {
				int col = v.getCol() + v.getLength() + i;
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
     * Add vehicle to the board.
     *
     * @param isVertical
     * @param length
     * @param row
     * @param col
     * @param color
     */
    public void addVehicle(boolean isVertical, int length, int row, int col, Color color) {
        int id = vehicleMap.size();
        Vehicle v = new Vehicle(id, isVertical, length, row, col, color);
        this.vehicleMap.put(id, v);
        this.fillVehicleSpace(v, id);
    }

    /**
     * To move a vehicle specify the vehicle, direction and distance.
     * * @param id
     *
     * @pre checkMove(id, direction, distance) == true
     */
    public void moveVehicleState(int id, int newRow, int newCol) {


        // Todo currently clicking on the vehicle counts as a move
        // Need to ensure it is not
        Vehicle v = this.vehicleMap.get(id);
        if (v.getRow() != newRow || v.getCol() != newCol){
            redo.removeAllElements();
            undo.add(new MoveState(copyBoard(this.board), copyVehicleMap()));
            this.fillVehicleSpace(v, -1);
            v.setPos(newRow, newCol);
            this.fillVehicleSpace(v, id);
            moves += 1;
        }
    }

    public void moveVehicle(int id, int newRow, int newCol) {
        Vehicle v = this.vehicleMap.get(id);
		this.fillVehicleSpace(v, -1);
		v.setPos(newRow, newCol);
		this.fillVehicleSpace(v, id);
    }

    private void printBoard(int[][] board) {
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

    /**
     * Resets the board to the starting state
     */
    public void reset() {
        this.board = initialState.getGameBoard();
        this.vehicleMap = initialState.getVehicleMap();
        undo.removeAllElements();
        redo.removeAllElements();
        moves = 0;
    }

    /**
     * Redo a move that has previously been undone
     */
    public void redo() {

        if (!redo.empty()) {
            undo.add(new MoveState(copyBoard(this.board), copyVehicleMap()));
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
            redo.add(new MoveState(copyBoard(this.board), copyVehicleMap()));
            MoveState ps = undo.pop();
            this.board = ps.getGameBoard();
            this.vehicleMap = ps.getVehicleMap();
            moves -=1;
        }
    }

    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[sizeRow][sizeCol];
        for (int y = 0; y < sizeRow; y++) {
            System.arraycopy(board[y], 0, copy[y], 0, sizeCol);
        }
        return copy;
    }

    private Map<Integer, Vehicle> copyVehicleMap() {
        Map<Integer, Vehicle> copy = new HashMap<>();
        for (Vehicle v : getVehicles()) {
            copy.put(v.getID(), new Vehicle(v));
        }
        return copy;
    }

    public void initState() {
        initialState = new MoveState(copyBoard(getBoard()), copyVehicleMap());
    }

    public int getMoves(){
        return moves;
    }
}
