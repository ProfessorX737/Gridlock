import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class LevelController implements ActionListener {
	private JFrame levelView;
	private GridlockGame game;
	private int level;
	private JFrame gameView;
	private JFrame menu;

	public LevelController(GridlockGame game, int level, JFrame levelView, JFrame menu) {
		this.levelView = levelView;
		this.game = game;
		this.level = level;
		this.menu = menu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		System.out.println(action);
		for(PuzzleGame p : this.game.getPuzzles(this.level)) {
			if(action.equals(Integer.toString(p.getId()))) {

				this.setGameView(p);
				this.levelView.setVisible(false);
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

        PuzzleSolvedPopUpController popUpController = new PuzzleSolvedPopUpController(this.game,this.level,puzzleGame.getId(),this.gameView,this.levelView);
        pv.setController(popUpController);

		this.gameView.setLocation(levelView.getLocation());
		this.gameView.setSize(levelView.getSize());
        bc.setMenuButtonController(e -> {
			menu.setLocation(this.gameView.getLocation());
			menu.setSize(this.gameView.getSize());
            menu.setVisible(true);
			this.gameView.dispose();
		});
    }

	public JFrame getMenu() {
		return menu;
	}

	public JFrame getGameView() {
		return gameView;
	}

	private JFrame createFrame(Container view) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(view, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

}
