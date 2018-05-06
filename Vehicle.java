import java.awt.Color;

/**
 * Represents vehicles on the board 
 *
 */
public class Vehicle {
	private final int id;
	private final boolean isVertical;
	private final int length;
	private int row;
	private int col;
	private final Color color;
	
	/**
	 * Constructor for vehicles, takes the parameters
	 * @post 
	 * @param id, identifier for the vehicle, should be unique
	 * @param isVertical, the orientation of the vehicle
	 * @param length, length of the vehicle, must be positive
	 * @param row, row coordinate
	 * @param col, column coordinate
	 * @param color, color of the car
	 */
	public Vehicle(int id, boolean isVertical, int length, int row, int col, Color color) {
		this.id = id;
		this.isVertical = isVertical;
		this.length = length;
		this.row = row;
		this.col = col;
		this.color = color;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean getIsVertical() {
		return isVertical;
	}
	
	public int getLength() {
		return length;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void setPos(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
