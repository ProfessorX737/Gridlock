import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class PuzzleGameTest {

	@Test
	public void test() throws IOException {
		PuzzleGeneratorAStar foo = new PuzzleGeneratorAStar();
		PuzzleGame bah = foo.generateEasyPuzzle();
		bah.savePuzzleGame("save_file");
	}

}
