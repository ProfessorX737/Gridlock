import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * keep track of the current state of the game 
 * acts as nodes in the graph
 *
 */
public class GameState {
	private final Board board;
	private final int turns;
	private final Collection<Board> previousMoves;

	/**
	 * Initialize with no parameters
	 */
	public GameState() {
		Map<Integer, Vehicle> vehicleList = new HashMap<Integer, Vehicle>();
		board = new Board(5, 5, vehicleList);
		turns = 0;
		previousMoves = new ArrayList<Board>();
	}
	/**
	 * Initialize with parameters
	 * @param board
	 * @param turns
	 * @param previousMoves
	 */
	public GameState(Board board, int turns, Collection<Board> previousMoves) {
		this.board = board;
		this.turns = turns;
		this.previousMoves = previousMoves;
	}
	
	Board getBoard() {
		return board;
	}
	
	int getTurns() {
		return turns;
	}
	
	Collection<Board> getPreviousMoves() {
		return previousMoves;
	}
}
