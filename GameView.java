import javax.swing.*;
import java.awt.*;

public class GameView extends Container{
    private ButtonPanel buttonPanel;
    private PuzzleView puzzleView;

   GameView(ButtonPanel buttonPanel, SideButtonController sideButtonController, PuzzleView puzzleView, PuzzleController puzzleController) {
      
      this.buttonPanel = buttonPanel;
      this.puzzleView = puzzleView;
      this.puzzleView.setController(puzzleController);
      this.buttonPanel.setController(sideButtonController);
      puzzleController.setButtonController(sideButtonController);
      this.puzzleView.setController(sideButtonController.getMouseAdapter());
      this.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = 0;
      c.gridy = 0;
      c.weightx = 1;
      c.fill = GridBagConstraints.HORIZONTAL;
      this.add(this.puzzleView);
      c.gridx = 1;
      c.gridy = 0;
      c.weightx = 0.5;
      c.fill = GridBagConstraints.VERTICAL;
      this.add(this.buttonPanel);
   }

   public ButtonPanel getButtonPanel() {
      return buttonPanel;
   }

   public PuzzleView getPuzzleView() {
      return puzzleView;
   }
}
