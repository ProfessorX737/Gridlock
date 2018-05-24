import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    private final JButton menuButton;
    private JLabel MoveCount;
    private JButton RedoBtn;
    private JButton UndoBtn;
    private JButton jResetBtn;
    private JLabel jTimeLabel;
    private Timer timer;
    private int cellSize;
    private GridBagLayout g;
    private GridBagConstraints c;

    public ButtonPanel(int cellSize) {
        this.cellSize = cellSize;
        setPreferredSize(new java.awt.Dimension(cellSize * 3, cellSize * 8));

        ImageIcon button = new ImageIcon(this.getClass().getResource("Assets/button.png"));
        button = new ImageIcon(button.getImage().getScaledInstance(cellSize * 2, cellSize, Image.SCALE_SMOOTH));
        ImageIcon label = new ImageIcon(this.getClass().getResource("Assets/textBg.png"));
        label = new ImageIcon(label.getImage().getScaledInstance(cellSize * 2, cellSize, Image.SCALE_SMOOTH));

        jResetBtn = new JButton(button);
        jTimeLabel = new JLabel(label);
        MoveCount = new JLabel(label);
        UndoBtn = new JButton(button);
        RedoBtn = new JButton(button);
        menuButton = new JButton(button);
        this.setOpaque(false);


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

        menuButton.setVerticalTextPosition(JButton.CENTER);
        jTimeLabel.setVerticalTextPosition(JButton.CENTER);
        MoveCount.setVerticalTextPosition(JButton.CENTER);
        jResetBtn.setVerticalTextPosition(JButton.CENTER);
        UndoBtn.setVerticalTextPosition(JButton.CENTER);
        RedoBtn.setVerticalTextPosition(JButton.CENTER);



        /*jTimeLabel.setIcon(button);
        jResetBtn.setIcon(button);
        MoveCount.setIcon(button);
        UndoBtn.setIcon(button);
        RedoBtn.setIcon(button);
        menuButton.setIcon(button);
*/
        g = new GridBagLayout();
        this.setLayout(g);
        c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(cellSize/10, 0,cellSize/10,0);
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

    private void addButtons() {

    }

    public void setController(ButtonController c) {
        this.RedoBtn.addActionListener(c.getRedoButtonListener());
        this.UndoBtn.addActionListener(c.getUndoButtonListener());
        this.jResetBtn.addActionListener(c.getResetButtonListener());
        this.timer = new Timer(1000, c.getTimerListener());
        this.startTimer();
    }

    public void startTimer() {
        this.timer.start();
    }

    public void endTimer() {
        this.timer.stop();
    }

    public void displayTime(String time) {
        jTimeLabel.setText(time);
    }

    public void displayMoves(int moves) {
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

    public void resize(int newCellSize) {
        System.out.println("Resizing");
        this.cellSize = newCellSize;

        setPreferredSize(new Dimension(newCellSize * 3, newCellSize * 8));

        ImageIcon button = new ImageIcon(this.getClass().getResource("Assets/button.png"));
        button = new ImageIcon(button.getImage().getScaledInstance(cellSize * 2, cellSize, Image.SCALE_SMOOTH));
        ImageIcon label = new ImageIcon(this.getClass().getResource("Assets/textBg.png"));
        label = new ImageIcon(label.getImage().getScaledInstance(cellSize * 2, cellSize, Image.SCALE_SMOOTH));

        menuButton.setIcon(button);
        jResetBtn.setIcon(button);
        MoveCount.setIcon(label);
        UndoBtn.setIcon(button);
        RedoBtn.setIcon(button);
        jTimeLabel.setIcon(label);

       /*menuButton.setPreferredSize(buttonSize);
        jResetBtn.setPreferredSize(buttonSize);
        MoveCount.setPreferredSize(buttonSize);
        UndoBtn.setPreferredSize(buttonSize);
        RedoBtn.setPreferredSize(buttonSize);
        jTimeLabel.setPreferredSize(buttonSize);*/

        for (Component c : this.getComponents()){
            c.setFont(new Font("Arial", Font.PLAIN, cellSize/5));
            if(c.getClass() == menuButton.getClass()) {
                JButton b = (JButton) c;
                b.setIcon(button);
                b.setForeground(new Color(245,222,179));
            }
            else {
                JLabel l = (JLabel) c;
                l.setIcon(label);
                l.setForeground(new Color(128,0,0));
            }
        }

    }
}
