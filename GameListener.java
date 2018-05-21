import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GameListener implements ComponentListener {
    private PuzzleController puzzleController;

    public GameListener(PuzzleController puzzleController) {
        this.puzzleController = puzzleController;
    }

    @Override
    public void componentResized(ComponentEvent e) {
       System.out.println("Game was resized");
       GameView g = (GameView) e.getComponent();
       ButtonPanel b = g.getButtonPanel();
       b.setPreferredSize(new Dimension(150,g.getHeight()));
       int newCellSize = Math.min(g.getHeight(), g.getWidth() - 150)/6;
       puzzleController.resize(newCellSize);

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
