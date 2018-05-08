import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Generate a puzzle using AStar. 
 *
 */
public class puzzleGeneratorAStar implements puzzleGenerator{
	private final Heuristic h;
	private PuzzleGame finalState;
	//The main car will always be of length 2
	private static final int mainCarLength = 2;
	//Other cars can either be of size 2 or size 3
	private final static int[] carLength = new int[] {2, 3};
	//private Collection<Integer> carLength;
	
	public puzzleGeneratorAStar(Heuristic h) {
		this.h = h;
		this.finalState = null;
		//carLength = new ArrayList<Integer>();
	}

	/**
	 * Given the parameters generate a random puzzle using those parameters
	 * @param width, the width of the board
	 * @param height, the height of the board
	 * @param exitRow, the row which the vehicle can exit
	 * @param exitCol, the col which the vehicle can exit
	 * @param numVehicle, the number of vehicles on the board
	 * @param move, the number of moves required to complete the game
	 * @return puzzleGame, 
	 */
	@Override
	public PuzzleGame generatePuzzle(int width, int height, int exitRow, int exitCol, int numVehicle, int moves) {

		return generateSolutionState(width, height, exitRow, exitCol, numVehicle);
	}
	
	
	/**
	 * Generate the initial solution state of which with the number of vehicles.
	 * The exit must be at the edge of the board
	 * @pre (exitRow == height - 1 || exitRow == 0) || (exitCol == width - 1 || exitCol == 0), numVehicle > 0
	 * @param width
	 * @param height
	 * @param exitRow
	 * @param numVehicle, number of vehicles
	 * @param move
	 * @return
	 */
	private PuzzleGame generateSolutionState(int width, int height, int exitRow, int exitCol, int numVehicle) {
		Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
		PuzzleGame puzzle = new PuzzleGame(width, height, exitRow, exitCol, vehicleMap);
		//Place main car
		boolean isVertical = true;
		int carRow = 0;
		int carCol = 0;
		if (exitRow == 0) {
			//Indicates that the exit is on the top of the board
			isVertical = true;
			carRow = exitRow;
			carCol = exitCol;
		} else if (exitRow == height - 1) {
			//Indicates that the exit is on the bottom of the board
			isVertical = true;
			carRow = height - mainCarLength;
			carCol = exitCol;
		} else if (exitCol == 0) {
			//Indicates that the exit is on the left of the board
			isVertical = false;
			carRow = exitRow;
			carCol = exitCol;
		} else if (exitCol == width - 1) {
			//Indicates that the exit is on the right of the board
			isVertical = false;
			carRow = exitRow;
			carCol = width - mainCarLength;
		}
		puzzle.addVehicle(isVertical, mainCarLength, carRow, carCol, new Color(255, 0, 0));
		//Place remaining cars
		int len = 0;
		int col0 = 0; 		//Colors of car
		int col1 = 0;
		int col2 = 0;
		Random randomGenerator = new Random();
		for (int i = 1; i < numVehicle; i++) {
			//Generate new random vehicle and add to board
			do {
				isVertical = randomGenerator.nextBoolean();
				len = carLength[randomGenerator.nextInt(carLength.length)];
				carRow = randomGenerator.nextInt(height - len + 1);
				carCol = randomGenerator.nextInt(width - len + 1);
			} while (!puzzle.canAddVehicle(isVertical, len, carRow, carCol));
			col0 = randomGenerator.nextInt(256);
			col1 = randomGenerator.nextInt(256);
			col2 = randomGenerator.nextInt(256);
			puzzle.addVehicle(isVertical, len, carRow, carCol, new Color(col0, col1, col2));
		}
		return puzzle;
	}

}





















