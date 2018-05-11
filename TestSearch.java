import java.awt.Color;
import java.util.List;

public class TestSearch {
	public static void test1() {
		PuzzleGame puzzleGame = new PuzzleGame(6,6,2,5);
		puzzleGame.addVehicle(false, 2, 2, 0, Color.RED);
		puzzleGame.addVehicle(false, 3, 0, 0, Color.ORANGE);
		puzzleGame.addVehicle(true, 3, 1, 2, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 3, 0, Color.ORANGE);
		puzzleGame.addVehicle(false, 3, 5, 0, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 4, 4, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 3, 4, Color.ORANGE);
		puzzleGame.addVehicle(true, 3, 0, 5, Color.ORANGE);
		PuzzleGame goal = new PuzzleGame(6,6,2,5);
		goal.addVehicle(false, 2, 2, 5, Color.RED);
		Heuristic<PuzzleState> h = new PuzzleHeuristic();
		Graph<PuzzleState> stateGraph = new TreeGraph<>();
		ShortestPathSearch<PuzzleState> search = new AStar<>(stateGraph,h);
		List<PuzzleState> path = search.shortestPath(new PuzzleState(puzzleGame), new PuzzleState(goal));
		System.out.printf("%d%n",search.getNumExpanded());
		System.out.printf("path cost = %d%n", search.getFinalCost());
		if(path == null) {
			System.out.println("null");
			return;
		}
		for(PuzzleState state : path) {
			showBoard(puzzleGame, state.getBoard());
			System.out.println("");
		}
	}
	public static void test2() {
		PuzzleGame puzzleGame = new PuzzleGame(6,6,2,5);
		puzzleGame.addVehicle(false, 2, 2, 2, Color.RED);
		puzzleGame.addVehicle(false, 3, 0, 0, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 0, 3, Color.ORANGE);
		puzzleGame.addVehicle(true, 3, 0, 4, Color.ORANGE);
		puzzleGame.addVehicle(true, 3, 0, 5, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 1, 0, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 1, 1, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 3, 0, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 3, 2, Color.ORANGE);
		puzzleGame.addVehicle(true, 2, 4, 1, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 5, 2, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 4, 4, Color.ORANGE);
		puzzleGame.addVehicle(false, 2, 5, 4, Color.ORANGE);
		PuzzleGame goal = new PuzzleGame(6,6,2,5);
		goal.addVehicle(false, 2, 2, 5, Color.RED);
		Heuristic<PuzzleState> h = new PuzzleHeuristic();
		Graph<PuzzleState> stateGraph = new TreeGraph<>();
		ShortestPathSearch<PuzzleState> search = new AStar<>(stateGraph,h);
		List<PuzzleState> path = search.shortestPath(new PuzzleState(puzzleGame), new PuzzleState(goal));
		System.out.printf("%d%n",search.getNumExpanded());
		System.out.printf("path cost = %d%n", search.getFinalCost());
		if(path == null) {
			System.out.println("null");
			return;
		}
		for(PuzzleState state : path) {
			showBoard(puzzleGame, state.getBoard());
			System.out.println("");
		}
	}
	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		test2();
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) );
	}
	private final static String red_car = "r";
	private final static String road = "-";
	private final static String wall = "W";

	static void showBoard(PuzzleGame game, int[][] board) {
		for (int y = -1; y <= game.getNumRows(); y++) {
			for (int x = -1; x <= game.getNumCols(); x++) {
				if (y == -1 || y == game.getNumRows() || x == -1 || x == game.getNumCols()) {
					System.out.print(wall);
				} else if (board[y][x] == 0) {
					System.out.print(red_car);
				} else if (board[y][x] == -1) {
					System.out.print(road);
				} else {
					System.out.print(Integer.toHexString(board[y][x]));
				}
			}
			System.out.println("");
		}
	}
}
