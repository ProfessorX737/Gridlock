import java.awt.Color;

/**
 * More of a wrapper. 
 *
 */
public interface LevelEditor {
	
	/**
	 * Choose the size of the board.
	 * @param row
	 * @param col
	 */
	public void chooseBoardSize(int row, int col);
	
	/**
	 * Choose the exit.
	 * @param exitRow
	 * @param exitCol
	 */
	public void chooseExit(int exitRow, int exitCol);
	
	/**
	 * Returns the puzzle that you can currently making.
	 * @return
	 */
	public PuzzleGame exportLevel();
	
	/**
	 * Load a file with the specified name, and returns the puzzle game.
	 * @param filename
	 * @return
	 */
	public PuzzleGame loadSavedLevel(String filename);
	
	/**
	 * Save the game with the filename.
	 * @param filename
	 */
	public void saveLevel(String filename);
	
	/**
	 * Check whether the puzzle is solvable.
	 * @return
	 */
	public boolean isSolvable();
	
	/**
	 * Check whether a vehicle can be added.
	 * @param isVertical
	 * @param length
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean canAddVehicle(boolean isVertical, int length, int row, int col);

	
	/**
	 * Add vehicle.
	 * @param isVertical
	 * @param length
	 * @param row
	 * @param col
	 */
	public void addVehicle(boolean isVertical, int length, int row, int col, Color color);
	
	/**
	 * Returns the vehicle id at a location, if there is no vehicle there
	 * returns -1.
	 * @param row
	 * @param col
	 * @return
	 */
	public int getVehicleIDAtLocation(int row, int col);
	
	/**
	 * Remove vehicle at location.
	 * @param row
	 * @param col
	 */
	public void removeVehicleAtLocation(int row, int col);
	
	/**
	 * Change the position of the vehicle with the given ID to a new
	 * position.
	 * @param currRow current row of the vehicle
	 * @param currCol current col of the vehicle
	 * @param row
	 * @param col
	 */
	public void changeVehiclePosition(int currRow, int currCol, int row, int col);
	
	/**
	 * Essential rotates the vehicle around depending on whether
	 * it is currently vertical or not. 
	 * @param id
	 * @param isVertical
	 */
	public void changeVehicleIsVertical(int currRow, int currCol, boolean isVertical);

	/**
	 * Change the color of the vehicle.
	 * @param color
	 */
	public void changeVehicleColor(int currRow, int currCol, Color color);
}







































