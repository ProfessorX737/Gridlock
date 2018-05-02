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
	
	public static void testCanMove() {
		Map<Integer, Vehicle> vehicleMap = new HashMap<Integer, Vehicle>();
		Vehicle v1 = new Vehicle(0, true, 2, 1, 0, "Red");
		Vehicle v2 = new Vehicle(1, false, 2, 1, 1, "Red");
		vehicleMap.put(0, v1);
		vehicleMap.put(1, v2);
		Board foo = new Board(5,5,0,0,vehicleMap);
		foo.showBoard();
		System.out.printf("canMoveUp %d%n", foo.canMoveUp(v1.getID()));
		System.out.printf("canMoveDown %d%n", foo.canMoveDown(v1.getID()));
		System.out.printf("canMoveLeft %d%n", foo.canMoveLeft(v1.getID()));
		System.out.printf("canMoveRight %d%n", foo.canMoveRight(v1.getID()));
		System.out.println("");
		System.out.printf("canMoveUp %d%n", foo.canMoveUp(v2.getID()));
		System.out.printf("canMoveDown %d%n", foo.canMoveDown(v2.getID()));
		System.out.printf("canMoveLeft %d%n", foo.canMoveLeft(v2.getID()));
		System.out.printf("canMoveRight %d%n", foo.canMoveRight(v2.getID()));
		
	}
	
	public static void main(String[] args) {
		//test();
		testCanMove();
	}

}
