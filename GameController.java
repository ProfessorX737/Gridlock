public class GameController {
    private GameView gameView;
    private GameListener gameListener;

    public GameController(GameView gameView, BorderedPuzzleController borderedPuzzleController, SideButtonController
            buttonController) {
        this.gameView = gameView;
        this.gameListener = new GameListener(borderedPuzzleController, buttonController);
        gameView.addComponentListener(gameListener);
    }
}
