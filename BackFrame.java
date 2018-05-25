import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class BackFrame extends JPanel {
    public final static int DEFAULT_BAR_HEIGHT = 25;
    private JPanel topBar;
    private JLabel title;
    private JButton backButton;

    public BackFrame(String title, JComponent main, int topBarHeight) {
//        this.setLayout(new BorderLayout(topBarHeight, topBarHeight));
        this.setPreferredSize(new Dimension(PuzzleView.DEFAULT_CELL_SIZE * 11, PuzzleView.DEFAULT_CELL_SIZE * 8));

        ImageIcon back = new ImageIcon(this.getClass().getResource("Assets/back.png"));
        back = new ImageIcon(back.getImage().getScaledInstance((int) Math.round(PuzzleView.DEFAULT_CELL_SIZE * 1.5),
				PuzzleView.DEFAULT_CELL_SIZE, Image.SCALE_SMOOTH));

        this.topBar = new JPanel();
        this.backButton = new JButton(back);
        this.title = new JLabel(title);

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

//        this.setLayout(new BorderLayout());
//        this.add(main, BorderLayout.CENTER);
//        this.add(backButton, BorderLayout.WEST);


        this.setSize(new Dimension(main.getWidth(), main.getHeight() + topBarHeight));
    }

    public void setController(ActionListener al) {
        this.backButton.addActionListener(al);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        ImageIcon bg = new ImageIcon(this.getClass().getResource("Assets/board.png"));
        bg = new ImageIcon(bg.getImage().getScaledInstance(this.getWidth(), this.getWidth(), Image.SCALE_SMOOTH));
        g.drawImage(bg.getImage(), 0, 0, null);
    }
}
