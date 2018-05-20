import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GameController {
    private GameView gameView;
    private GameListener gameListener;

    public GameController(GameView gameView, PuzzleController puzzleController) {
        this.gameView = gameView;
        this.gameListener = new GameListener(puzzleController);
        gameView.addComponentListener(gameListener);
    }
}
