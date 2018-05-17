
public interface PuzzleGenerator {
	/**
	 * Specify the board size and exit location and the number of vehicles and generate a puzzle
	 * @param width
	 * @param height
	 * @param exitRow
	 * @param exitCol
	 * @param numVehicle, number of vehicles on the board
	 * @param moves, the number of moves required to solve the puzzle
	 * @return
	 */
	public PuzzleGame generatePuzzle(int width, int height, int exitRow, int exitCol, int moves);
	
	/**
	 * Generate a 6x6 board with random exit that takes around 15 moves to solve.
	 * @return
	 */
	public PuzzleGame generateEasyPuzzle();
	
	/**
	 * Generate a 12x6 board with random exit that takes around 30 moves to solve.
	 * @return
	 */
	public PuzzleGame generateMediumPuzzle();
	
	/**
	 * Generate a 18x6 board with random exit that takes around 45 moves to solve.
	 * @return
	 */
	public PuzzleGame generateHardPuzzle();
	
}
