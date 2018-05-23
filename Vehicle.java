import java.awt.*;
import java.io.Serializable;

/**
 * Represents vehicles on the board
 */
public class Vehicle implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private boolean isVertical;
	private final int length;
	private Color color;
	private int row;
	private int col;
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
	
	public void setIsVertical(boolean isVertical) {
		this.isVertical = isVertical;
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
	
	@Override
	public String toString() {
		String x = "" + this.id + "," + this.isVertical + "," 
					+ this.length + "," + this.row + ","
					+ this.col + "," + this.color.toString() + ":";
		return x;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + (isVertical ? 1231 : 1237);
		result = prime * result + length;
		result = prime * result + row;
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
		Vehicle other = (Vehicle) obj;
		if (col != other.col)
			return false;
		if (isVertical != other.isVertical)
			return false;
		if (length != other.length)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
}

































