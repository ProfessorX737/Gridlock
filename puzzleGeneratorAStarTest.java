import static org.junit.Assert.*;

import org.junit.Test;

public class puzzleGeneratorAStarTest {

	@Test
	public void test() {
		Heuristic h = new ZeroHeuristic();
		puzzleGeneratorAStar foo = new puzzleGeneratorAStar(h);
		PuzzleGame bah = foo.generatePuzzle(5, 5, 0, 3, 6, 5);
		bah.showBoard();
	}

}
