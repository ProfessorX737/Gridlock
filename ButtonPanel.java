import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class ButtonPanel extends JPanel {

    private final JButton menuButton;
    private JLabel MoveCount;
    private JButton RedoBtn;
    private JButton UndoBtn;
    private JButton jResetBtn;
    private JLabel jTimeLabel;
    private Timer timer;

    public ButtonPanel() {
        ImageIcon button = new ImageIcon(this.getClass().getResource("Assets/button.png"));
//        ImageIcon button = new ImageIcon("Assets/button.png");
        button = new ImageIcon(button.getImage().getScaledInstance(100, 50, Image
                .SCALE_SMOOTH));

        jResetBtn = new JButton(button);
        jTimeLabel = new JLabel(button);
        MoveCount = new JLabel(button);
        UndoBtn = new JButton(button);
        RedoBtn = new JButton(button);
        menuButton = new JButton(button);
        this.setOpaque(false);


        this.setPreferredSize(new java.awt.Dimension(150, 400));

        jTimeLabel.setText("00:00:00");
        MoveCount.setText("Moves: 0");
        jResetBtn.setText("Reset");
        UndoBtn.setText("Undo");
        RedoBtn.setText("Redo");
        menuButton.setText("Menu");

        menuButton.setBorder(BorderFactory.createEmptyBorder());
        jTimeLabel.setBorder(BorderFactory.createEmptyBorder());
        MoveCount.setBorder(BorderFactory.createEmptyBorder());
        jResetBtn.setBorder(BorderFactory.createEmptyBorder());
        UndoBtn.setBorder(BorderFactory.createEmptyBorder());
        RedoBtn.setBorder(BorderFactory.createEmptyBorder());

        menuButton.setHorizontalTextPosition(JButton.CENTER);
        jTimeLabel.setHorizontalTextPosition(JButton.CENTER);
        MoveCount.setHorizontalTextPosition(JButton.CENTER);
        jResetBtn.setHorizontalTextPosition(JButton.CENTER);
        UndoBtn.setHorizontalTextPosition(JButton.CENTER);
        RedoBtn.setHorizontalTextPosition(JButton.CENTER);


        /*jTimeLabel.setIcon(button);
        jResetBtn.setIcon(button);
        MoveCount.setIcon(button);
        UndoBtn.setIcon(button);
        RedoBtn.setIcon(button);
        menuButton.setIcon(button);
*/
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        this.add(jTimeLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        this.add(MoveCount, c);

        c.gridx = 0;
        c.gridy = 2;
        this.add(menuButton, c);

        c.gridx = 0;
        c.gridy = 3;
        this.add(UndoBtn, c);

        c.gridx = 0;
        c.gridy = 4;
        this.add(RedoBtn, c);

        c.gridx = 0;
        c.gridy = 5;
        this.add(jResetBtn, c);
    }

    public void setController(ButtonController c) {
        this.RedoBtn.addActionListener(c.getRedoButtonListener());
        this.UndoBtn.addActionListener(c.getUndoButtonListener());
        this.jResetBtn.addActionListener(c.getResetButtonListener());
        this.timer = new Timer(1000, c.getTimerListener());
        this.startTimer();
    }

    public void startTimer(){
        this.timer.start();
    }

    public void endTimer(){
        this.timer.stop();
    }

    public void displayTime(String time){
        jTimeLabel.setText(time);
    }

    public void displayMoves(int moves){
        MoveCount.setText("Moves: " + Integer.toString(moves));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon bg = new ImageIcon(this.getClass().getResource("Assets/sideBg.png"));
        bg = new ImageIcon(bg.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
        // Image board = bg.getImage();
        // board = board.getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
        g.drawImage(bg.getImage(), 0, 0, null);
    }
}
