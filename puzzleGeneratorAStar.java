import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Generate a puzzle using AStar. 
 *
 */
public class puzzleGeneratorAStar implements puzzleGenerator{
	//The main car will always be of length 2
	private static final int mainCarLength = 2;
	//Other cars can either be of size 2 or size 3
	private final static int[] carLength = new int[] {2, 3};
	//private Collection<Integer> carLength;
	//limit of which to generate random vehicle
	private final static int randomLimit = 100;
	//limit of how many tries to generate board
	private final static int triesLimit = 100;

	public puzzleGeneratorAStar() {
	}

	/**
	 * Given the parameters generate a random puzzle using those parameters
	 * It may be impossible to include numVehicles and moves as params as time to generate would be too long
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
		int movesRequired = 0;
		boolean canAdd = true;
		int tries = 0;
		PuzzleSolver solver = new PuzzleSolver();
		List<int[][]> puzzleSolved = null;
		//generate initial puzzle
		PuzzleGame puzzle = generateInitialState(width, height, exitRow, exitCol);
		//Keep generating until we have a suitable puzzle
		while (movesRequired < moves) {
			if (tries > triesLimit) {
				//if tries exceeds tries limit then it is too difficult to generate
				return null;
			}
			//add random vehicle
			//if can't find a suitable place to add vehicle start over
			canAdd = addRandomVehicle(puzzle);
			if (!canAdd) {
				puzzle = generateInitialState(width, height, exitRow, exitCol);
				tries++;
			} else {
				puzzleSolved = solver.solve(puzzle);
				if (puzzleSolved == null) {
					//couldn't Solve puzzle restart
					movesRequired = 0;
					puzzle = generateInitialState(width, height, exitRow, exitCol);
					tries++;
				} else {
					movesRequired = puzzleSolved.size() - 1;
				}
			}
		}
		return puzzle;
	}
	
	/**
	 * Adds a random vehicle onto the board, it ensures that the vehicle does not super block the main car.
	 * i.e is the same orientation and in the same row or col
	 * @param puzzle, puzzle of which to add the vehicle to
	 * @return true if successfully added vehicle otherwise false
	 */
	private boolean addRandomVehicle(PuzzleGame puzzle) {
		Random randomGenerator = new Random();
		//Get the orientation of the main car and its position
		boolean mainIsVertical = puzzle.getMainVehicle().getIsVertical();
		int mainRow = puzzle.getMainVehicle().getRow();
		int mainCol = puzzle.getMainVehicle().getCol();
		//Get board params
		int height = puzzle.getNumRows();
		int width = puzzle.getNumCols();
		
		//generate random position for new vehicle
		int len = 0;
		int carID = 0;
		boolean isVertical = true;
		int carRow = 0;
		int carCol = 0;
		int col0 = 0; 		//Colors of car
		int col1 = 0;
		int col2 = 0;

		//loop until limit is reached or vehicle is generated
		for (int i = 0; i < randomLimit; i++) {
			len = carLength[randomGenerator.nextInt(carLength.length)];
			isVertical = randomGenerator.nextBoolean();
			col0 = randomGenerator.nextInt(256);
			col1 = randomGenerator.nextInt(256);
			col2 = randomGenerator.nextInt(256);
			if (isVertical) {
				carRow = randomGenerator.nextInt(height - len + 1);
				carCol = randomGenerator.nextInt(width);
			} else {
				carRow = randomGenerator.nextInt(height);
				carCol = randomGenerator.nextInt(width - len + 1);
			}
			if (isVertical == true && carCol != mainCol) {
				//vertical but not in same column
				if (puzzle.canAddVehicle(isVertical, len, carRow, carCol)) {
					puzzle.addVehicle(isVertical, len, carRow, carCol, new Color(col0, col1, col2));
					return true;
				}
			} else if (isVertical == false && carRow != mainRow) {
				//horizontal but not in same row
				if (puzzle.canAddVehicle(isVertical, len, carRow, carCol)) {
					puzzle.addVehicle(isVertical, len, carRow, carCol, new Color(col0, col1, col2));
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Places the main vehicle in a random place on the map, place it near the end of the map.
	 * The main car should not be more than half way across the board, just a hypothesis but it would be hard to generate puzzle otherwise.
	 * @param width, the width of the board
	 * @param height, the height of the board
	 * @param exitRow, the row which the vehicle can exit
	 * @param exitCol, the col which the vehicle can exit
	 * @return
	 */
	private PuzzleGame generateInitialState(int width, int height, int exitRow, int exitCol) {
		Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
		PuzzleGame puzzle = new PuzzleGame(width, height, exitRow, exitCol, vehicleMap);
		Random randomGenerator = new Random();
		//How far should it be from the other end of the board.
		int dist = 0;
		//Place main car
		boolean isVertical = true;
		int carRow = 0;
		int carCol = 0;
		if (exitRow == 0) {
			//Indicates that the exit is on the top of the board
			isVertical = true;
			dist = randomGenerator.nextInt(height / 2);
			carRow = height - dist - mainCarLength;
			carCol = exitCol;
		} else if (exitRow == height - 1) {
			//Indicates that the exit is on the bottom of the board
			isVertical = true;
			dist = randomGenerator.nextInt(height / 2);
			carRow = height + dist;
			carCol = exitCol;
		} else if (exitCol == 0) {
			//Indicates that the exit is on the left of the board
			isVertical = false;
			dist = randomGenerator.nextInt(width / 2);
			carRow = exitRow;
			carCol = width - dist - mainCarLength;
		} else if (exitCol == width - 1) {
			//Indicates that the exit is on the right of the board
			isVertical = false;
			dist = randomGenerator.nextInt(width / 2);
			carRow = exitRow;
			carCol = width + dist;
		}
		puzzle.addVehicle(isVertical, mainCarLength, carRow, carCol, new Color(255, 0, 0));
		return puzzle;
	}
}





















