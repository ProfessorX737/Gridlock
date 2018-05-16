import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class PuzzleGameTest {

	@Test
	public void testgetPossibleVehicle() {
		//generate new board with only main car
		PuzzleGame foo = new PuzzleGame(4, 4, 0, 2);
		foo.addVehicle(true, 2, 2, 2, new Color(0,0,0));
		foo.showBoard();
		foo.getPossibleVehicle();
		for (Vehicle vehicle : foo.getPossibleVehicle()) {
			System.out.println(vehicle.toString());
		}
	}

}
