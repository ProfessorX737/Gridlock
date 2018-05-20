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
	 * @param v
	 * @return
	 */
	public boolean canAddVehicle(Vehicle v);
	
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
	 * @param v
	 */
	public void addVehicle(Vehicle v);
	
	/**
	 * Add vehicle.
	 * @param isVertical
	 * @param length
	 * @param row
	 * @param col
	 */
	public void addVehicle(boolean isVertical, int length, int row, int col, Color color);
	
	/**
	 * Remove vehicle at location.
	 * @param row
	 * @param col
	 */
	public void removeVehicleAtLocation(int row, int col);
	
	/**
	 * Generate a vehicle.
	 * @return
	 */
	public Vehicle generateVehicle(boolean isVertical, int length, int row, int col, Color color);

}
