import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MainMenu {
	private static JFrame myFrame;
	private static JPanel control;
	private JFrame f;
	private String fileName;
	JLabel gameName;
	JButton Login;
	JButton Guest;
	JButton Exit;
		
	JButton register; // add a JButton of register
	
	JTextField username;
	//JTextField password;  
	JPasswordField password; //use JPasswordField to hide password
	JPasswordField passwordField;
	
	private JLabel usernameTit; //add a title for user name
	private JLabel passwordTit;//add a title for password
	
	String userTxt;
	String passTxt;
	JLabel info;
	boolean correct = false;
	
	public MainMenu(JFrame f, String fileName) {
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
        bg = new ImageIcon(bg.getImage().getScaledInstance(80, 30, Image.SCALE_SMOOTH));
        JLabel test = new JLabel("",icon,JLabel.CENTER);
        test.setSize(400,400);
        test.setBounds(0,-10,400,400);
        
        
        info = new JLabel("Welcome",JLabel.CENTER);  
        info.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        info.setSize(350,100);
        info.setBounds(90,220,210,30);
        
        username = new JTextField(20);
        username.setEditable(true);
        username.setBounds(150,180,100,20);
        username.setText("comp");
        
        //password = new JTextField(20);  
        password = new JPasswordField(20); // change to JPasswordField
        password.setEditable(true);
        password.setBounds(150,200,100,20);
        password.setText("2511");
        
        Guest = new JButton("Guest");
        Guest.setVerticalTextPosition(AbstractButton.CENTER);
        Guest.setMnemonic(KeyEvent.VK_P);
        Guest.setActionCommand("Guest");
        Guest.setBounds(160,250,80,30);
        Guest.setIcon(bg);
        Guest.setIconTextGap(-55);
        
        Exit = new JButton("Exit");
        Exit.setVerticalTextPosition(AbstractButton.CENTER);
        Exit.setMnemonic(KeyEvent.VK_P);
        Exit.setActionCommand("Start");
        Exit.setBounds(260,250,80,30);
        Exit.setIcon(bg);
        Exit.setIconTextGap(-50);
        
        Login = new JButton("Login");
        Login.setVerticalTextPosition(AbstractButton.CENTER);
        Login.setMnemonic(KeyEvent.VK_P);
        Login.setActionCommand("Start");
        Login.setBounds(60,250,80,30);
        Login.setIcon(bg);
        Login.setIconTextGap(-55);
        
        //add a register button 
        register = new JButton("Register");
        register.setVerticalTextPosition(AbstractButton.CENTER);
        register.setMnemonic(KeyEvent.VK_P);
        register.setActionCommand("Start");
        register.setBounds(260,190,80,30);
        register.setIcon(bg);
        register.setIconTextGap(-60);
        
        //add a label for user name and password
        usernameTit = new JLabel("Username:" );  
        usernameTit.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        usernameTit.setSize(350,100);
        usernameTit.setBounds(90,175,210,30);
		passwordTit = new JLabel("Password:" );
		passwordTit.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		passwordTit.setSize(350,100);
		passwordTit.setBounds(90,195,210,30);
        
        //add a register action
        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               new RegisterScreen(f,fileName);
               myFrame.dispose();
            }  
        });
        
        Login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	userTxt = username.getText();
            	passTxt = password.getText();
            	boolean authenticated = isCorrect(userTxt,passTxt);
            	if (authenticated == true) {
            		JOptionPane.showMessageDialog(myFrame, "Welcome!!\n");
            		new TitleScreen(f, fileName);
            		myFrame.dispose();
            	} else if (authenticated == false) {
            		//JOptionPane.showMessageDialog(myFrame, "Wrong Username or Password\nPlease try again");
            		info.setText("Wrong Username or Password");
            		username.setText("");
            		password.setText("");
            	}
            }  
        });
        
        Guest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               new TitleScreen(f, fileName);
               myFrame.dispose();
            }  
        });
        
        Exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	myFrame.dispose();
            	System.exit(0);
            }  
        });
        
        control = new JPanel();
        control.setLayout(null);
        gameName = new JLabel("GridLock",JLabel.CENTER);  
        gameName.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        gameName.setSize(350,100);
        gameName.setBounds(100,100,210,30);
        
        control.add(register); // eve add register button to JPanel
        control.add(usernameTit); // eve add user name label to JPanel
        control.add(passwordTit); // eve add password label to JPanel
        control.add(Guest);
        control.add(Exit);
        control.add(Login);
        control.add(gameName);
        control.add(username);
        control.add(password);
        control.add(info);
        control.add(test);
        myFrame.add(control);
        
        myFrame.getContentPane();
        myFrame.setVisible(true);
	}
	
	private boolean isCorrect(String user,String pass) {
    	userTxt = username.getText();
    	passTxt = password.getText();
	    Scanner sc = null;
	    String inputFile = null;
	    try {
	        sc = new Scanner(new FileReader("src/credentials.txt")); 
	        while (sc.hasNextLine()) {                      // Scanner is used to read through the text file and
	      	  if (inputFile == null) {                      // pass along the text into input who contains the entire
	      		  inputFile = sc.nextLine() + "\n";         // text file sentences
	       	  } else {
	       		  inputFile = inputFile + sc.nextLine() + "\n";
	       	  }
	         }
	     }
	     catch (FileNotFoundException e) {}
	     finally
	     {
	         if (sc != null) sc.close();
	     }
	    String[] words = inputFile.split("\\s+");
    	for (int i = 0;i<words.length;i++) {
    		if (words[i].contains(userTxt)) {
    			if (words[i+1].contains(passTxt)) {
    				correct = true;
    			}
    		}
    	}
    	return correct;
	}
	
}
