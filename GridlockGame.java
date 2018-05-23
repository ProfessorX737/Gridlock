import java.util.List;
import java.util.ArrayList;

/**
 * Automatically loads the puzzles when instance of GridlockGame is created
 * @author Xavier Poon
 *
 */
public class GridlockGame {

	PuzzleGenerator puzzleGenerator;
	PuzzleManager puzzleManager;
	FileSystem fileSys;

	public final static int NUM_LEVELS = 6;
	private final static int MAX_PUZZLES_PER_LEVEL = 10;
	private final static String MAIN_FOLDER_NAME = "puzzles/";
	private final static String[] LEVEL_NAMES = {"veryEasy","easy","medium","hard","veryHard","ultraHard"};
    public final static int[] LEVEL_MIN_MOVES = {7,10,15,20,25,30};
    public static final int DEFAULT_BOARD_SIZE = 6;
    public static final int DEFAULT_CAR_LENGTH = 2;
    
    public static final int VERY_EASY = 0;
    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;
    public static final int VERY_HARD = 4;
    public static final int ULTRA_HARD = 5;

	public GridlockGame() {
		fileSys = new FileSystemImp();
		this.puzzleGenerator = new PuzzleGeneratorAStar();
		this.puzzleManager = new PuzzleManager(NUM_LEVELS);
		for(int i = 0; i < NUM_LEVELS; i++) {
			for(PuzzleGame puzzle : fileSys.loadPuzzlesFromFolder(MAIN_FOLDER_NAME + LEVEL_NAMES[i])) {
				this.puzzleManager.addExistingPuzzle(i, puzzle);
			}
		}
	}
	public void generatePuzzles() {
		this.puzzleGenerator.generateAndAddPuzzles(this.puzzleManager, MAX_PUZZLES_PER_LEVEL);
	}
	public PuzzleGame getPuzzle(int level, int puzzleId) {
		return this.puzzleManager.getPuzzle(level, puzzleId);
	}
	public List<PuzzleGame> getPuzzles(int level) {
		return this.puzzleManager.getPuzzles(level);
	}
	public void savePuzzles() {
		for(int i = 0; i < NUM_LEVELS; i++) {
			this.fileSys.savePuzzlesToFolder(this.puzzleManager.getPuzzles(i), MAIN_FOLDER_NAME + LEVEL_NAMES[i]);
		}
	}
}
