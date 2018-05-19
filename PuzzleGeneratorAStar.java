import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Generate a puzzle using AStar.
 */
public class PuzzleGeneratorAStar implements PuzzleGenerator{
	//The main car will always be of length 2
	private static final int mainCarLength = 2;
	//List of vehicles that you can add

    public PuzzleGeneratorAStar() {
    }

	/**
	 * Given the parameters generate a random puzzle using those parameters
	 * It may be impossible to include numVehicles and moves as params as time to generate would be too long
	 * @param width, the width of the board
	 * @param height, the height of the board
	 * @param exitRow, the row which the vehicle can exit
	 * @param exitCol, the col which the vehicle can exit
	 * @param move, the number of moves required to complete the game
	 * @return puzzleGame, 
	 */
	@Override
	public PuzzleGame generatePuzzle(int width, int height, int exitRow, int exitCol, int moves) {
		int movesRequired = 1;
		//generate initial puzzle
		PuzzleGame puzzle = generateInitialState(width, height, exitRow, exitCol);
		//Keep generating until we have a suitable puzzle
		while (movesRequired <= moves) {
			//add random vehicle which increases the difficulty and is still solvable
			PuzzleGame newPuzzle = mostDifficultAdd(puzzle, movesRequired);
			if (newPuzzle == null) {
				return puzzle;
			} else {
				int requiredToSolve = newPuzzle.getRequiredToSolve();
				movesRequired = requiredToSolve;
				puzzle = newPuzzle;
			}
		}
		return puzzle;
	}
	
	/**
	 * Find all possible locations for a vehicle, adds them one by one and sees if its harder.
	 * Take the first puzzle that is harder
	 * @param puzzle
	 * @param currentMoves, number of moves required to solve the current puzzle
	 */
	private PuzzleGame mostDifficultAdd(PuzzleGame puzzle, int currentMoves) {
		//add every possible vehicle to the board and see if it makes the game harder
		List<Vehicle> possibleVehicle = puzzle.getPossibleVehicle();
		Collections.shuffle(possibleVehicle);
		for (int i = 0; i < possibleVehicle.size(); i++) {
			Vehicle vehicle = possibleVehicle.get(i);
			//get a new board and add a random piece and see if its harder
			PuzzleGame newPuzzle = new PuzzleGame(puzzle);
			newPuzzle.addVehicle(vehicle);
			//get the number of moves required to solve puzzle
			List<int[][]>puzzleSolved = PuzzleSolver.solve(newPuzzle);
			if (puzzleSolved != null) {
				int requiredMoves = puzzleSolved.size() - 1;
				if (requiredMoves > currentMoves) {
					//found a harder puzzle
					//set the required amount to solve the puzzle
					newPuzzle.setRequiredToSolve(requiredMoves);
					//the vehicle that has been added
					return newPuzzle;
				}
			} 
		}
		//could not find a harder puzzle
		return null;
	}
	
	/**
	 * Places the main vehicle in a random place on the map, place it at the end of the map.
	 * The exit is randomly generated, although max difficulty puzzles might need the exit near the middle
	 * @param width, the width of the board
	 * @param height, the height of the board
	 * @return
	 */
	private PuzzleGame generateInitialState(int height, int width, int exitRow, int exitCol) {
		PuzzleGame puzzle = new PuzzleGame(height, width, exitRow, exitCol);
		//Place main car
		//Indicates that the exit is on the right of the board
		boolean isVertical = false;
		int carRow = exitRow;
		int carCol = 0;
		puzzle.addVehicle(isVertical, mainCarLength, carRow, carCol, Color.RED);
		return puzzle;
	}
	
	/**
	 * All puzzles have the exit on the right
	 * @param height
	 * @param width
	 * @return
	 */
	private List<Integer> randomExit(int height, int width) {
		Random randomGenerator = new Random();
		//generate a random exit
		int exitRow = randomGenerator.nextInt(height);
		int exitCol = width - 1;
		List<Integer> exit = new ArrayList<Integer>();
		exit.add(exitRow);
		exit.add(exitCol);
		return exit;
	}
	
	/**
	 * Merge two puzzles together, they must have similar height.
	 * Puzzles are always from left to right.
	 * First and second puzzles are destroyed, afterwards. Don't reference again.
	 * @pre first.height == second.height 
	 * @post first = null && second == null
	 * @param first, the first puzzle, other puzzles will be attached to this puzzle.
	 * @param second, puzzle to be merged, the puzzle can include the red car which it will be removed.
	 * @return
	 */
	private PuzzleGame puzzleMerge(PuzzleGame first, PuzzleGame second) {
		int firstExitRow = first.getExitRow();
		int firstHeight = first.getNumRows();
		int firstWidth = first.getNumCols();
		int secondWidth = second.getNumCols();
		int numVehicles = 0;
		PuzzleGame mergedPuzzle = null;

		//remove the main car from the second puzzle
		second.removeVehicle(0);


		mergedPuzzle = new PuzzleGame(firstHeight, firstWidth + secondWidth, firstExitRow, firstWidth + secondWidth - 1);
		//copy over everything from first no need to change anything
		for (Vehicle vehicle : first.getVehicles()) {
			mergedPuzzle.addVehicle(vehicle);
			numVehicles++;
		}
		//copy from the second puzzle and change the id 
		for (Vehicle vehicle : second.getVehicles()) {
			vehicle.setID(numVehicles);
			vehicle.setCol(vehicle.getCol() + firstWidth);
			mergedPuzzle.addVehicle(vehicle);
			numVehicles++;
		}

		return mergedPuzzle;
	}
	
	@Override
	public PuzzleGame generateVeryEasyPuzzle() {
		List<Integer> exit = randomExit(6, 6);
		int exitRow = exit.get(0);
		int exitCol = exit.get(1);
		return generatePuzzle(6, 6, exitRow, exitCol, 5);
		
	}

    /**
     * Jumble the vehicles around, using a theory that randomly moving the vehicles around will generate a more
     * complex puzzle
     * Moves one vehicle around randomly
     *
     * @param puzzle, the puzzle that will be jumbled
     */
    private PuzzleGame jumbleVehicles(PuzzleGame puzzle) {
        //jumble the vehicles around n times
        Random randomGenerator = new Random();
        //create a puzzle state and see what other connections it has
        PuzzleState currState = new PuzzleState(puzzle);
        List<PuzzleState> stateList = currState.getConnections();
        PuzzleState newState = stateList.get(randomGenerator.nextInt(stateList.size()));
        return newState.getGame();
    }

    /**
     * Adds a random vehicle onto the board, it ensures that the vehicle does not super block the main car.
     * i.e is the same orientation and in the same row or col
     *
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

	/**
	 * Generate a 18x6 puzzle.
	 */
	@Override
	public PuzzleGame generateHardPuzzle() {
		List<Integer> exit = randomExit(6, 6);
		int exitRow = exit.get(0);
		int exitCol = exit.get(1);
		//generate two different puzzles and merge them together
		PuzzleGame mainPuzzle = generatePuzzle(6, 6, exitRow, exitCol, 20);
		PuzzleGame secondPuzzle = generatePuzzle(6, 6, exitRow, exitCol, 20);
		PuzzleGame thirdPuzzle = generatePuzzle(6, 6, exitRow, exitCol, 20);
		PuzzleGame firstMerge = puzzleMerge(mainPuzzle, secondPuzzle);
		return puzzleMerge(firstMerge, thirdPuzzle);
	}
	
	/**
	 * Don't use takes quite a while to generate
	 * @return
	 */
	public PuzzleGame generateSpicyPuzzle() {
		List<Integer> exit = randomExit(8, 5);
		int exitRow = exit.get(0);
		int exitCol = exit.get(1);
		PuzzleGame mainPuzzle = generatePuzzle(8, 5, exitRow, exitCol, 20);
		PuzzleGame secondPuzzle = generatePuzzle(8, 5, exitRow, exitCol, 20);
		PuzzleGame thirdPuzzle = generatePuzzle(8, 5, exitRow, exitCol, 20);
		PuzzleGame mergedPuzzle = puzzleMerge(mainPuzzle, secondPuzzle);
		return puzzleMerge(mergedPuzzle, thirdPuzzle);
	}
}





















