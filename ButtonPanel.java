
import javax.swing.*;

import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 *
 */
public class ButtonPanel extends JPanel {
	
	//selected level string
	static private String selectedLevelString(ItemSelectable is) {   
	    Object selected[] = is.getSelectedObjects();   
	    return ((selected.length == 0) ? "null" : (String) selected[0]);   
	  } 

    // Variables declaration
    private javax.swing.JComboBox<String> DifficultyBox;
    private javax.swing.JLabel DifficultyLabel;
    private javax.swing.JButton HintBtn;
    private javax.swing.JComboBox<String> LevelBox;
    private javax.swing.JLabel LevelLabel;
    private javax.swing.JButton LoadGameBtn;
    private javax.swing.JLabel MoveCount;
    private javax.swing.JButton RedoBtn;
    private javax.swing.JButton UndoBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton jResetBtn;
    private javax.swing.JLabel jTimeLabel;
    private JFrame f;
    
    /**
     * Creates new form NewJFrame
     */
    public ButtonPanel(JPanel container, JFrame f) {
        this.f = f;
    		initComponents(container);
	}

	/**
     * This method is called from within the constructor to initialize the form.
     */

    private void initComponents(JPanel container) {


        LoadGameBtn = new javax.swing.JButton();
        jResetBtn = new javax.swing.JButton();
        jTimeLabel = new javax.swing.JLabel();
        MoveCount = new javax.swing.JLabel();
        HintBtn = new javax.swing.JButton();
        UndoBtn = new javax.swing.JButton();
        RedoBtn = new javax.swing.JButton();
        DifficultyLabel = new javax.swing.JLabel();
        DifficultyBox = new javax.swing.JComboBox<>();
        LevelLabel = new javax.swing.JLabel();
        LevelBox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();


        setPreferredSize(new java.awt.Dimension(300, 280));

        this.setBorder(javax.swing.BorderFactory.createTitledBorder("Rush Hour"));

        LoadGameBtn.setText("Load Game");

        jResetBtn.setText("Reset");
        jResetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jResetBtnActionPerformed(evt);
            }
        });

        jTimeLabel.setText("Time: 11:11");

        MoveCount.setText("Moves: 15");

        HintBtn.setText("Hint");

        UndoBtn.setText("Undo");

        RedoBtn.setText("Redo");
        RedoBtn.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RedoBtnActionPerformed(evt);
            }
        });

        DifficultyLabel.setText("Difficulty:");

        DifficultyBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Novice", "Intermediate", "Expert" }));

        LevelLabel.setText("Puzzle: ");

        LevelBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a level","Puzzle 1", "Puzzle 2", "Puzzle 100" }));
 
        // listen select
        LevelBox.addItemListener(new ItemListener(){
         @Override
         	public void itemStateChanged(ItemEvent e){
        	 		// if selected one item
        	 		if (e.getStateChange() == ItemEvent.SELECTED){
        	 			// read the level name  
        	 			String text=(String) LevelBox.getSelectedItem(); 
        	 			System.out.println(text);
        	 			// dispose the frame
        	 			f.dispose();
        	 			// get net fileNmae
        	 			String[] s = text.split(" ");;
        	 			String fileName = "puzzle"+s[1]+".txt";
        	 			new Board(fileName);
        	 		}
         	}
        });   
        
        jButton1.setText("Create Randomized Game");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ButtonPanelLayout = new GroupLayout(this);
        this.setLayout(ButtonPanelLayout);
        ButtonPanelLayout.setHorizontalGroup(
                ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(ButtonPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(ButtonPanelLayout.createSequentialGroup()
                                                .addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(DifficultyLabel)
                                                        .addComponent(DifficultyBox, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(UndoBtn)
                                                        .addComponent(RedoBtn))
                                                .addGap(18, 18, 18)
                                                .addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jTimeLabel)
                                                        .addComponent(MoveCount))
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(ButtonPanelLayout.createSequentialGroup()
                                                .addGroup(ButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(LevelLabel)
                                                        .addGroup(ButtonPanelLayout.createSequentialGroup()
                                                                .addComponent(LevelBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(LoadGameBtn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                .addComponent(HintBtn)
                                                                .addComponent(jButton1)
                                                                .addComponent(jResetBtn)))
                                                .addGap(0, 0, Short.MAX_VALUE))))
        );
        ButtonPanelLayout.setVerticalGroup(
                ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(ButtonPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(DifficultyLabel)
                                        .addComponent(jTimeLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(DifficultyBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(MoveCount))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LevelLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(LevelBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(LoadGameBtn))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                .addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(HintBtn)
                                        .addComponent(UndoBtn))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(ButtonPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jResetBtn)
                                        .addComponent(RedoBtn))
                                .addContainerGap())
        );

        container.add(this, java.awt.BorderLayout.CENTER);

    }

    private void RedoBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO 
    }

    private void jResetBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO 
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO 
    }



}
