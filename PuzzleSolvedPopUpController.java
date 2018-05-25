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
	private JFrame currGameView;
	
	public PuzzleSolvedPopUpController(GridlockGame game, int level, int puzzleId, JFrame currGameView) {
		this.game = game;
		this.level = level;
		this.puzzleId = puzzleId;
		this.currGameView = currGameView;
		this.puzzleGame = game.getPuzzle(level, puzzleId);
	}
	
	@Override 
	public void mouseReleased(MouseEvent e) {
		if(this.puzzleGame.isSolved()) {
			this.view = new PuzzleSolvedPopUpScreen(puzzleGame);
			this.view.setVisible(true);
			this.view.setOkButtonController(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					view.dispose();
					currGameView.dispose();
					if(game.getPuzzles(level).size() - 1 > puzzleId) {
						
					}
				}
			});
		}
	}
}
