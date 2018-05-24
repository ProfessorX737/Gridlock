import javax.swing.*;
import java.awt.*;

public class BorderedPuzzleView extends JPanel implements Board {
    private PuzzleView puzzleView;
//    private int cellSize;

    /*public BorderedPuzzleView(PuzzleView puzzleView, int cellSize) {
        this.puzzleView = puzzleView;
        this.cellSize = cellSize;
        draw();
    }*/

    public BorderedPuzzleView(PuzzleView puzzleView) {
        setOpaque(false);
        this.puzzleView = puzzleView;
        setLayout(new GridBagLayout());
        updateSize(puzzleView.getCellLength());
//        setLayout(null);
//        this.add(puzzleView);
//        puzzleView.setBounds(puzzleView.getCellLength(), puzzleView.getCellLength(), puzzleView.getWidth(),
// puzzleView.getHeight());
//        draw();
    }

    @Override
    public void draw() {
        GridBagConstraints c = new GridBagConstraints();
//       setLayout(null);
//       puzzleView.draw();
        PuzzleGame puzzleGame = puzzleView.getPuzzleGame();
        int cellSize = puzzleView.getCellLength();
        setPreferredSize(new Dimension(cellSize * 8, cellSize * 8));
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 6;
        c.gridheight = 6;
        this.add(puzzleView, c);
        puzzleView.setBounds(puzzleView.getCellLength(), puzzleView.getCellLength(), puzzleView.getWidth(),
                puzzleView.getHeight());
//       puzzleView.draw();
        ImageIcon border = new ImageIcon(this.getClass().getResource("Assets/border.png"));
        border = new ImageIcon(border.getImage().getScaledInstance(cellSize, cellSize, Image
                .SCALE_SMOOTH));
        int columns = puzzleGame.getNumCols();
        int rows = puzzleGame.getNumRows();

        for (int i = 0; i < columns + 2; i++) {
            for (int j = 0; j < rows + 2; j++) {
                if(i == columns + 1 && j == puzzleGame.getExitRow() + 1) continue;
                if (i == 0 || i == columns + 1 || j == 0 || j == rows + 1) {
                    c.gridheight = 1;
                    c.gridwidth = 1;
                    c.gridx = i;
                    c.gridy = j;
                    JComponent b = new JLabel("", border, JLabel.CENTER);
                    //                    b.setBounds(i * cellSize, j * cellSize, cellSize, cellSize);
//                    if(i == columns + 1 && j == puzzleGame.getExitRow() + 1) b.setOpaque(false);
                    this.add(b, c);
                    if (i == puzzleGame.getExitCol() && j == puzzleGame.getExitRow()) {
                        System.out.println(i + " " + j);
                    }

                }
            }
        }

    }

    @Override
    public void updateSize(int cellSize) {
        removeAll();
        puzzleView.updateSize(cellSize);
        draw();

    }
}
