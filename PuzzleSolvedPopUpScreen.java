import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PuzzleSolvedPopUpScreen extends JFrame {
	private JLabel solvedMessage;
	private JButton okBtn;
	
	public PuzzleSolvedPopUpScreen(PuzzleGame puzzleGame) {
		this.setLayout(new BorderLayout());

		this.okBtn = new JButton("Ok");
		String message = String.format("You solved the game in %d moves %nrecord : %d moves", puzzleGame.getMoves(), puzzleGame.getMinMoves());
		this.solvedMessage = new JLabel(message);
		
		this.add(this.solvedMessage,BorderLayout.NORTH);
		this.add(this.okBtn, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(false);
	}
	
	public void setOkButtonController(ActionListener al) {
		this.okBtn.addActionListener(al);
	}
}
