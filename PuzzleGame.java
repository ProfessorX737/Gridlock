import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Represent the current board of the game
 */
public class PuzzleGame {
    //Used for printing out to the console
    private final static String red_car = "r";
    private final static String road = "-";
    private final static String wall = "W";
    //sizeRow and sizeCol keeps track of the size of the board
    private int sizeRow;
    private int sizeCol;
    private int exitRow;
    private int exitCol;
    //vehicleMap is a map of all the vehicles on the board with their ID as the key.
    private Map<Integer, Vehicle> vehicleMap;
    //a matrix which represents the board. Where their ID represents their location on the board
    private int[][] board;
    private List<PuzzleState> stateHistory;
    // private PuzzleState puzzleState;
    private int currentState;
    private Stack<PuzzleState> undo;
    private Stack<PuzzleState> redo;

    /**
     * Constructor for the board, when only the size of the board is provided.
     *
     * @param sizeRow, the number of rows in the board
     * @param sizeCol, the number of columns in the board
     */
    public PuzzleGame(int sizeRow, int sizeCol, int exitRow, int exitCol) {
        this.sizeRow = sizeRow;
        this.sizeCol = sizeCol;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.vehicleMap = new HashMap<>();
        this.stateHistory = new ArrayList<>();
        this.initBoard();
        this.currentState = 0;
        this.undo = new Stack<>();
        this.redo = new Stack<>();
    }

    /**
     * Constructor for the board, when the size of the board and map of vehicles is provided
     *
     * @param sizeRow,    the number of rows in the board
     * @param sizeCol,    the number of columns in the board
     * @param vehicleMap, a map of all the vehicles on the board
     */
    public PuzzleGame(int sizeRow, int sizeCol, int exitRow, int exitCol, Map<Integer, Vehicle> vehicleMap) {
        this.sizeRow = sizeRow;
        this.sizeCol = sizeCol;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
        this.vehicleMap = vehicleMap;//does this work
        this.initBoard();
        for (Vehicle v : this.vehicleMap.values()) {
            this.fillVehicleSpace(v, v.getID());
        }
    }

    private void initBoard() {
        this.board = new int[sizeRow][sizeCol];
        for (int y = 0; y < sizeRow; y++) {
            for (int x = 0; x < sizeCol; x++) {
                board[y][x] = -1;
            }
        }
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
     * @param row
     * @param col
     * @return
     * @pre row < this.sizeRow, col < this.sizeCol
     */
    public boolean isOccupied(int row, int col) {
        if (board[row][col] == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isOutOfBounds(int row, int col) {
        if (row < this.sizeRow && col < this.sizeCol && row >= 0 && col >= 0) {
            return false;
        }
        return true;
    }

    public int canMoveUp(int id) {
        Vehicle v = this.vehicleMap.get(id);
        if (!v.getIsVertical()) return 0;
        int row = v.getRow() - 1;
        int col = v.getCol();
        int numLegal = 0;
        while (!this.isOutOfBounds(row, col)) {
            if (this.isOccupied(row, col)) break;
            row--;
            numLegal++;
        }
        if (this.exitRow == 0 && id == 0 && row == -1) {
            return numLegal + 1;
        }
        return numLegal;
    }

    public int canMoveDown(int id) {
        Vehicle v = this.vehicleMap.get(id);
        if (!v.getIsVertical()) return 0;
        int row = v.getRow() + v.getLength();
        int col = v.getCol();
        int numLegal = 0;
        while (!this.isOutOfBounds(row, col)) {
            if (this.isOccupied(row, col)) {
                break;
            }
            row++;
            numLegal++;
        }
        if (this.exitRow == this.sizeRow - 1 && id == 0 && row == this.sizeRow) {
            return numLegal + 1;
        }
        return numLegal;
    }

    public int canMoveLeft(int id) {
        Vehicle v = this.vehicleMap.get(id);
        if (v.getIsVertical()) return 0;
        int row = v.getRow();
        int col = v.getCol() - 1;
        int numLegal = 0;
        while (!this.isOutOfBounds(row, col)) {
            if (this.isOccupied(row, col)) break;
            col--;
            numLegal++;
        }
        if (this.exitCol == 0 && id == 0 && col == -1) {
            return numLegal + 1;
        }
        return numLegal;
    }

    public int canMoveRight(int id) {
        Vehicle v = this.vehicleMap.get(id);
        if (v.getIsVertical()) return 0;
        int row = v.getRow();
        int col = v.getCol() + v.getLength();
        int numLegal = 0;
        while (!this.isOutOfBounds(row, col)) {
            if (this.isOccupied(row, col)) break;
            col++;
            numLegal++;
        }
        if (this.exitCol == this.sizeCol - 1 && id == 0 && col == this.sizeCol) {
            return numLegal + 1;
        }
        return numLegal;
    }

    /**
     * To move a vehicle specify the vehicle, direction and distance.
     *
     * @param id
     * @pre checkMove(id, direction, distance) == true
     */
    public void moveVehicle(int id, int newRow, int newCol) {
        // If current state is not the most recent state (ie if we have undone some moves))
        /*
        if(currentState != stateHistory.size() - 1){
            for(int i = currentState; i < stateHistory.size() - 1; i++){
	            stateHistory.remove(i);
            }
        }
        */

        // Adds the previous board state to state history
        /*
        if(currentState != stateHistory.size() - 1) {
            stateHistory = stateHistory.subList(0, currentState);
            currentState = stateHistory.size() - 1;
        }
        stateHistory.add(new PuzzleState(copyBoard(), copyVehicleMap()));
        System.out.println(stateHistory);
        currentState++;
        */
        undo.add(new PuzzleState(copyBoard(), copyVehicleMap()));

        Vehicle v = this.vehicleMap.get(id);
        this.fillVehicleSpace(v, -1);
        v.setPos(newRow, newCol);
        this.fillVehicleSpace(v, id);
    }

    private void fillVehicleSpace(Vehicle v, int id) {
        int row = v.getRow();
        int col = v.getCol();
        if (v.getIsVertical()) {
            for (int i = 0; i < v.getLength(); i++) {
                if (this.isOutOfBounds(row + i, col)) return;
                this.board[row + i][col] = id;
            }
        } else {
            for (int i = 0; i < v.getLength(); i++) {
                if (this.isOutOfBounds(row, col + i)) return;
                this.board[row][col + i] = id;
            }
        }
    }

    /**
     * @return
     * @pre main car has id 0 and is added to the class
     */
    public boolean isSolved() {
        if (this.board[exitRow][exitCol] == 0) {
            return true;
        }
        return false;
    }

    /**
     * @return the vehicle id if the position, if not valid return -1
     * @pre this.isOutOfBounds(row, col) == false
     */
    public int getVehicleIDAtLocation(int row, int col) {
        if (!this.isOutOfBounds(row, col)) {
            return board[row][col];
        }
        return -1;
    }

    /**
     * @return
     * @pre this.isOutOfBounds(row, col) == false
     * @pre board[row][col] is in this.vehicleMap.keys()
     */
    public Vehicle getVehicleAtLocation(int row, int col) {
        return this.vehicleMap.get(board[row][col]);
    }

    public boolean isVehicleVertical(int id) {
        Vehicle v = this.vehicleMap.get(id);
        if (v == null) return false;
        if (v.getIsVertical()) return true;
        return false;
    }

    public Vehicle getMainVehicle() {
        return vehicleMap.get(0);
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

    public void showBoard() {
        printBoard(this.board);
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
        if(!undo.empty()){
            PuzzleState ps = undo.firstElement();
            this.board = ps.getGameBoard();
            this.vehicleMap = ps.getVehicleMap();
            undo.removeAllElements();
            redo.removeAllElements();
        }
        /*
        System.out.println("...");
        currentState = 0;
        // System.out.println("Should reset to " + stateHistory.get(0));
        PuzzleState ps = stateHistory.get(currentState);
        this.board = ps.getGameBoard();
        this.vehicleMap = ps.getVehicleMap();
        */

    }

    /**
     * Redo a move that has previously been undone
     */
    public void redo() {
        /*
        System.out.println("Current state is " + this.currentState);
        if (currentState < stateHistory.size() - 1 && currentState != -1) {
            currentState++;
            PuzzleState ps = stateHistory.get(currentState);
            this.board = ps.getGameBoard();
            this.vehicleMap = ps.getVehicleMap();
        }
        */
        if(!redo.empty()){
            PuzzleState ps = redo.pop();
            this.board = ps.getGameBoard();
            this.vehicleMap = ps.getVehicleMap();
        }
    }

    /**
     * Reverse a move made by the user
     */
    // TODO fine tune behaviour - first press undoes the last 2 moves?????
    //
    public void undo() {
        /*
        System.out.println("Current state is " + this.currentState);
        if (this.currentState > 0) {
            currentState--;
            PuzzleState ps = stateHistory.get(currentState);
            this.board = ps.getGameBoard();
            this.vehicleMap = ps.getVehicleMap();
        }
        */
        if(!undo.empty()){
            redo.add(new PuzzleState(copyBoard(),  copyVehicleMap()));
            PuzzleState ps = undo.pop();
            this.board = ps.getGameBoard();
            this.vehicleMap = ps.getVehicleMap();
        }
    }

    public void addMove() {
        for (int i = currentState + 1; i < stateHistory.size() - 1; i++) {
            stateHistory.remove(i);
        }

    }

    private int[][] copyBoard() {
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
        stateHistory.add(new PuzzleState(copyBoard(), copyVehicleMap()));
    }
}

