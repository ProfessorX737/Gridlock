import java.awt.Color;

/**
 * Represents vehicles on the board 
 *
 */
public class Vehicle {
	private int id;
	private final boolean isVertical;
	private final int length;
	private int row;
	private int col;
	private Color color;
	
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
	
	/**
	 * Copy constructor
	 * @return
	 */
	public Vehicle(Vehicle v) {
		this.id = v.id;
		this.isVertical = v.isVertical;
		this.length = v.length;
		this.row = v.row;
		this.col = v.col;
		this.color = v.color;
	}
	
	/**
	 * Set ID for vehicle
	 * @param ID
	 */
	public void setID(int ID) {
		this.id = ID;
	}
	
	/**
	 * Set the color of the vehicle
	 * @param color
	 */
	public void setColor(Color color) {
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
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * Given the row and col check whether the vehicle takes up that space.
	 * @pre isOutBounds(row, col) == false
	 * @post true
	 * @param row
	 * @param col
	 * @return
	 */
	public boolean isOccupy(int row, int col) {
		if (this.isVertical) {
			//the vehicle is vertical
			if (this.col == col) {
				if (this.row >= row && row < this.row + this.length) {
					return true;
				}
			}
		} else {
			if (this.row == row) {
				if (this.col >= col && col < this.col + this.length) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String x = "Vehicle ID: " + this.id + ", isVertical: " + 
					this.isVertical + ", length: " + this.length + 
					", row: " + this.row + ", col: " + this.col;
		return x;
	}
}

































