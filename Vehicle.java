import java.awt.*;

/**
 * Represents vehicles on the board
 */
public class Vehicle {
    private final int id;
    private final boolean isVertical;
    private final int length;
    private final Color color;
    private int row;
    private int col;

    /**
     * Constructor for vehicles, takes the parameters
     *
     * @param id,         identifier for the vehicle, should be unique
     * @param isVertical, the orientation of the vehicle
     * @param length,     length of the vehicle, must be positive
     * @param row,        row coordinate
     * @param col,        column coordinate
     * @param color,      color of the car
     * @post
     */
    public Vehicle(int id, boolean isVertical, int length, int row, int col, Color color) {
        this.id = id;
        this.isVertical = isVertical;
        this.length = length;
        this.row = row;
        this.col = col;
        this.color = color;
    }

    // copy constructor
    public Vehicle(Vehicle v) {
        this.id = v.getID();
        this.isVertical = v.getIsVertical();
        this.length = v.getLength();
        this.row = v.getRow();
        this.col = v.getCol();
        this.color = v.getColor();
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

    public static Vehicle strToVehicle(String s){
        s = s.trim();
        String[] parts = s.split(" ");

        Boolean isVert = (Integer.getInteger(parts[0]) == 1) ? true : false;
        int length = Integer.parseInt(parts[1]);
        int row = Integer.parseInt(parts[2]);
        int col = Integer.parseInt(parts[3]);
        Color colour = (parts[4] == "red") ? Color.RED: Color.ORANGE;
        int id = Integer.getInteger(parts[5]);

        Vehicle v = new Vehicle(id, isVert, length, row, col, colour);
        return v;
    }
}
