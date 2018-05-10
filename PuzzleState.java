import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class PuzzleState implements TreeNode<PuzzleState> {
	private PuzzleGame game;
	
	public PuzzleState(PuzzleGame game) {
		this.game = game;
	}

	@Override
	public int getWeightTo(PuzzleState to) {
		return 1;
	}

	@Override
	public List<PuzzleState> getConnections() {
		List<PuzzleState> conns = new ArrayList<>();
		for(Vehicle v : this.game.getVehicles()) {
			for(int i = 1; i <= this.game.canMoveUp(v.getID()); i++) {
				PuzzleGame gameClone = new PuzzleGame(this.game);
				gameClone.moveVehicle(v.getID(), v.getRow()-i, v.getCol());
				PuzzleState conn = new PuzzleState(gameClone);
				conns.add(conn);
			}
			for(int i = 1; i <= this.game.canMoveDown(v.getID()); i++) {
				PuzzleGame gameClone = new PuzzleGame(this.game);
				gameClone.moveVehicle(v.getID(), v.getRow()+i, v.getCol());
				PuzzleState conn = new PuzzleState(gameClone);
				conns.add(conn);
			}
			for(int i = 1; i <= this.game.canMoveLeft(v.getID()); i++) {
				PuzzleGame gameClone = new PuzzleGame(this.game);
				gameClone.moveVehicle(v.getID(), v.getRow(), v.getCol()-i);
				PuzzleState conn = new PuzzleState(gameClone);
				conns.add(conn);
			}
			for(int i = 1; i <= this.game.canMoveRight(v.getID()); i++) {
				PuzzleGame gameClone = new PuzzleGame(this.game);
				gameClone.moveVehicle(v.getID(), v.getRow(), v.getCol()+i);
				PuzzleState conn = new PuzzleState(gameClone);
				conns.add(conn);
			}
		}
		return conns;
	}
	
	public int[][] getBoard() {
		return this.game.getBoard();
	}
	
	public boolean isSolved() {
		return this.game.isSolved();
	}
	
	public int getExitRow() {
		return this.game.getExitRow();
	}

	public int getExitCol() {
		return this.game.getExitCol();
	}
	
	public Vehicle getMainVehicle() {
		return this.game.getMainVehicle();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((game == null) ? 0 : game.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuzzleState other = (PuzzleState) obj;
		if (game == null) {
			if (other.game != null)
				return false;
		}
		if(this.game.isSolved() && other.game.isSolved()) {
			return true;
		}
		if (!game.equals(other.game))
			return false;
		return true;
	}
	
	//Used for printing out to the console
	private final static String red_car = "r";
	private final static String road = "-";
	private final static String wall = "W";

	void showBoard(int[][] board) {
		for (int y = -1; y <= game.getNumRows(); y++) {
			for (int x = -1; x <= game.getNumCols(); x++) {
				if (y == -1 || y == game.getNumRows() || x == -1 || x == game.getNumCols()) {
					System.out.print(wall);
				} else if (board[y][x] == 0) {
					System.out.print(red_car);
				} else if (board[y][x] == -1) {
					System.out.print(road);
				} else {
					System.out.print(board[y][x]);
				}
			}
			System.out.println("");
		}
	}
}