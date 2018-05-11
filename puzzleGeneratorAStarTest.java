import static org.junit.Assert.*;

import org.junit.Test;

public class puzzleGeneratorAStarTest {

	@Test
	public void test() {
		puzzleGeneratorAStar foo = new puzzleGeneratorAStar();
		PuzzleGame bah = foo.generatePuzzle(5, 5, 0, 3, 6, 8);
		if (bah == null) {
			System.out.println("Could not generate puzzle");
		} else {
			bah.showBoard();
		}
	}

}
