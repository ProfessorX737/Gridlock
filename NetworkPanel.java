
import javax.swing.*;
import java.awt.*;

public class NetworkPanel extends JPanel {
    private JButton ChallengeBtn;
    private JButton ConnectBtn;
    private JLabel LossLabel;
    private JLabel OnlineUsrLabel;
    private JLabel OppoLabel;
    private JButton QuitBtn;
    private JButton SetUserBtn;
    private JButton UpdateBtn;
    private JButton ForfeitBtn;
    private JLabel UserLabel;
    private JComboBox<String> UserListBox;
    private JTextField UserTxtField;
    private JLabel WinLabel;
    public NetworkPanel(){
        GridBagConstraints gridBagConstraints;

        UserListBox = new JComboBox<>();
        ChallengeBtn = new JButton();
        SetUserBtn = new JButton();
        UserTxtField = new JTextField();
        UpdateBtn = new JButton();
        UserLabel = new JLabel();
        OnlineUsrLabel = new JLabel();
        QuitBtn = new JButton();
        OppoLabel = new JLabel();
        WinLabel = new JLabel();
        LossLabel = new JLabel();
        ConnectBtn = new JButton();
        ForfeitBtn = new JButton();

        setLayout(new java.awt.GridBagLayout());

        UserListBox.setModel(new DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
//        UserListBox.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                UserListBoxActionPerformed(evt);
//            }
//        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(UserListBox, gridBagConstraints);

        ChallengeBtn.setText("Challenge");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(ChallengeBtn, gridBagConstraints);

        SetUserBtn.setText("Set");
//        SetUserBtn.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                SetUserBtnActionPerformed(evt);
//            }
//        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(10, 4, 0, 0);
        add(SetUserBtn, gridBagConstraints);

        UserTxtField.setText("Username");
//        UserTxtField.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                UserTxtFieldActionPerformed(evt);
//            }
//        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(UserTxtField, gridBagConstraints);

        UpdateBtn.setText("Update");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(UpdateBtn, gridBagConstraints);

        UserLabel.setText("Username:  ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(UserLabel, gridBagConstraints);

        OnlineUsrLabel.setText("Online Users:  ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(OnlineUsrLabel, gridBagConstraints);

        QuitBtn.setText("Quit");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(QuitBtn, gridBagConstraints);

        OppoLabel.setText("Playing Against:");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(OppoLabel, gridBagConstraints);

        WinLabel.setText("Wins: 0");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(WinLabel, gridBagConstraints);

        LossLabel.setText("Losses: 0");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(10, 0, 0, 0);
        add(LossLabel, gridBagConstraints);

        ConnectBtn.setText("Connect To Server");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(10, 0, 0, 10);
        add(ConnectBtn, gridBagConstraints);

        ForfeitBtn.setText("Forfeit");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        add(ForfeitBtn, gridBagConstraints);

    }

    public void setController(NetworkUIController c) {
        this.ChallengeBtn.addActionListener(c.getChallengeBtnListener());
        this.SetUserBtn.addActionListener(c.getSetUserBtnListener());
        this.UpdateBtn.addActionListener(c.getUpdateBtnListener());
        this.ConnectBtn.addActionListener(c.ConnectBtnListener());
        this.QuitBtn.addActionListener(c.QuitBtnListener());
        this.ForfeitBtn.addActionListener(c.ForfeitListener());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NetworkPanel panel = new NetworkPanel();

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}
