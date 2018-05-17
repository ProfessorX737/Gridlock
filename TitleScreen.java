import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class TitleScreen extends JPanel implements ActionListener {
	
	private static Menu title;
	private static JFrame myFrame;
	private static JPanel control;
	static JButton start;
	static JButton lvSelect;
	static JButton instructions;
	static JButton exit;
	private static JLabel statusLabel;
	private JFrame f;
	private String fileName;
	JLabel background;
	
	public TitleScreen(JFrame f, String fileName) {
		this.f = f;
		this.fileName = fileName;
		
		myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(400,400);
        myFrame.setLocationRelativeTo(null);
        myFrame.setTitle("Game Menu");
        myFrame.setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("MainMenu_bg.png"));
        ImageIcon bg = new ImageIcon(getClass().getResource("MainMenu_bt.png"));
        icon = new ImageIcon(icon.getImage().getScaledInstance(395,380,Image.SCALE_SMOOTH));
        bg = new ImageIcon(bg.getImage().getScaledInstance(130, 40, Image.SCALE_SMOOTH));
        JLabel test = new JLabel("",icon,JLabel.CENTER);
        test.setSize(400,400);
        test.setBounds(0,-10,400,400);
        
        title = new Menu();
        
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
               System.exit(0);
            }        
         });
        
        start = new JButton("Start");
        start.setVerticalTextPosition(AbstractButton.CENTER);
        start.setMnemonic(KeyEvent.VK_P);
        start.setActionCommand("Start");
        start.setBounds(130,150,130,40);
        start.setIcon(bg);
        start.setIconTextGap(-85);
        
        lvSelect = new JButton("Level Select");
        lvSelect.setVerticalTextPosition(AbstractButton.CENTER);
        lvSelect.setMnemonic(KeyEvent.VK_P);
        lvSelect.setActionCommand("lvSelect");
        lvSelect.setBounds(130,200,130,40);
        lvSelect.setIcon(bg);
        lvSelect.setIconTextGap(-105);
        
        instructions = new JButton("Instructions");
        instructions.setVerticalTextPosition(AbstractButton.CENTER);
        instructions.setMnemonic(KeyEvent.VK_P);
        instructions.setActionCommand("Instruct");
        instructions.setBounds(130,250,130,40);
        instructions.setIcon(bg);
        instructions.setIconTextGap(-102);
        
        exit = new JButton("Logout");
        exit.setVerticalTextPosition(AbstractButton.CENTER);
        exit.setMnemonic(KeyEvent.VK_P);
        exit.setActionCommand("Exit");
        exit.setBounds(130,300,130,40);
        exit.setIcon(bg);
        exit.setIconTextGap(-90);
        
        control = new JPanel();
        control.setLayout(null);
        statusLabel = new JLabel("GridLock",JLabel.CENTER);  
        statusLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        statusLabel.setSize(350,100);
        statusLabel.setBounds(90,80,210,30);
        
        control.add(start);
        control.add(lvSelect);
        control.add(instructions);
        control.add(exit);
        control.add(test);
        myFrame.add(statusLabel);
        myFrame.add(control);
        
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	GridLock.board(f,fileName);
            	myFrame.dispose();
            }  
        });
        
        lvSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
 //           	new levelSelect();
            	myFrame.dispose();
            }  
        });
        
		
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(myFrame, "Your goal is to move the red car through the exit\n"
            			+ " the exit is at the right hand side of the board on the same row with red car\n"
            			+ "Cars and trucks can only move horizontally or vertically along a row or column\n"
            			+ "only the red car can exit the board\n","Instructions",JOptionPane.INFORMATION_MESSAGE);
        
            }  
        });
        
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new MainMenu(f, fileName);
            	myFrame.dispose();
            }  
        });
        
        myFrame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

