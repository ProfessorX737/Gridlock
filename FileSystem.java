import java.util.List;

public interface FileSystem {

	public void savePuzzlesToFolder(List<PuzzleGame> puzzles, String folder);
	/*
	 * Save the specified puzzle to a file with the given filename
	 * @param puzzle, the puzzle you want to save
	 * @param filename, the name for the file
	 */
	public void savePuzzleGame(PuzzleGame puzzle, String filename);
	
	public List<PuzzleGame> loadPuzzlesFromFolder(String folderPath);
	
	/**
	 * Load a puzzle from a file
	 * @param filename, name of the file that you want to load the puzzle from.
	 * @return the puzzle in the saved file
	 */
	public PuzzleGame loadPuzzleGame(String filename);
}
