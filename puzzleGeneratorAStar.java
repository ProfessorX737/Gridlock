import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Generate a puzzle using AStar. 
 *
 */
public class PuzzleGeneratorAStar implements PuzzleGenerator{
	//The main car will always be of length 2
	private static final int mainCarLength = 2;
	//Other cars can either be of size 2 or size 3
	private final static int[] carLength = new int[] {2, 3};
	//private Collection<Integer> carLength;
	//limit of which to generate random vehicle
	private final static int randomLimit = 20;
	//limit of how many tries to generate board
	private final static int triesLimit = 1;
	//number of different puzzles to generate each time
	private final static int boardBranch = 10;
	//number of directions
	private final static int numDirections = 4;

	public PuzzleGeneratorAStar() {
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
	public PuzzleGame generatePuzzle(int width, int height, int moves) {
		int movesRequired = 1;
		boolean canAdd = true;
		int tries = 0;
		//generate initial puzzle
		PuzzleGame puzzle = generateInitialState(width, height);
		puzzle.showBoard();
		//Keep generating until we have a suitable puzzle
		while (movesRequired <= moves) {
			System.out.println("Generating puzzle");
			if (tries > triesLimit) {
				System.out.println("Gave up generating puzzles");
				//if tries exceeds tries limit then it is too difficult to generate
				return null;
			}
			//add random vehicle
			//if can't add any more vehicles move the vehicles around
			canAdd = mostDifficultAdd(puzzle, movesRequired);
			if (!canAdd) {
				System.out.println("Couldn't add anymore vehicles to the board");
				//jumblePuzzle()
				return puzzle;
				//puzzle = generateInitialState(width, height, exitRow, exitCol);
				//tries++;
			} else {
				List<int[][]> puzzleSolved = PuzzleSolver.solve(puzzle);
				if (puzzleSolved == null) {
					//couldn't Solve puzzle restart
					movesRequired = 0;
					puzzle = generateInitialState(width, height);
					tries++;
				} else {
					movesRequired = puzzleSolved.size();
				}
			}
		}
		System.out.println("Got a spicy puzzle this time");
		return puzzle;
	}
	
	/**
	 * Find all possible locations for a vehicle, adds them one by one and sees if its harder.
	 * Take the first puzzle that is harder
	 * @param puzzle
	 * @param currentMoves, number of moves required to solve the current puzzle
	 */
	private boolean mostDifficultAdd(PuzzleGame puzzle, int currentMoves) {
		//get all possible location for the vehicle
		List<Vehicle> possibleVehicle = puzzle.getPossibleVehicle();
		//shuffle for random outcome, is this correct
		Collections.shuffle(possibleVehicle);
		//add every possible vehicle to the board and see if it makes the game harder
		for (Vehicle vehicle : possibleVehicle) {
			//get a new board and add a random piece and see if its harder
			PuzzleGame newPuzzle = new PuzzleGame(puzzle);
			newPuzzle.addVehicle(vehicle);
			newPuzzle.showBoard();
			//get the number of moves required to solve puzzle
			List<int[][]>puzzleSolved = PuzzleSolver.solve(newPuzzle);
			if (puzzleSolved != null) {
				//couldn't solve the puzzle
				if (puzzleSolved.size() - 1 > currentMoves) {
					//found a harder puzzle
					puzzle = newPuzzle;
					return true;
				}
			}
		}
		//could not find a harder puzzle
		return false;
	}
	
	/**
	 * Jumble the vehicles around using AStar and choose the one which gives the highest number of moves required to solve
	 * @param puzzle, the puzzle that will be jumbled
	 * @param moves, number of moves required
	 */
	private void jumbleVehicles(PuzzleGame puzzle, int moves) {
		//Get all the connections of the current graph and add them to queue
		//Sort based on the most number of moves required to solve puzzle
		
		Heuristic<PuzzleState> h = new PuzzleHeuristic();
		Graph<PuzzleState> stateGraph = new TreeGraph<>();
		ShortestPathSearch<PuzzleState> search = new AStar<>(stateGraph,h);
		PuzzleGame goal = new PuzzleGame(puzzle.getNumRows(),puzzle.getNumCols(),puzzle.getExitRow(),puzzle.getExitCol());
		goal.addVehicle(false, 2, puzzle.getExitRow(), puzzle.getExitCol(), Color.RED);
		List<PuzzleState> states = search.shortestPath(new PuzzleState(puzzle), new PuzzleState(goal));
		//if(states == null) return null;
		List<int[][]> path = new ArrayList<>();
		for(PuzzleState state : states) {
			path.add(state.getBoard());
		}
		//return path;
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
	 * Places the main vehicle in a random place on the map, place it at the end of the map.
	 * The exit is randomly generated, although max difficulty puzzles might need the exit near the middle
	 * @param width, the width of the board
	 * @param height, the height of the board
	 * @return
	 */
	private PuzzleGame generateInitialState(int height, int width) {
		Random randomGenerator = new Random();
		//generate a random exit
		int direction = randomGenerator.nextInt(numDirections);
		int exitRow = 0;
		int exitCol = 0;
		if (direction == 0) {
			//exit on the top row
			exitRow = 0;
			exitCol = randomGenerator.nextInt(width);
		} else if (direction == 1) {
			//exit on the right
			exitRow = randomGenerator.nextInt(height);
			exitCol = width - 1;
		} else if (direction == 2) {
			//exit on the bottom
			exitRow = height - 1;
			exitCol = randomGenerator.nextInt(width);
		} else if (direction == 3) {
			//exit on the left
			exitRow = randomGenerator.nextInt(height);
			exitCol = 0;		
		}

		Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
		PuzzleGame puzzle = new PuzzleGame(width, height, exitRow, exitCol, vehicleMap);
		//How far should it be from the other end of the board.
		//Place main car
		boolean isVertical = true;
		int carRow = 0;
		int carCol = 0;
		if (exitRow == 0) {
			//Indicates that the exit is on the top of the board
			isVertical = true;
			carRow = height - mainCarLength;
			carCol = exitCol;
		} else if (exitRow == height - 1) {
			//Indicates that the exit is on the bottom of the board
			isVertical = true;
			carRow = 0;
			carCol = exitCol;
		} else if (exitCol == 0) {
			//Indicates that the exit is on the left of the board
			isVertical = false;
			carRow = exitRow;
			carCol = width - mainCarLength;
		} else if (exitCol == width - 1) {
			//Indicates that the exit is on the right of the board
			isVertical = false;
			carRow = exitRow;
			carCol = 0;
		}
		puzzle.addVehicle(isVertical, mainCarLength, carRow, carCol, new Color(255, 0, 0));
		return puzzle;
	}
}





















