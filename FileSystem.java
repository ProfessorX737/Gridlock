import java.util.List;

/**
 * Interface for saving and loading puzzles.
 *
 */
public interface FileSystem {
	
	/**
	 * Save a list of puzzles to a directory given a path to the directory.
	 * @pre folderPath != null
	 * @post file.getPath(folderPath.puzzles) == true
	 * @param puzzles the puzzles to be saved
	 * @param folderPath the path to the directory which the puzzles will be saved to
	 */
	public void savePuzzlesToFolder(List<PuzzleGame> puzzles, String folder);

	/**
	 * Save the specified puzzle to a file with the given filename
	 * @pre filename != null
	 * @post file.getPath(filename.puzzle) == true
	 * @param puzzle the puzzle you want to save
	 * @param filename the name for the file
	 */
	public void savePuzzleGame(PuzzleGame puzzle, String filename);
	
	/**
	 * Returns a list of puzzles given the path to the folder.
	 * @pre folderPath != null
	 * @post true
	 * @param folderPath, the folder to load puzzles from
	 * @return a list of PuzzleGame puzzles
	 */
	public List<PuzzleGame> loadPuzzlesFromFolder(String folderPath);
	
	/**
	 * Load a puzzle from a file.
	 * @pre filename != false
	 * @post true
	 * @param filename, name of the file that you want to load the puzzle from.
	 * @return the puzzle in the saved file
	 */
	public PuzzleGame loadPuzzleGame(String filename);
}
