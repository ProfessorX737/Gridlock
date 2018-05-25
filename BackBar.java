import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class BackBar extends JPanel {
    public final static int DEFAULT_HEIGHT = 25;
    public final static int DEFAULT_WIDTH = 200;

    private JLabel title;
    private JButton backButton;
    private int width;
    private int height;
    private int cellSize;
    private JComponent main;

    public BackBar(JComponent main) {
//       cellSize = Math.min(SwingUtilities.getRoot(this).getWidth()/12, SwingUtilities.getRoot(this).getHeight()/8);
        cellSize = PuzzleView.DEFAULT_CELL_SIZE;
        this.title = new JLabel();
        this.backButton = new JButton();
        this.main = main;
        this.setPreferredSize(new Dimension(cellSize * 11, cellSize * 8));

        ImageIcon back = new ImageIcon(this.getClass().getResource("Assets/back.png"));
        back = new ImageIcon(back.getImage().getScaledInstance((int) Math.round(cellSize * 1.5),
                cellSize, Image.SCALE_SMOOTH));

        this.backButton = new JButton(back);

        this.backButton.setBorder(BorderFactory.createEmptyBorder());

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

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateCellSize();
                ImageIcon back = new ImageIcon(this.getClass().getResource("Assets/back.png"));
                back = new ImageIcon(back.getImage().getScaledInstance((int) Math.round(cellSize * 1.5),
                        cellSize, Image.SCALE_SMOOTH));
                backButton.setIcon(back);
//                resizeMain();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

    }

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

    private void updateCellSize(){
        this.cellSize = Math.min(this.getHeight() / 8, this.getWidth() / 12);
    }

    /*private void resizeMain(){
       main.updateSize();
    }*/
}
