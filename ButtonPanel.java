import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

public class ButtonPanel extends JPanel {

    private final JButton menuButton;
    // Variables declaration
    private JComboBox<String> DifficultyBox;
    private JLabel DifficultyLabel;
    private JButton HintBtn;
    private JComboBox<String> LevelBox;
    private JLabel LevelLabel;
    private JButton LoadGameBtn;
    private JLabel MoveCount;
    private JButton RedoBtn;
    private JButton UndoBtn;
    private JButton CreateBtn;
    private JButton jResetBtn;
    private JLabel jTimeLabel;
    private Timer timer;

    public ButtonPanel() {

        LoadGameBtn = new JButton();
        jResetBtn = new JButton();
        jTimeLabel = new JLabel();
        MoveCount = new JLabel();
        HintBtn = new JButton();
        UndoBtn = new JButton();
        RedoBtn = new JButton();
        DifficultyLabel = new JLabel();
        DifficultyBox = new JComboBox<>();
        LevelLabel = new JLabel();
        LevelBox = new JComboBox<>();
        CreateBtn = new JButton();
        menuButton = new JButton();


        this.setPreferredSize(new java.awt.Dimension(150, 300));
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        LoadGameBtn.setText("Load Game");
        jTimeLabel.setText("00:00:00");
        MoveCount.setText("Moves: 0");
        jResetBtn.setText("Reset");
        HintBtn.setText("Hint");
        UndoBtn.setText("Undo");
        RedoBtn.setText("Redo");
        DifficultyLabel.setText("Difficulty:");
        LevelLabel.setText("Puzzle: ");
        CreateBtn.setText("Create Randomized Game");
        menuButton.setText("Menu");

        DifficultyBox.setModel(new DefaultComboBoxModel<>(new String[]{"Novice", "Intermediate",
                "Expert"}));
        LevelBox.setModel(new DefaultComboBoxModel<>(new String[]{"Puzzle 1", "Puzzle 2", "Puzzle 100"}));

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
        this.HintBtn.addActionListener(c.getHintButtonListener());
        this.LoadGameBtn.addActionListener(c.getLoadGameButtonListener());
        this.CreateBtn.addActionListener(c.getCreateGameButtonListener());
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

}
