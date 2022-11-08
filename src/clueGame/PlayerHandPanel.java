package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerHandPanel extends JPanel {
    private JPanel outerPanel,peoplePanel,roomsPanel,weaponsPanel;

    public PlayerHandPanel() {
        setLayout(new GridLayout(0,1));

        outerPanel = new JPanel();
        outerPanel.setLayout(new GridLayout(3,0));
        outerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

        peoplePanel = createPanel("People");
        roomsPanel = createPanel("Rooms");
        weaponsPanel = createPanel("Weapons");
        
        outerPanel.add(peoplePanel);
        outerPanel.add(roomsPanel);
        outerPanel.add(weaponsPanel);

        add(outerPanel);
    }

    public JPanel createPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(), title));

        JLabel handLabel,seenLabel;
        JComboBox<String> handCombo,seenCombo;
        handLabel = new JLabel("In Hand:");
        seenLabel = new JLabel("Seen:");
        handCombo = new JComboBox<>();
        seenCombo = new JComboBox<>();

        

        return panel;
    }

    public static void main(String[] args) {
        PlayerHandPanel panel = new PlayerHandPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setSize(150,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
