import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class BoardController extends MouseAdapter {
	private BoardView view;
	private Game game;
	private final int cellSize;
	private int currentVehicleID;
	private int rightSpace;
	private int leftSpace; 
	private int upSpace;
	private int downSpace;
	private int x;
	private int y;
	private int screenX;
	private int screenY;
	private boolean isVertical;

	public BoardController(Game game, BoardView boardView) {
		this.view = boardView;
		this.game = game;
		this.cellSize = boardView.getCellLength();
		this.screenX = 0;
		this.screenY = 0;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if(this.currentVehicleID == -1) return;
        int deltaX = e.getXOnScreen() - screenX;
        int deltaY = e.getYOnScreen() - screenY;
		JComponent vc = this.view.getVehicle(this.currentVehicleID);
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

	@Override
	public void mousePressed(MouseEvent e) {
		this.screenX = e.getXOnScreen();
		this.screenY = e.getYOnScreen();
        int row = e.getY() / this.view.getCellLength();
        int col = e.getX() / this.view.getCellLength();
        if((this.currentVehicleID = this.game.getVehicleIDAtLocation(row, col)) == -1) {
        		return;
        }
        JComponent vc = this.view.getVehicle(this.currentVehicleID);
        this.x = vc.getX();
        this.y = vc.getY();
        this.rightSpace = this.game.canMoveRight(this.currentVehicleID) * this.cellSize;
        this.leftSpace = this.game.canMoveLeft(this.currentVehicleID) * this.cellSize;
        this.upSpace = this.game.canMoveUp(this.currentVehicleID) * this.cellSize;
        this.downSpace = this.game.canMoveDown(this.currentVehicleID) * this.cellSize;
        this.isVertical = this.game.isVehicleVertical(this.currentVehicleID);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(this.currentVehicleID == -1) return;
		JComponent vc = this.view.getVehicle(this.currentVehicleID);
		int row = vc.getY() / this.view.getCellLength();
		int col = vc.getX() / this.view.getCellLength();
		if(this.isVertical) {
			if(vc.getY() % this.cellSize <= this.cellSize / 2) {
				this.view.setVehicleLocation(this.currentVehicleID, vc.getX(), row * this.cellSize);
				this.game.moveVehicle(this.currentVehicleID, row, col);
			} else {
				this.view.setVehicleLocation(this.currentVehicleID, vc.getX(), (row+1)* this.cellSize);
				this.game.moveVehicle(this.currentVehicleID, row+1, col);
			}
		} else {
			if(vc.getX() % this.cellSize <= this.cellSize / 2) {
				this.view.setVehicleLocation(this.currentVehicleID, col * this.cellSize, vc.getY());
				this.game.moveVehicle(this.currentVehicleID, row, col);
				//System.out.printf("same %d %d%n",row, col);
			} else {
				this.view.setVehicleLocation(this.currentVehicleID, (col+1) * this.cellSize, vc.getY());
				this.game.moveVehicle(this.currentVehicleID, row, col+1);
				//System.out.printf("next %d %d%n",row, col+1);
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
