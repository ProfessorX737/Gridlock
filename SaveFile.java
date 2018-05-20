import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Used to save the puzzle to a file 
 *
 */
public class SaveFile implements Serializable{
	//used for saving files
	private static final long serialVersionUID = 1L;
	//the game
	private PuzzleGame puzzle;
	//meta data
	private String filename;
	private LocalDateTime saveTime;
	private String user;
	
	/**
	 * Create file you need to specify the following.
	 * @param puzzle
	 * @param filename
	 */
	public SaveFile(PuzzleGame puzzle, String filename) {
		this.puzzle = puzzle;
		this.filename = filename;
		this.saveTime = LocalDateTime.now();
		this.user = "dude";
	}
	
	public PuzzleGame getPuzzleGame() {
		return this.puzzle;
	}
	
	public String getFilename() {
		return this.filename;
	}
	
	public LocalDateTime getSaveTime() {
		return this.saveTime;
	}
	
	public String getUser() {
		return this.user;
	}

}
