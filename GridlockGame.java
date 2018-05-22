import java.util.List;
import java.util.ArrayList;

public class GridlockGame {
	PuzzleGenerator puzzleGenerator;
	PuzzleManager puzzleManger;
	FileSystem fileSys;
	private final static int MAX_PUZZLES_PER_LEVEL = 10;
	private final static String MAIN_FOLDER_NAME = "src/puzzles/";
	private final static String[] LEVEL_NAMES = {"veryEasy","easy","medium","hard","veryHard","ultraHard"};

	public GridlockGame() {
		FileSystem fileSys = new FileSystemImp();
		this.puzzleGenerator = new PuzzleGeneratorAStar();
		this.puzzleManger = new PuzzleManager(PuzzleGeneratorAStar.NUM_LEVELS);
		for(int i = 0; i < PuzzleGeneratorAStar.NUM_LEVELS; i++) {
			for(PuzzleGame puzzle : fileSys.loadPuzzlesFromFolder(MAIN_FOLDER_NAME + LEVEL_NAMES[i])) {
				this.puzzleManger.addExistingPuzzle(i, puzzle);
			}
		}
	}
	public void generatePuzzles() {
		this.puzzleGenerator.generateAndAddPuzzles(this.puzzleManger, MAX_PUZZLES_PER_LEVEL);
	}

}
