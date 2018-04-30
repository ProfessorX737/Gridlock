import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class BoardTest {

	@Test
	public void test() {
		Collection<Vehicle> vehicleList = new ArrayList<Vehicle>();
		vehicleList.add(new Vehicle(0, true, 2, 0, 0, "Blue"));
		Board foo = new Board(5,5, vehicleList);
		foo.showBoard();
	}

}
