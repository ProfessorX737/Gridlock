import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    private ButtonPanel buttonPanel;
    private BorderedPuzzleView borderedPuzzleView;

    GameView(ButtonPanel buttonPanel, SideButtonController sideButtonController, PuzzleView puzzleView,
             PuzzleController puzzleController, BorderedPuzzleView borderedPuzzleView) {

        this.buttonPanel = buttonPanel;
        puzzleView.setController(puzzleController);
        this.buttonPanel.setController(sideButtonController);
        puzzleController.setButtonController(sideButtonController);
        puzzleView.setController(sideButtonController.getMouseAdapter());
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.borderedPuzzleView = borderedPuzzleView;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(this.borderedPuzzleView);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.5;
        c.fill = GridBagConstraints.VERTICAL;
        this.add(this.buttonPanel);
    }

    public ButtonPanel getButtonPanel() {
        return buttonPanel;
    }

//    public PuzzleView getPuzzleView() {
//        return puzzleView;
//    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        ImageIcon bg = new ImageIcon(this.getClass().getResource("Assets/board.png"));
        bg = new ImageIcon(bg.getImage().getScaledInstance(this.getWidth(), this.getWidth(), Image.SCALE_SMOOTH));
        g.drawImage(bg.getImage(), 0, 0, null);
    }
}
