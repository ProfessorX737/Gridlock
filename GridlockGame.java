import java.awt.*;
import java.util.List;

/**
 * Automatically loads the puzzles when instance of GridlockGame is created
 * @author Xavier Poon
 *
 */
public class GridlockGame implements Runnable {
	private Thread puzzleGenThread;
	PuzzleGenerator puzzleGenerator;
	PuzzleManager puzzleManager;
	FileSystem fileSys;

	public final static int NUM_LEVELS = 6;
	private final static int MAX_PUZZLES_PER_LEVEL = 20;
	private final static String MAIN_FOLDER_NAME = "cs2511/puzzles/";
	public final static String[] LEVEL_NAMES = {"veryEasy","easy","medium","hard","veryHard","ultraHard"};
	public final static String[] DISPLAY_LEVEL_NAMES = {"Newbie","Beginner","Intermediate","Experienced","Advanced","Expert"};
    public final static int[] LEVEL_MIN_MOVES = {7,10,15,20,25,30};
    public static final int DEFAULT_BOARD_SIZE = 6;
    public static final int DEFAULT_CAR_LENGTH = 2;
    public static final Dimension MINIMUM_SIZE = new Dimension(PuzzleView.DEFAULT_CELL_SIZE * 11, PuzzleView.DEFAULT_CELL_SIZE * 17/2);
    
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
	/**
	 * generate puzzles until each level has the desired number of levels
	 */
	public void generatePuzzles() {
		this.puzzleGenerator.generateAndAddPuzzles(this.puzzleManager, MAX_PUZZLES_PER_LEVEL);
	}
	
	/**
	 * 
	 * @param level
	 * @param puzzleId
	 * @return
	 */
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
	@Override
	public void run() {
		this.generatePuzzles();
	}
	public void generatePuzzlesInBackground() {
		if(this.puzzleGenThread == null) {
			this.puzzleGenThread = new Thread(this,"PuzzleGenThread");
			this.puzzleGenThread.start();
		}
	}
}
