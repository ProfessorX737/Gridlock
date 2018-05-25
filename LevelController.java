import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class LevelController implements ActionListener {
	private JFrame levelView;
	private GridlockGame game;
	private int level;
	private JFrame gameView;
	
	public LevelController(GridlockGame game, int level, JFrame levelView) {
		this.levelView = levelView;
		this.game = game;
		this.level = level;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		System.out.println(action);
		for(PuzzleGame p : this.game.getPuzzles(this.level)) {
			if(action.equals(Integer.toString(p.getId()))) {
				this.levelView.setVisible(false);
				this.setGameView(p);
			}
		}
	}
	
	private void setGameView(PuzzleGame puzzleGame) {

        PuzzleView pv = new PuzzleView(puzzleGame, PuzzleView.DEFAULT_CELL_SIZE);
        PuzzleController pc = new PuzzleController(puzzleGame, pv);

        ButtonPanel bp = new ButtonPanel();
        SideButtonController bc = new SideButtonController(pv, puzzleGame, bp);
        BorderedPuzzleView borderedPuzzleView = new BorderedPuzzleView(pv);

        GameView gameView = new GameView(bp, bc, pv, pc, borderedPuzzleView);
        BorderedPuzzleController borderedPuzzleController = new BorderedPuzzleController(borderedPuzzleView);
        GameController gameController = new GameController(gameView, borderedPuzzleController);
        
        this.gameView = this.createFrame(gameView);
        PuzzleSolvedPopUpController popUpController = new PuzzleSolvedPopUpController(this.game,this.level,puzzleGame.getId(),this.gameView,this.levelView);
        pv.setController(popUpController);
	}

	private JFrame createFrame(Container view) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(view,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
	}

}
