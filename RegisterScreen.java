import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

// eve add a registerScreen

public class RegisterScreen {
	private static JFrame myFrame;
	private static JPanel control;
	private JFrame f;
	private String fileName;
	JLabel pageName;
	JButton Done;
	JButton Back;
	JButton Exit;
	
	JTextField username;
	JTextField password; 
	
	private JLabel usernameTit; 
	private JLabel passwordTit;
	
	String userTxt;
	String passTxt;
	JLabel info;
	boolean correct = true;
	
	public RegisterScreen(JFrame f, String fileName) {
		this.f = f;
		this.fileName = fileName;
		
		myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(400,400);
        myFrame.setLocationRelativeTo(null);
        myFrame.setTitle("Register");
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
        
        password = new JTextField(20);  
        password.setEditable(true);
        password.setBounds(150,200,100,20);
        
        Done = new JButton("Done");
        Done.setVerticalTextPosition(AbstractButton.CENTER);
        Done.setMnemonic(KeyEvent.VK_P);
        Done.setActionCommand("Done");
        Done.setBounds(160,250,80,30);
        Done.setIcon(bg);
        Done.setIconTextGap(-55);
        
        Exit = new JButton("Exit");
        Exit.setVerticalTextPosition(AbstractButton.CENTER);
        Exit.setMnemonic(KeyEvent.VK_P);
        Exit.setActionCommand("Start");
        Exit.setBounds(260,250,80,30);
        Exit.setIcon(bg);
        Exit.setIconTextGap(-50);
        
        Back = new JButton("Back");
        Back.setVerticalTextPosition(AbstractButton.CENTER);
        Back.setMnemonic(KeyEvent.VK_P);
        Back.setActionCommand("Start");
        Back.setBounds(60,250,80,30);
        Back.setIcon(bg);
        Back.setIconTextGap(-55);
        
        usernameTit = new JLabel("Username:" );  
        usernameTit.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        usernameTit.setSize(350,100);
        usernameTit.setBounds(90,175,210,30);
        
		passwordTit = new JLabel("Password:" );
		passwordTit.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		passwordTit.setSize(350,100);
		passwordTit.setBounds(90,195,210,30);
        
        Done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	userTxt = username.getText();
            	passTxt = password.getText();
            	boolean authenticated = isCorrect(userTxt,passTxt);
            	if (authenticated == true) {
            		JOptionPane.showMessageDialog(myFrame, "Register Successfully!!\n");
            		new TitleScreen(f,fileName);
            		myFrame.dispose();
            	} else if (authenticated == false) {
            		//JOptionPane.showMessageDialog(myFrame, "Wrong Username or Password\nPlease try again");
            		info.setText("Username is Existed");
            		username.setText("");
            		password.setText("");
            	}
            }  
        });
        
        Back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               new MainMenu(f,fileName);
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
        pageName = new JLabel("Register",JLabel.CENTER);  
        pageName.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        pageName.setSize(350,100);
        pageName.setBounds(100,100,210,30);
        
        control.add(Done);
        control.add(Back);
        control.add(Exit);
        control.add(usernameTit); 
        control.add(passwordTit);
        control.add(pageName);
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
    		if (words[i].equals(userTxt)) {
    				correct = false;
    		}
    	}
    	
    	if(correct == true){
    		try {
    			String str = "\n"+ userTxt + " " + passTxt;	
				File file = new File("src/credentials.txt");
				FileWriter fileWriter = new FileWriter(file.getName(), true);
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
				bufferWriter.write(str);
				bufferWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return correct;
	}
}
