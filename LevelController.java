import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LevelController implements ActionListener {
    private JFrame levelView;
    private List<PuzzleGame> puzzles;
    private JFrame gameView;

    public LevelController(JFrame levelView, List<PuzzleGame> puzzles) {
        this.levelView = levelView;
        this.puzzles = puzzles;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        for (PuzzleGame p : this.puzzles) {
            if (action.equals(Integer.toString(p.getId()))) {
                this.levelView.setVisible(false);
                this.setGameView(p);
            }
        }
    }

    private void setGameView(PuzzleGame puzzleGame) {

        PuzzleView pv = new PuzzleView(puzzleGame, PuzzleView.DEFAULT_CELL_SIZE);
        PuzzleController pc = new PuzzleController(puzzleGame, pv);

        ButtonPanel bp = new ButtonPanel(PuzzleView.DEFAULT_CELL_SIZE);
        SideButtonController bc = new SideButtonController(pv, puzzleGame, bp);
        BorderedPuzzleView borderedPuzzleView = new BorderedPuzzleView(pv);

        GameView gameView = new GameView(bp, bc, pv, pc, borderedPuzzleView);
        gameView.setMinimumSize(new Dimension(PuzzleView.DEFAULT_CELL_SIZE * 11, PuzzleView.DEFAULT_CELL_SIZE * 8));
        BorderedPuzzleController borderedPuzzleController = new BorderedPuzzleController(borderedPuzzleView);
        GameController gameController = new GameController(gameView, borderedPuzzleController, bc);

        this.gameView = this.createFrame(gameView);
    }

    private JFrame createFrame(Container view) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(view, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        return frame;
    }

}
