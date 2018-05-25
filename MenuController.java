import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MenuController implements ActionListener {
	
	private GridlockGame game;
	private JFrame menu;
	private LevelSelectScreen levelSelect;
	private PuzzleSelectScreen[] puzzleSelect;
    private JFrame multiplayer;

	public MenuController(GridlockGame game, JFrame menu) {
		this.game = game;
		this.menu = menu;
		this.puzzleSelect = new PuzzleSelectScreen[GridlockGame.NUM_LEVELS];
		for(int i = 0; i < GridlockGame.NUM_LEVELS; i++) {
			this.puzzleSelect[i] = new PuzzleSelectScreen(this.game,i, menu);
			PuzzleSelectScreen currScreen = this.puzzleSelect[i];
			currScreen.setBackBarController(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
                    System.out.println("back pressed");
                    levelSelect.setSize(currScreen.getSize());
                    levelSelect.setLocation(currScreen.getLocation());
					// currScreen.setVisible(false);
					//
					// levelSelect.setLocationRelativeTo(null);
					levelSelect.setVisible(true);
					currScreen.setVisible(false);
				}
			});
		}
    		this.levelSelect = new LevelSelectScreen();
    		this.levelSelect.setBackBarController(new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
					menu.setSize(levelSelect.getSize());
					menu.setLocation(levelSelect.getLocation());
					menu.setVisible(true);
    				levelSelect.setVisible(false);
    				//
    				// menu.setLocationRelativeTo(null);
    				// menu.setVisible(true);
    			}
    		});
		this.levelSelect.setLevelSelectController(new LevelSelectController(this.levelSelect, this.puzzleSelect));

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
		this.levelSelect.setLocationRelativeTo(null);
		this.levelSelect.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action == "play") {
			levelSelect.setLocation(menu.getLocation());
			levelSelect.setSize(menu.getSize());
			this.levelSelect.setVisible(true);
			menu.setVisible(false);
		} else if(action == "multiplayer") {
			System.out.println("multiplayer button pressed");
			this.menu.setVisible(false);
			//
			this.levelSelect.setLocationRelativeTo(null);
			this.multiplayer.setVisible(true);
			this.menu.setVisible(false);
			multiplayer.setLocation(menu.getLocation());
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
