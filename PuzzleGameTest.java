import static org.junit.Assert.*;


import org.junit.Test;

public class PuzzleGameTest {

	@Test
	public void test() {
		PuzzleGeneratorAStar foo = new PuzzleGeneratorAStar();
		PuzzleGame bah = foo.generateEasyPuzzle();
		bah.showBoard();
		FileSystemImp mak = new FileSystemImp();
		mak.savePuzzleGame(bah, "jim");
		PuzzleGame jim_save = mak.loadPuzzleGame("jim");
		jim_save.showBoard();
	}

}
