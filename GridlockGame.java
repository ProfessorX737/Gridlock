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
	private final static int MAX_PUZZLES_PER_LEVEL = 10;
	private final static String MAIN_FOLDER_NAME = "src/puzzles/";
	private final static String[] LEVEL_NAMES = {"veryEasy","easy","medium","hard","veryHard","ultraHard"};

	public GridlockGame() {
		FileSystem fileSys = new FileSystemImp();
		this.puzzleGenerator = new PuzzleGeneratorAStar();
		this.puzzleManager = new PuzzleManager(PuzzleGeneratorAStar.NUM_LEVELS);
		for(int i = 0; i < PuzzleGeneratorAStar.NUM_LEVELS; i++) {
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
		for(int i = 0; i < PuzzleGeneratorAStar.NUM_LEVELS; i++) {
			fileSys.savePuzzlesToFolder(this.puzzleManager.getPuzzles(i), MAIN_FOLDER_NAME + LEVEL_NAMES[i]);
		}
	}
}
