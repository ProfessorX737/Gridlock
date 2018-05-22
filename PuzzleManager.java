import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class PuzzleManager {
	private final int numPuzzleTypes;
	Map<Integer,PuzzleGame>[] puzzles;
	private int numPuzzles;

	public PuzzleManager(int numPuzzleTypes) {
		this.numPuzzleTypes = numPuzzleTypes;
		this.numPuzzles = 0;
		puzzles = new LinkedHashMap[this.numPuzzleTypes];
		for(int i = 0; i < this.numPuzzleTypes; i++) {
			puzzles[i] = new LinkedHashMap<>();
		}
	}

	/**
	 * Generates an id for the given puzzle and adds it to the correct map given by typeIndex
	 * @pre typeIndex < numPuzzleTypes, typeIndex >= 0
	 * @param typeIndex, which puzzle list to add to
	 * @param puzzle
	 */
	public void addNewPuzzle(int typeIndex, PuzzleGame puzzle) {
		int id = this.getUniquePuzzleId(typeIndex);
		puzzle.setId(id);
		this.puzzles[typeIndex].put(id, puzzle);
		this.numPuzzles++;
		return;
	}
	
	/**
	 * Uses the id of the given puzzle and adds it to the correct map
	 * @pre typeIndex < numPuzzleTypes, typeIndex >= 0
	 * @param typeIndex
	 * @param puzzle
	 */
	public void addExistingPuzzle(int typeIndex, PuzzleGame puzzle) {
		this.puzzles[typeIndex].put(puzzle.getId(), puzzle);
		this.numPuzzles++;
		return;
	}
	
	private int getUniquePuzzleId(int typeIndex) {
		int id = 0;
		while(this.puzzles[typeIndex].containsKey(id)) {
			id++;
		}
		return id;
	}

	public List<PuzzleGame> getPuzzles(int typeIndex) {
		return (List<PuzzleGame>) this.puzzles[typeIndex].values();
	}
	
	/**
	 * @pre typeIndex < numPuzzleTypes, typeIndex >= 0
	 * @param typeIndex
	 * @param puzzleId
	 * @return the puzzle with the puzzleId or null of puzzleId does not exist
	 */
	public PuzzleGame getPuzzle(int typeIndex, int puzzleId) {
		if(this.puzzles[typeIndex].containsKey(puzzleId)) {
			return this.puzzles[typeIndex].get(puzzleId);
		}
		return null;
	}

	/**
	 * @param puzzleId
	 * @return the puzzle with the puzzleId or null of puzzleId does not exist
	 */
	public PuzzleGame getPuzzle(int puzzleId) {
		for(int i = 0; i < this.numPuzzleTypes; i++) {
			if(this.puzzles[i].containsKey(puzzleId)) {
				return this.puzzles[i].get(puzzleId);
			}
		}
		return null;
	}
	
	public int getNumPuzzles(int typeIndex) {
		return this.puzzles[typeIndex].size();
	}
	
}
