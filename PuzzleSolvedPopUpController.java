import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class PuzzleSolvedPopUpController extends MouseAdapter {
	private PuzzleSolvedPopUpScreen view;
	private GridlockGame game;
	private int level;
	private int puzzleId;
	private PuzzleGame puzzleGame;
	private JFrame currGameScreen;
	private JFrame selectPuzzleScreen;
	
	public PuzzleSolvedPopUpController(GridlockGame game, int level, int puzzleId, JFrame puzzleGameScreen, JFrame selectPuzzleScreen) {
		this.game = game;
		this.level = level;
		this.puzzleId = puzzleId;
		this.currGameScreen = puzzleGameScreen;
		this.selectPuzzleScreen = selectPuzzleScreen;
		this.puzzleGame = game.getPuzzle(level, puzzleId);
	}
	
	@Override 
	public void mouseReleased(MouseEvent e) {
		if(this.puzzleGame.isSolved()) {
			System.out.println("Puzzle solved!");
			this.view = new PuzzleSolvedPopUpScreen(puzzleGame);
			this.view.setVisible(true);
			this.view.setOkButtonController(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					view.dispose();
					currGameScreen.dispose();
					selectPuzzleScreen.setVisible(true);
				}
			});
		}
	}
}
