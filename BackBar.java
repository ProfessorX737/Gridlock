import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BackBar extends JPanel {
    public final static int DEFAULT_HEIGHT = 25;
    public final static int DEFAULT_WIDTH = 200;

    private JLabel title;
    private JButton backButton;
    private int width;
    private int height;
    private JComponent main;

    public BackBar(JComponent main) {
//		this.width = DEFAULT_WIDTH;
//		this.height = DEFAULT_HEIGHT;
//        this.setLayout(new BorderLayout(height, 0));
        this.title = new JLabel();
        this.backButton = new JButton();
        this.main = main;
        this.setPreferredSize(new Dimension(PuzzleView.DEFAULT_CELL_SIZE * 11, PuzzleView.DEFAULT_CELL_SIZE * 8));

        ImageIcon back = new ImageIcon(this.getClass().getResource("Assets/back.png"));
        back = new ImageIcon(back.getImage().getScaledInstance((int) Math.round(PuzzleView.DEFAULT_CELL_SIZE * 1.5),
                PuzzleView.DEFAULT_CELL_SIZE, Image.SCALE_SMOOTH));

        this.backButton = new JButton(back);

//		this.backButton.setText("Back");
        this.backButton.setBorder(BorderFactory.createEmptyBorder());

       /* topBar.setLayout(new BorderLayout(topBarHeight, 0));
        topBar.add(backButton, BorderLayout.WEST);
        topBar.add(this.title, BorderLayout.CENTER);
        topBar.setPreferredSize(new Dimension(main.getWidth(), topBarHeight));
        this.add(topBar, BorderLayout.PAGE_START);
        this.add(main, BorderLayout.CENTER);*/

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        this.add(main, c);
        c.anchor = GridBagConstraints.NORTHWEST;
        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 0, 0);
        this.add(backButton, c);

    }

    /*public BackBar(JComponent main, int width, int height) {
        this.width = width;
        this.height = height;
        this.setLayout(new BorderLayout(height, 0));
        this.title = new JLabel();
        this.backButton = new JButton();

        this.backButton.setText("Back");
        this.title.setText(title);
        this.add(this.backButton, BorderLayout.WEST);
        this.add(this.title, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(width, height));
    }*/

    public void setController(ActionListener al) {
        this.backButton.addActionListener(al);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        ImageIcon bg = new ImageIcon(this.getClass().getResource("Assets/board.png"));
        bg = new ImageIcon(bg.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
        g.drawImage(bg.getImage(), 0, 0, null);
    }

    public void updateContent(JComponent newMain){
        this.remove(main);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        this.add(newMain, c);
    }
}
