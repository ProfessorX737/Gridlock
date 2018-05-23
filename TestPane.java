import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class TestPane extends JPanel {

    public TestPane() {

        setBorder(new LineBorder(Color.RED));
        setPreferredSize(new Dimension(500, 1000));

    }

//    @Override
//    protected void paintComponent(Graphics g) {
//
//        super.paintComponent(g);
//
//        FontMetrics fm = g.getFontMetrics();
//
//        Dimension size = getPreferredSize();
//        String text = "Pref: " + size.width + "x" + size.height;
//        g.drawString(text, 0, fm.getAscent());
//
//        size = getSize();
//        text = "Size: " + size.width + "x" + size.height;
//        g.drawString(text, 0, fm.getHeight() + fm.getAscent());
//
//    }

}