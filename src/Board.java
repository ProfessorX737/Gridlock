import java.awt.Point;
import java.util.Collection;

/**
 * represent the current board of the game
 */
public class Board {
	private int sizeX;
	private int sizeY;
	private Collection<Vehicle> vehicleList;
	private char[][] board;

	static String red_car = "🚗";
	static String road = "⬛";

	public Board(int sizeX, int sizeY, Collection<Vehicle> vehicleList) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.vehicleList = vehicleList;//need to fix this as it requires copy constructor
		board = new char[sizeX][sizeY];
		for (int y = 0; y < sizeY; y++) {
			for (int x = 0; x < sizeX; x++) {
				board[x][y]= '#';
			}
		}
		for (Vehicle vehicle : vehicleList) {
			Collection<Point> takenPos = vehicle.getTakenPos();
			for(Point point : takenPos) {
				//weird stuff will change that
				board[(int)point.getX()][(int)point.getY()] = (char) (vehicle.getID() + '0');
			}
		}
	}
	
	public boolean isOccupied(int row, int col) {
		if (board[row][col] == '#') {
			return true;
		} else {
			return false;
		}
	}
	
	void showBoard() {
		for (int y = 0; y < sizeY; y++) {
			for (int x = 0; x < sizeX; x++) {
				if (board[x][y] == '0') {
					System.out.print(red_car + "\t");
				} else if (board[x][y] == '#') {
					System.out.print(road + "\t");
				} else {
					System.out.print(board[x][y] + "\t");
				}
			}
			System.out.println("");
		}
	}

}
