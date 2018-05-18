import java.awt.Color;
import java.util.List;

public class TestGetIntersects {
	public static void main(String[] args) {
		PuzzleGame puzzleGame = new PuzzleGame(6,6,2,5);
		puzzleGame.addVehicle(false, 2, 1, 2, Color.RED);
		puzzleGame.showBoard();
		List<Vehicle> l = puzzleGame.getPossibleIntersects(0, 3);
		for(Vehicle v : l) {
			System.out.printf("%d,%d%n",v.getRow(),v.getCol());
		}
	}
}
