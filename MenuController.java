import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MenuController implements ActionListener {
	
	private GridlockGame game;
	private JFrame menu;
	private JFrame levelSelect;
	private JFrame[] puzzleSelect;
	
	public MenuController(GridlockGame game, JFrame menu) {
		this.game = game;
		this.menu = menu;
		
		this.puzzleSelect = new JFrame[GridlockGame.NUM_LEVELS];
		for(int i = 0; i < GridlockGame.NUM_LEVELS; i++) {
			LevelView lv = new LevelView(this.game.getPuzzles(i),MainMenu.WIDTH,MainMenu.HEIGHT,PuzzleView.DEFAULT_CELL_SIZE);
			this.puzzleSelect[i] = this.createFrame(new BackFrame("Select A Puzzle",lv, BackFrame.DEFAULT_BAR_HEIGHT));
			LevelController puzzleSelectController = new LevelController(this.puzzleSelect[i],this.game.getPuzzles(i));
			lv.setController(puzzleSelectController);
		}
    		LevelSelect ls = new LevelSelect(MainMenu.WIDTH,MainMenu.HEIGHT);
		this.levelSelect = this.createFrame(new BackFrame("Select a Level", ls, BackFrame.DEFAULT_BAR_HEIGHT));
		LevelSelectController lvlContr = new LevelSelectController(this.levelSelect,this.puzzleSelect);
		ls.setController(lvlContr);
		
	}
	
	private JFrame createFrame(Container view) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(view,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);
        return frame;
	}
	
//	private JFrame createGameView(PuzzleGame puzzleGame) {
//
//        PuzzleView pv = new PuzzleView(puzzleGame, PuzzleView.DEFAULT_CELL_SIZE);
//        PuzzleController pc = new PuzzleController(puzzleGame, pv);
//
//        ButtonPanel bp = new ButtonPanel();
//        SideButtonController bc = new SideButtonController(pv, puzzleGame, bp);
//
//        GameView gameView = new GameView(bp, bc, pv, pc);
//        new GameController(gameView, pc);
//        
//        return this.createFrame(gameView);
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action == "play") {
			System.out.println("play pressed");
		}
		if(action == "puzzles") {
			menu.setVisible(false);
			this.levelSelect.setVisible(true);
		}
		if(action == "exit") {
			menu.dispose();
			this.levelSelect.dispose();
			for(int i = 0; i < GridlockGame.NUM_LEVELS; i++) {
				this.puzzleSelect[i].dispose();
			}
			this.levelSelect.dispose();
		}
	}
	
}
