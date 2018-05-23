import java.util.*;

/**
 * Generate a puzzle using AStar.
 */
public class PuzzleGeneratorAStar implements PuzzleGenerator {
    //The main car will always be of length 2
    private static final int mainCarLength = 2;
    //Other cars can either be of size 2 or size 3
    private final static int[] carLength = new int[]{2, 3, 4};
    //private Collection<Integer> carLength;
    //limit of which to generate random vehicle
    private final static int randomLimit = 20;
    //limit of how many tries to generate board
    private final static int triesLimit = 1;
    //number of different puzzles to generate each time
    private final static int jumbleLimit = 10;
    //number of directions
    private final static int numDirections = 4;

    public PuzzleGeneratorAStar() {
    }

    /**
     * Given the parameters generate a random puzzle using those parameters
     * It may be impossible to include numVehicles and moves as params as time to generate would be too long
     *
     * @param width,   the width of the board
     * @param height,  the height of the board
     * @param exitRow, the row which the vehicle can exit
     * @param exitCol, the col which the vehicle can exit
     * @param moves,   the number of moves required to complete the game
     * @return puzzleGame,
     */
    @Override
    public PuzzleGame generatePuzzle(int width, int height, int exitRow, int exitCol, int moves) {
        int movesRequired = 1;
        int tries = 0;
        //generate initial puzzle
        PuzzleGame puzzle = generateInitialState(width, height, exitRow, exitCol);
        //Keep generating until we have a suitable puzzle
        while (movesRequired <= moves) {
            if (tries > triesLimit) {
                //if tries exceeds tries limit then it is too difficult to generate
                return null;
            }
            //add random vehicle
            PuzzleGame newPuzzle = mostDifficultAdd(puzzle, movesRequired);
            if (newPuzzle == null) {
                return puzzle;
            } else {
                //puzzle = jumbleVehicles(puzzle);
                List<int[][]> puzzleSolved = PuzzleSolver.solve(newPuzzle);
                if (puzzleSolved == null) {
                    //couldn't Solve puzzle restart
                    movesRequired = 0;
                    puzzle = generateInitialState(width, height, exitRow, exitCol);
                    tries++;
                } else {
                    movesRequired = puzzleSolved.size() - 1;
                    puzzle = newPuzzle;
                }
            }
        }
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
        List<Vehicle> possibleVehicle = puzzle.getPossibleVehicle();
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
                    return newPuzzle;
                }
            }
        }
        //could not find a harder puzzle
        return null;
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

        //generate random position for new vehicle
        int len = 0;
        int carID = 0;
        boolean isVertical = true;
        int carRow = 0;
        int carCol = 0;
        int col0 = 0;        //Colors of car
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
            if (isVertical && carCol != mainCol) {
                //vertical but not in same column
                if (puzzle.canAddVehicle(true, len, carRow, carCol)) {
                    puzzle.addVehicle(true, len, carRow, carCol, false);
                    return true;
                }
            } else if (!isVertical && carRow != mainRow) {
                //horizontal but not in same row
                if (puzzle.canAddVehicle(false, len, carRow, carCol)) {
                    puzzle.addVehicle(false, len, carRow, carCol, false);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Places the main vehicle in a random place on the map, place it at the end of the map.
     * The exit is randomly generated, although max difficulty puzzles might need the exit near the middle
     *
     * @param width,  the width of the board
     * @param height, the height of the board
     * @return
     */
    private PuzzleGame generateInitialState(int height, int width, int exitRow, int exitCol) {
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
        puzzle.addVehicle(isVertical, mainCarLength, carRow, carCol, true);
        return puzzle;
    }

    private List<Integer> randomExit(int height, int width) {
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
        List<Integer> exit = new ArrayList<Integer>();
        exit.add(exitRow);
        exit.add(exitCol);
        return exit;
    }

    /**
     * Merge two puzzles together, they must have similar dimensions.
     * If the exitRow is on the top, then the puzzle will be merged onto the top of the first puzzle.
     * Hence the width of both puzzles must be the same.
     * Likewise if the exit of the first puzzle is on the left, the second puzzle is added to the left,
     * and both puzzles must have the same height.
     * First and second puzzles are destoryed, afterwards. Don't reference again.
     *
     * @param first,  the first puzzle, other puzzles will be attached to this puzzle.
     * @param second, puzzle to be merged, the puzzle can include the red car which it will be removed.
     * @return
     * @pre first.height == second.height || first.width == second.wdith
     * @post first = null && second == null
     */
    private PuzzleGame puzzleMerge(PuzzleGame first, PuzzleGame second) {
        //getting exit of first and the width of both puzzles
        int firstExitRow = first.getExitRow();
        int firstExitCol = first.getExitCol();
        int firstHeight = first.getNumRows();
        int firstWidth = first.getNumCols();
        int secondHeight = second.getNumRows();
        int secondWidth = second.getNumCols();
        PuzzleGame mergedPuzzle = null;
        //remove the main car from the second puzzle
        second.removeVehicle(0);

        //determine where the exit is
        if (firstExitRow == 0) {
            //the exit is on the top
            mergedPuzzle = new PuzzleGame(firstHeight + secondHeight, firstWidth, firstExitRow, firstExitCol);
            //copy everything over from first
            for (Vehicle vehicle : first.getVehicles()) {
                //change the location
                vehicle.setRow(vehicle.getRow() + secondHeight);
                mergedPuzzle.addVehicle(vehicle);
            }
            //copy everything from the second
            for (Vehicle vehicle : second.getVehicles()) {
                //don't need to change location but need to change the id
                vehicle.setID(mergedPuzzle.getVehicles().size());
                mergedPuzzle.addVehicle(vehicle);
            }
        } else if (firstExitRow == firstHeight - 1) {
            //the exit is on the bottom
            mergedPuzzle = new PuzzleGame(firstHeight + secondHeight, firstWidth, firstHeight + secondHeight - 1,
                    firstExitCol);
            //copy everything over from first
            for (Vehicle vehicle : first.getVehicles()) {
                //no need to change anything just copy everything over
                mergedPuzzle.addVehicle(vehicle);
            }
            //copy everything from second and adjust both position and id
            for (Vehicle vehicle : second.getVehicles()) {
                vehicle.setRow(vehicle.getRow() + firstHeight);
                vehicle.setID(mergedPuzzle.getVehicles().size());
                mergedPuzzle.addVehicle(vehicle);
            }
        } else if (firstExitCol == 0) {
            //the exit is on the left
            mergedPuzzle = new PuzzleGame(firstHeight, firstWidth + secondWidth, firstExitRow, firstExitCol);
            //copy everything over from first
            for (Vehicle vehicle : first.getVehicles()) {
                //change the position of the col
                vehicle.setCol(vehicle.getCol() + secondWidth);
                mergedPuzzle.addVehicle(vehicle);
            }
            //copy everything from second
            for (Vehicle vehicle : second.getVehicles()) {
                //change the id
                vehicle.setID(mergedPuzzle.getVehicles().size());
                mergedPuzzle.addVehicle(vehicle);
            }
        } else if (firstExitCol == firstWidth - 1) {
            //the exit is on the right
            mergedPuzzle = new PuzzleGame(firstHeight, firstWidth + secondWidth, firstExitRow, firstWidth +
                    secondWidth - 1);
            //copy over everything from first no need to change anything
            for (Vehicle vehicle : first.getVehicles()) {
                mergedPuzzle.addVehicle(vehicle);
            }
            //copy from the second puzzle and change the id
            for (Vehicle vehicle : second.getVehicles()) {
                vehicle.setID(mergedPuzzle.getVehicles().size());
                vehicle.setCol(vehicle.getCol() + firstWidth);
            }
        }
        return mergedPuzzle;
    }

    @Override
    public PuzzleGame generateEasyPuzzle() {
        List<Integer> exit = randomExit(6, 6);
        int exitRow = exit.get(0);
        int exitCol = exit.get(1);
        return generatePuzzle(6, 6, exitRow, exitCol, 20);
    }

    /**
     * Generate a 12x6 puzzle.
     */
    @Override
    public PuzzleGame generateMediumPuzzle() {
        List<Integer> exit = randomExit(6, 6);
        int exitRow = exit.get(0);
        int exitCol = exit.get(1);
        //generate two different puzzles and merge them together
        PuzzleGame mainPuzzle = generatePuzzle(6, 6, exitRow, exitCol, 20);
        PuzzleGame secondPuzzle = generatePuzzle(6, 6, exitRow, exitCol, 20);
        return puzzleMerge(mainPuzzle, secondPuzzle);
    }

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

    public PuzzleGame generateSpicyPuzzle() {
        List<Integer> exit = randomExit(5, 7);
        int exitRow = exit.get(0);
        int exitCol = exit.get(1);
        //generate two different puzzles and merge them together
        PuzzleGame mainPuzzle = generatePuzzle(5, 7, exitRow, exitCol, 20);
        PuzzleGame secondPuzzle = generatePuzzle(5, 7, exitRow, exitCol, 20);
        return puzzleMerge(mainPuzzle, secondPuzzle);
    }
}





















