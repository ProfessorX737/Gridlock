import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;

/**
 * Generate a puzzle using AStar. 
 *
 */
public class PuzzleGeneratorAStar implements PuzzleGenerator {
	//number of difficulty levels that can be generated
    public static final int NUM_LEVELS = 6;
    //limit of how many tries to generate board
    private static final int TRIES_LIMIT = 1000;
    //default values
    private static final int DEFAULT_BOARD_SIZE = 6;
    private static final int DEFAULT_CAR_LENGTH = 2;

    private static final int VERY_EASY = 0;
    private static final int EASY = 1;
    private static final int MEDIUM = 2;
    private static final int HARD = 3;
    private static final int VERY_HARD = 4;
    private static final int ULTRA_HARD = 5;

    private static final int[] MIN_MOVES = {7,10,15,20,25,30};

    public PuzzleGeneratorAStar() {
    }
    
    @Override
    public void generateAndAddPuzzles(PuzzleManager puzzleManager, int maxPuzzlesPerLevel) {
        int minMoves = 1;
        int[] numLeft = getNumLeft(puzzleManager, maxPuzzlesPerLevel);

        boolean[] canAdd = new boolean[NUM_LEVELS];
        for(int i = 0; i < NUM_LEVELS; i++) {
        	canAdd[i] = true;
        }

        PuzzleGame puzzle = this.generateRandomStart();
        while(this.sumNumLeft(numLeft) != 0) {
            puzzle = mostDifficultAdd(puzzle, minMoves);
			if (puzzle == null) {
				// could not find a more difficult puzzle, needs to be restarted
				minMoves = 0;
				puzzle = this.generateRandomStart();
				for(int i = 0; i < NUM_LEVELS; i++) {
					canAdd[i] = true;
				}
			} else {
				minMoves = puzzle.getMinMoves();
				
				for(int i = 0; i < NUM_LEVELS-1; i++) {
					if(minMoves >= MIN_MOVES[i] && minMoves < MIN_MOVES[i+1] && canAdd[i] == true && numLeft[i] != 0) {
						puzzleManager.addNewPuzzle(i, puzzle);
						canAdd[i] = false;
						numLeft[i]--;
						System.out.printf("%d%n", i);
					}
				}
				int lastIndex = NUM_LEVELS-1;
				if(minMoves >= MIN_MOVES[lastIndex] && canAdd[lastIndex] == true && numLeft[lastIndex] != 0) {
					puzzleManager.addNewPuzzle(lastIndex, puzzle);
					canAdd[lastIndex] = false;
					numLeft[lastIndex]--;
					puzzle.showBoard();
					System.out.printf("%d%n", lastIndex);
				}
				
			}
        }
    }
    
    private int[] getNumLeft(PuzzleManager puzzleManager, int maxPuzzlesPerLevel) {
    	int[] numLeft = new int[NUM_LEVELS];
    	for(int i = 0; i < NUM_LEVELS; i++) {
    		if(puzzleManager.getNumPuzzles(i) < maxPuzzlesPerLevel) {
    			numLeft[i] = maxPuzzlesPerLevel - puzzleManager.getNumPuzzles(i);
    		} else {
    			numLeft[i] = 0;
    		}
    	}
    	return numLeft;
    }

    private int sumNumLeft(int[] numLeft) {
    	int sum = 0;
    	for(int i = 0; i < NUM_LEVELS; i++) {
    		sum += numLeft[i];
    	}
    	return sum;
    }

    /**
     * Same as generatePuzzle(int,int,int,int,int)
     * Uses default board size
     * Generates a random exit
     * @param minMoves
     * @return
     * @throws PuzzleGenerationException 
     */
	@Override
    public PuzzleGame generatePuzzle(int minMoves) throws Exception {
		PuzzleGame puzzle = generatePuzzle(DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE, minMoves);
		return puzzle;
    }
	
	public PuzzleGame generatePuzzle(int width, int height, int minMoves) throws Exception {
        int movesRequired = 1;
        int tries = 0;
        //generate initial puzzle
        PuzzleGame puzzle = this.generateRandomStart();
        //Keep generating until we have a suitable puzzle
        while (movesRequired <= minMoves) {
            if (tries > TRIES_LIMIT) {
                //if tries exceeds tries limit then it is too difficult to generate
            	throw new Exception(String.format("A puzzle cannot be generated on a %dx%d board with a minimum of %d moves", width,height, minMoves));
            }
            //add random vehicle
            PuzzleGame newPuzzle = mostDifficultAdd(puzzle, movesRequired);
			if (newPuzzle == null) {
				//couldn't find more difficult puzzle, so restart
				movesRequired = 0;
				puzzle = this.generateRandomStart();
				tries++;
			} else {
				//System.out.println("found more difficult puzzle");
				movesRequired = newPuzzle.getMinMoves();
				puzzle = newPuzzle;
			}
        }
        return puzzle;
	}

    /**
     * Given the parameters generate a random puzzle using those parameters
     * It may be impossible to include numVehicles and moves as params as time to generate would be too long
     *
     * @param width,      the width of the board
     * @param height,     the height of the board
     * @param exitRow,    the row which the vehicle can exit
     * @param exitCol,    the col which the vehicle can exit
     * @param numVehicle, the number of vehicles on the board
     * @param move,       the number of moves required to complete the game
     * @return puzzleGame,
     */
    public PuzzleGame generatePuzzle(int width, int height, int exitRow, int exitCol, int moves) throws Exception {
        int movesRequired = 1;
        int tries = 0;
        //generate initial puzzle
        PuzzleGame puzzle = generateInitialState(width, height, exitRow, exitCol);
        //Keep generating until we have a suitable puzzle
        while (movesRequired <= moves) {
            if (tries > TRIES_LIMIT) {
                //if tries exceeds tries limit then it is too difficult to generate
            	throw new Exception(String.format("A puzzle cannot be generated on a %dx%d board with a minimum of %d moves", width,height,moves));
            }
            //add random vehicle
            PuzzleGame newPuzzle = mostDifficultAdd(puzzle, movesRequired);
			if (newPuzzle == null) {
				//couldn't find more difficult puzzle, so restart
				movesRequired = 0;
				puzzle = generateInitialState(width, height, exitRow, exitCol);
				tries++;
			} else {
				//System.out.println("found more difficult puzzle");
				movesRequired = newPuzzle.getMinMoves();
				puzzle = newPuzzle;
            	System.out.printf("min moves %d%n", puzzle.getMinMoves());
			}
        }
        System.out.printf("num tries: %d%n", tries);
        System.out.printf("min moves: %d%n", puzzle.getMinMoves());
        return puzzle;
    }
    


    /**
     * Find all possible locations for a vehicle, adds them one by one and sees if its harder.
     * Take the first puzzle that is harder
     *
     * @param puzzle
     * @param currentMoves, number of moves required to solve the current puzzle
     */
    private PuzzleGame mostDifficultAdd(PuzzleGame puzzle, int currentMoves) {
        //get all possible location for the vehicle
        //List<Vehicle> possibleVehicle = puzzle.getPossibleVehicle();
        List<Vehicle> possibleVehicle = puzzle.getPossibleIntersects();
        //shuffle for random outcome, is this correct
        Collections.shuffle(possibleVehicle);
        //add every possible vehicle to the board and see if it makes the game harder
        for (Vehicle vehicle : possibleVehicle) {
            //get a new board and add a random piece and see if its harder
            PuzzleGame newPuzzle = new PuzzleGame(puzzle);
            newPuzzle.addVehicle(vehicle);
            //get the number of moves required to solve puzzle
            List<int[][]> puzzleSolved = PuzzleSolver.solve(newPuzzle);
            if (puzzleSolved != null) {
                if (puzzleSolved.size() - 1 > currentMoves) {
                    //found a harder puzzle
                	newPuzzle.setMinMoves(puzzleSolved.size() - 1);
                    return newPuzzle;
                }
            }
        }
        //could not find a harder puzzle
        return null;
    }

	private PuzzleGame generateRandomStart() {
        List<Integer> exit = randomExit(6, 6);
        int exitRow = exit.get(0);
        int exitCol = exit.get(1);
        return generateInitialState(DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE, exitRow, exitCol);
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
//		Random rand = new Random();
//		int carCol = rand.nextInt(exitCol - 1);
		int carCol = 0;
		puzzle.addVehicle(isVertical, DEFAULT_CAR_LENGTH, carRow, carCol, Color.RED);
		puzzle.setMinMoves(1);
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
    	try {
			PuzzleGame puzzle = this.generatePuzzle(MIN_MOVES[VERY_EASY]);
			return puzzle;
    	} catch(Exception e) {
    		return null;
    	}
	}

    @Override
    public PuzzleGame generateEasyPuzzle() {
    	try {
			PuzzleGame puzzle = this.generatePuzzle(MIN_MOVES[EASY]);
			return puzzle;
    	} catch(Exception e) {
    		return null;
    	}
    }

    @Override
    public PuzzleGame generateMediumPuzzle() {
    	try {
			PuzzleGame puzzle = this.generatePuzzle(MIN_MOVES[MEDIUM]);
			return puzzle;
    	} catch(Exception e) {
    		return null;
    	}
    }

    @Override
    public PuzzleGame generateHardPuzzle() {
    	try {
			PuzzleGame puzzle = this.generatePuzzle(MIN_MOVES[HARD]);
			return puzzle;
    	} catch(Exception e) {
    		return null;
    	}
    }

    @Override
    public PuzzleGame generateVeryHardPuzzle() {
    	try {
			PuzzleGame puzzle = this.generatePuzzle(MIN_MOVES[VERY_HARD]);
			return puzzle;
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    @Override
    public PuzzleGame generateUltraHardPuzzle() {
    	try {
			PuzzleGame puzzle = this.generatePuzzle(MIN_MOVES[ULTRA_HARD]);
			return puzzle;
    	} catch(Exception e) {
    		return null;
    	}
    }
    
    
    public PuzzleGame generateMergedPuzzle(int numToMerge, int minMovesPerPuzzle) throws Exception {
        List<Integer> exit = randomExit(6, 6);
        int exitRow = exit.get(0);
        int exitCol = exit.get(1);
        PuzzleGame mainPuzzle = this.generatePuzzle(DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE, exitRow, exitCol, minMovesPerPuzzle);
        for(int i = 0; i < numToMerge; i++) {
        	PuzzleGame puzzle = this.generatePuzzle(DEFAULT_BOARD_SIZE, DEFAULT_BOARD_SIZE, exitRow, exitCol, minMovesPerPuzzle); 
        	mainPuzzle = this.puzzleMerge(mainPuzzle, puzzle);
        }
        return mainPuzzle;
    }
}





















