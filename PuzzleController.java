import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * The C in the MVC, controls the board by listening to mouse input and mouse motion input
 */
public class PuzzleController extends MouseAdapter {
	private PuzzleView view;
	private PuzzleGame puzzleGame;
	private final int cellSize;
	private ButtonController bc; // <== Needed to update moves when a vehicle is placed
	// variables for the currently selected vehicle which is set when the mouse is pressed
// ===========================
	private int currentVehicleID;
	// freedom of movement of the current vehicle:
	private int rightSpace;
	private int leftSpace;
	private int upSpace;
	private int downSpace;
	// The orientation of the current vehicle
	private boolean isVertical;
	// Store the position of the current vehicle when the mouse is pressed
	private int x;
	private int y;
	// ===========================
// Store the position of the mouse on the screen when the mouse is pressed
	private int screenX;
	private int screenY;

	public PuzzleController(PuzzleGame puzzleGame, PuzzleView puzzleView) {
		this.view = puzzleView;
		this.puzzleGame = puzzleGame;
		this.cellSize = puzzleView.getCellLength();
		// set current vehicle id to -1, meaning no vehicle is currently selected
		this.currentVehicleID = -1;
		this.bc = null;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.currentVehicleID == -1) return;
		// Calculate how much the mouse has moved while mouse is pressed
		int deltaX = e.getXOnScreen() - screenX;
		int deltaY = e.getYOnScreen() - screenY;
		JComponent vc = this.view.getVehicle(this.currentVehicleID);

		// Set the new vehicle location on the board view
		if(this.isVertical) {
			if(deltaY <= this.downSpace && deltaY >= -this.upSpace) {
				this.view.setVehicleLocation(this.currentVehicleID, this.x, this.y + deltaY);
			} else if(vc.getY() <= this.y + this.downSpace && vc.getY() >= this.y - this.upSpace) {
				// Handle the case if the user moves the vehicle too fast for vertical vehicles
				if(deltaY < 0) {
					this.view.setVehicleLocation(this.currentVehicleID, this.x, this.y - this.upSpace);
				} else {
					this.view.setVehicleLocation(this.currentVehicleID, this.x, this.y + this.downSpace);
				}
			}
		} else {
			if(deltaX <= this.rightSpace && deltaX >= -this.leftSpace) {
				this.view.setVehicleLocation(this.currentVehicleID, this.x + deltaX, this.y);
			} else if(vc.getX() <= this.x + this.rightSpace && vc.getX() >= this.x - this.leftSpace) {
				// Handle the case if the user moves the vehicle too fast for horizontal vehicles
				if(deltaX < 0) {
					this.view.setVehicleLocation(this.currentVehicleID, this.x - this.leftSpace, this.y);
				} else {
					this.view.setVehicleLocation(this.currentVehicleID, this.x + this.rightSpace, this.y);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * When the mouse is pressed:
	 * Set the current vehicle id and position
	 * Calculate the freedom of movement of the current vehicle
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		this.screenX = e.getXOnScreen();
		this.screenY = e.getYOnScreen();
		int row = e.getY() / this.view.getCellLength();
		int col = e.getX() / this.view.getCellLength();
		if((this.currentVehicleID = this.puzzleGame.getVehicleIDAtLocation(row, col)) == -1) {
			return;
		}
		JComponent vc = this.view.getVehicle(this.currentVehicleID);
		this.x = vc.getX();
		this.y = vc.getY();
		this.rightSpace = this.puzzleGame.canMoveRight(this.currentVehicleID) * this.cellSize;
		this.leftSpace = this.puzzleGame.canMoveLeft(this.currentVehicleID) * this.cellSize;
		this.upSpace = this.puzzleGame.canMoveUp(this.currentVehicleID) * this.cellSize;
		this.downSpace = this.puzzleGame.canMoveDown(this.currentVehicleID) * this.cellSize;
		this.isVertical = this.puzzleGame.isVehicleVertical(this.currentVehicleID);
	}

	/**
	 * When the mouse is released:
	 * snap the current vehicle's location to the closest matching cells in the view and update the game
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.currentVehicleID == -1) return;
		JComponent vc = this.view.getVehicle(this.currentVehicleID);
		int row = vc.getY() / this.view.getCellLength();
		int col = vc.getX() / this.view.getCellLength();
		if(this.isVertical) {
			if(vc.getY() % this.cellSize <= this.cellSize / 2) {
				this.view.setVehicleLocation(this.currentVehicleID, vc.getX(), row * this.cellSize);
				this.puzzleGame.moveVehicle(this.currentVehicleID, row, col);
			} else {
				this.view.setVehicleLocation(this.currentVehicleID, vc.getX(), (row+1)* this.cellSize);
				this.puzzleGame.moveVehicle(this.currentVehicleID, row+1, col);
			}
		} else {
			if(vc.getX() % this.cellSize <= this.cellSize / 2) {
				this.view.setVehicleLocation(this.currentVehicleID, col * this.cellSize, vc.getY());
				this.puzzleGame.moveVehicle(this.currentVehicleID, row, col);
				//System.out.printf("same %d %d%n",row, col);
			} else {
				this.view.setVehicleLocation(this.currentVehicleID, (col+1) * this.cellSize, vc.getY());
				this.puzzleGame.moveVehicle(this.currentVehicleID, row, col+1);
				//System.out.printf("next %d %d%n",row, col+1);
			}
		}
		updateMoves();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void setButtonController(ButtonController bc){
		this.bc = bc;
	}

	private void updateMoves(){
		bc.updateMoves();
	}

}
