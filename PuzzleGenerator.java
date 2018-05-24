
public interface PuzzleGenerator {

	public void generateAndAddPuzzles(PuzzleManager puzzleManager, int maxPuzzlesPerLevel);
	public PuzzleGame generatePuzzle(int minMoves) throws Exception;
	
}
