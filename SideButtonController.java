import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class SideButtonController implements ButtonController {

    private PuzzleView puzzleView;
    private PuzzleGame puzzleGame;
    private ButtonPanel bp;

    private long time;


    public SideButtonController(PuzzleView puzzleView, PuzzleGame puzzleGame, ButtonPanel bp) {
        this.puzzleView = puzzleView;
        this.puzzleGame = puzzleGame;
        this.bp = bp;
        this.time = System.currentTimeMillis();

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
                bp.displayMoves(puzzleGame.getMoves());
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
                bp.displayMoves(puzzleGame.getMoves());
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
                time = System.currentTimeMillis();
                bp.displayMoves(puzzleGame.getMoves());
                updateView();
            }
        };
        return al;
    }

    @Override
    public ActionListener getTimerListener(){
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                long millis = System.currentTimeMillis() - time;
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                bp.displayTime(hms);
            }
        };
        return al;
    }
    
    @Override
    public MouseAdapter getMouseAdapter() {
    	MouseAdapter ma = new MouseAdapter() {
    		public void mouseReleased(MouseEvent e) {
    			bp.displayMoves(puzzleGame.getMoves());
    		}
    	};
    	return ma;
    }

}
