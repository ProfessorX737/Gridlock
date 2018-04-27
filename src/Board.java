
/**
 * represent the current board of the game
 */
public class Board {
	private final int sizeX;
	private final int sizeY;
	private char[][] board;

	public Board(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		board = new char[sizeX][sizeY];
		for (int y = 0; y < sizeY; y++) {
			for (int x = 0; x < sizeX; x++) {
				board[x][y]= '#';
			}
		}
	}
	
	void showBoard() {
		for (int y = 0; y < sizeY; y++) {
			for (int x = 0; x < sizeX; x++) {
				System.out.print(board[x][y]);
			}
			System.out.println("");
		}
	}

}
