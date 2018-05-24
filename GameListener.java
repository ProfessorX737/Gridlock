import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GameListener implements ComponentListener {
    private BorderedPuzzleController borderedPuzzleController;
    private SideButtonController buttonController;

    public GameListener(BorderedPuzzleController borderedPuzzleController, SideButtonController buttonController) {
        this.borderedPuzzleController = borderedPuzzleController;
        this.buttonController = buttonController;
    }

    @Override
    public void componentResized(ComponentEvent e) {
       GameView g = (GameView) e.getComponent();
       ButtonPanel b = g.getButtonPanel();
       int newCellSize = Math.min(g.getHeight(), g.getWidth() - 150)/8;
        buttonController.resize(newCellSize);
//        b.setPreferredSize(new Dimension(newCellSize * 3,g.getHeight()));
       borderedPuzzleController.resize(newCellSize);

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
