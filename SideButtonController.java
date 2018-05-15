import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideButtonController implements ButtonController {
	
    private PuzzleView puzzleView;
    private PuzzleGame puzzleGame;

    public SideButtonController(PuzzleView puzzleView, PuzzleGame puzzleGame) {
        this.puzzleView = puzzleView;
        this.puzzleGame = puzzleGame;
    }

    /**
     * Updates the view with the current game board
     */
    private void updateView(){
        for(Vehicle v : puzzleGame.getVehicles()) {
        	puzzleView.setVehicleLocation(v.getID(), v.getCol() * this.puzzleView.getCellLength(), v.getRow() * this.puzzleView.getCellLength());
        }
    }

	@Override
	public ActionListener getRedoButtonListener() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	puzzleGame.redo();
            	updateView();
            }
        };
        return al;
	}

	@Override
	public ActionListener getUndoButtonListener() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	puzzleGame.undo();
            	updateView();
            }
        };
        return al;
	}

	@Override
	public ActionListener getLoadGameButtonListener() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("load game pressed!");
            }
        };
        return al;
	}

	@Override
	public ActionListener getCreateGameButtonListener() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("Create Buttoon pressed!");
            }
        };
        return al;
	}

	@Override
	public ActionListener getHintButtonListener() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("Hint button pressed!");
            }
        };
        return al;
	}

	@Override
	public ActionListener getResetButtonListener() {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	puzzleGame.reset();
            	updateView();
            }
        };
        return al;
	}


}
