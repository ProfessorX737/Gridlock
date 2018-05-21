import java.awt.Color;
import java.util.List;

public class TestGetIntersects {
	public static void main(String[] args) {
		PuzzleGame puzzleGame = new PuzzleGame(6,6,2,5);
		puzzleGame.addVehicle(true, 2, 2, 2, Color.RED);
		puzzleGame.showBoard();
		System.out.println("");
		List<Vehicle> l = puzzleGame.getPossibleIntersects(0, 3);
		for(Vehicle v : l) {
			System.out.printf("%d,%d%n",v.getRow(),v.getCol());
		}
	}
}
