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
    private JFrame multiplayer;
	
	public MenuController(GridlockGame game, JFrame menu) {
		this.game = game;
		this.menu = menu;
		this.puzzleSelect = new JFrame[GridlockGame.NUM_LEVELS];
		for(int i = 0; i < GridlockGame.NUM_LEVELS; i++) {
			LevelView lv = new LevelView(this.game.getPuzzles(i),MainMenu.WIDTH,MainMenu.HEIGHT,PuzzleView.DEFAULT_CELL_SIZE);
			BackFrame puzzleSelectBackPanel = new BackFrame("Select A Puzzle",lv,BackFrame.DEFAULT_BAR_HEIGHT);
			this.puzzleSelect[i] = this.createFrame(puzzleSelectBackPanel);
			JFrame currLevel = this.puzzleSelect[i];
			puzzleSelectBackPanel.setController(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					currLevel.setVisible(false);
					levelSelect.setVisible(true);
				}
			});
			LevelController puzzleSelectController = new LevelController(this.puzzleSelect[i],this.game.getPuzzles(i));
			lv.setController(puzzleSelectController);
		}
    		LevelSelect ls = new LevelSelect(MainMenu.WIDTH,MainMenu.HEIGHT);
    		BackFrame levelSelectBackPanel = new BackFrame("Select a Level", ls, BackFrame.DEFAULT_BAR_HEIGHT);
    		levelSelectBackPanel.setController(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				levelSelect.setVisible(false);
				menu.setVisible(true);
			}
		});
		this.levelSelect = this.createFrame(levelSelectBackPanel);
		LevelSelectController lvlContr = new LevelSelectController(this.levelSelect,this.puzzleSelect);
		ls.setController(lvlContr);

        NetworkPanel networkPanel = new NetworkPanel();
        NetUIController controller = new NetUIController(networkPanel, "localhost", 55555);
        networkPanel.setController(controller);
        this.multiplayer = this.createFrame(networkPanel);
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
	
	public void goToMainMenu() {
		menu.setVisible(false);
		this.levelSelect.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action == "play") {
			System.out.println("play pressed");
		} else if(action == "puzzles") {
			menu.setVisible(false);
			this.levelSelect.setVisible(true);
		} else if(action == "multiplayer") {
			System.out.println("multiplayer button pressed");
			this.menu.setVisible(false);
			this.multiplayer.setVisible(true);
		} else if(action == "exit") {
			menu.dispose();
			this.levelSelect.dispose();
			for(int i = 0; i < GridlockGame.NUM_LEVELS; i++) {
				this.puzzleSelect[i].dispose();
			}
			this.levelSelect.dispose();
		}
	}
	
	public void createLevelViewFrame() {
		
	}
	
}
