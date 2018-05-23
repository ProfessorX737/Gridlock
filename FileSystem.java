import java.util.List;

public interface FileSystem {
	
	/**
	 * @param puzzles, the puzzles to save
	 * @param folder, the folder to save to
	 */
	public void savePuzzlesToFolder(List<PuzzleGame> puzzles, String folder);
	/*
	 * Save the specified puzzle to a file with the given filename
	 * @param puzzle, the puzzle you want to save
	 * @param filename, the name for the file
	 */
	public void savePuzzleGame(PuzzleGame puzzle, String filename);
	
	/**
	 * @param folderPath, the folder to load puzzles from
	 * @return a list of PuzzleGame classes
	 */
	public List<PuzzleGame> loadPuzzlesFromFolder(String folderPath);
	
	/**
	 * Load a puzzle from a file
	 * @param filename, name of the file that you want to load the puzzle from.
	 * @return the puzzle in the saved file
	 */
	public PuzzleGame loadPuzzleGame(String filename);
}
