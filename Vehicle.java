import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

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
	private final String color;
	
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
	public Vehicle(int id, boolean isVertical, int length, int row, int col, String color) {
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
	
	public String getColor() {
		return color;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	/**
	 * Return a collection of points which represents the positions that are
	 * taken by the vehicle.
	 * @return
	 */
	public Collection<Point> getTakenPos() {
		Collection<Point> takenPos = new ArrayList<Point>();
		if (getIsVertical()) {
			for (int i = 0; i < length; i++) {
				takenPos.add(new Point(getRow() + i, getCol()));
			}
		} else {
			for (int i = 0; i < length; i++) {
				takenPos.add(new Point(getRow(), getCol() + i));
			}
		}
		return takenPos;
	}
	
	public void setPos(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
