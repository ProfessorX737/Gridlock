//import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//import org.junit.Test;

public class BoardTest {

	//@Test
	public static void test() {
		Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
		vehicleMap.put(0, new Vehicle(0, true, 2, 0, 0, "Red"));
		vehicleMap.put(1, new Vehicle(1, false, 2, 3, 0, "Blue"));
		Board foo = new Board(5, 5, 4, 0, vehicleMap);
		foo.showBoard();
		System.out.println("");
		//assertFalse(foo.checkMove(0, 0, 1));
		//assertTrue(foo.checkMove(0, 2, 1));
		foo.moveVehicle(0, 2, 0);
		foo.showBoard();
		System.out.println("");
		foo.moveVehicle(0, 3, 0);
		foo.showBoard();
		System.out.println("");
		System.out.println(foo.isSolved());
	}
	
	public static void main(String[] args) {
		test();
	}

}
