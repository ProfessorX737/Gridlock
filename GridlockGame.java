import java.util.List;
import java.util.ArrayList;

public class GridlockGame {

	PuzzleGenerator puzzleGenerator;
	PuzzleManager puzzleManger;
	FileSystem fileSys;
	public GridlockGame() {
		FileSystem fileSys = new FileSystemImp();
		this.puzzleGenerator = new PuzzleGeneratorAStar();
		this.puzzleManger = new PuzzleManager(PuzzleGeneratorAStar.NUM_LEVELS);
		for(PuzzleGame puzzle : fileSys.loadPuzzlesFromFolder("puzzles/veryEasy/")) {
			this.puzzleManger.addExistingPuzzle(0, puzzle);
		}
		for(PuzzleGame puzzle : fileSys.loadPuzzlesFromFolder("puzzles/easy/")) {
			this.puzzleManger.addExistingPuzzle(1, puzzle);
		}
		for(PuzzleGame puzzle : fileSys.loadPuzzlesFromFolder("puzzles/medium/")) {
			this.puzzleManger.addExistingPuzzle(2, puzzle);
		}
		for(PuzzleGame puzzle : fileSys.loadPuzzlesFromFolder("puzzles/hard/")) {
			this.puzzleManger.addExistingPuzzle(3, puzzle);
		}
		for(PuzzleGame puzzle : fileSys.loadPuzzlesFromFolder("puzzles/veryHard/")) {
			this.puzzleManger.addExistingPuzzle(4, puzzle);
		}
		for(PuzzleGame puzzle : fileSys.loadPuzzlesFromFolder("puzzles/ultraHard/")) {
			this.puzzleManger.addExistingPuzzle(5, puzzle);
		}
	}

}
