import java.awt.Color;

/**
 * More of a wrapper. 
 *
 */
public interface LevelEditor {
	
	/**
	 * Choose the size of the board.
	 * @pre row > 0 && col > 0
	 * @pre true
	 * @param row
	 * @param col
	 */
	public void chooseBoardSize(int row, int col);
	
	/**
	 * Choose the exit.
	 * @pre 0 <= exitRow < row && 0 <= exitCol < col
	 * @post true
	 * @param exitRow
	 * @param exitCol
	 */
	public void chooseExit(int exitRow, int exitCol);
	
	/**
	 * Returns the puzzle that you can currently making.
	 * @pre true
	 * @post true
	 * @return puzzleGame
	 */
	public PuzzleGame exportLevel();
	
	/**
	 * Load a file with the specified name, and returns the puzzle game.
	 * @pre filename != null
	 * @post true
	 * @param filename path to the file where the puzzle is saved
	 * @return puzzleGame that was saved to the file
	 */
	public PuzzleGame loadSavedLevel(String filename);
	
	/**
	 * Save the game with the filename.
	 * @pre filename != null
	 * @post true
	 * @param filename to save the level with
	 */
	public void saveLevel(String filename);
	
	/**
	 * Check whether the puzzle is solvable.
	 * @pre true
	 * @post true
	 * @return true if the puzzle is solvable and false otherwise
	 */
	public boolean isSolvable();
	
	/**
	 * Check whether a vehicle can be added.
	 * @pre length > 0 && 0 < row < height && 0 < col < width
	 * @post true
	 * @param isVertical
	 * @param length
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean canAddVehicle(boolean isVertical, int length, int row, int col);

	
	/**
	 * Add vehicle.
	 * @pre length > 0 && 0 <= row < height && 0 <= col < width
	 * @post puzzle.containsVehicle() == true
	 * @param isVertical
	 * @param length
	 * @param row
	 * @param col
	 */
	public void addVehicle(boolean isVertical, int length, int row, int col, Color color);
	
	/**
	 * Returns the vehicle id at a location, if there is no vehicle there returns -1.
	 * @pre 0 <= row < height && 0 <= col < width
	 * @post true
	 * @param row
	 * @param col
	 * @return id of the vehicle at the location
	 */
	public int getVehicleIDAtLocation(int row, int col);
	
	/**
	 * Remove vehicle at location.
	 * @pre 0 <= row < height && 0 <= col < width
	 * @post puzzle.containsVheicle() == false
	 * @param row
	 * @param col
	 */
	public void removeVehicleAtLocation(int row, int col);
	
	/**
	 * Change the position of the vehicle with the given ID to a new position.
	 * @pre 0 <= currRow < height && 0 <= currCol < width && 0 <= row < height && 0 <= col < width
	 * @post puzzle.vehicleLocation() == (row, col)
	 * @param currRow current row of the vehicle
	 * @param currCol current col of the vehicle
	 * @param row new row of the vehicle
	 * @param col new row of the vehicle
	 */
	public void changeVehiclePosition(int currRow, int currCol, int row, int col);
	
	/**
	 * Essential rotates the vehicle around depending on whether it is currently vertical or not. 
	 * @pre 0 <= currRow < height && 0 <= currCol < width 
	 * @post puzzle.vehicleIsVertical() == isVertical
	 * @param currRow row of the vehicle
	 * @param currCol column of the vehicle
	 * @param isVertical new verticalness of the vehicle
	 */
	public void changeVehicleIsVertical(int currRow, int currCol, boolean isVertical);

	/**
	 * Change the color of the vehicle.
	 * @pre 0 <= currRow < height && 0 <= currCol < width 
	 * @post puzzle.vehicleColor() == color
	 * @param color new color for the vehicle
	 */
	public void changeVehicleColor(int currRow, int currCol, Color color);
}