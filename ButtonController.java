
public class ButtonController{
    private PuzzleView puzzleView;
    private PuzzleGame puzzleGame;

    public ButtonController(PuzzleView puzzleView, PuzzleGame puzzleGame) {
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

    public void reset(){
        puzzleGame.reset();
        // puzzleGame.showBoard();
        updateView();
    }

    public void redo(){
        puzzleGame.redo();
        updateView();
    }

    public void undo(){
        puzzleGame.undo();
        updateView();
    }


}
