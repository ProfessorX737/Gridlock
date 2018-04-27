import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {

	@Test
	public void test() {
		Board foo = new Board(5,5);
		foo.showBoard();
	}

}
