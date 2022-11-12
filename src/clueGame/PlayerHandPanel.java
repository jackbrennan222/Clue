package clueGame;

import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerHandPanel extends JPanel {
    private JPanel outerPanel,peoplePanel,roomsPanel,weaponsPanel;
    private Player human;

    public PlayerHandPanel() {
        for (Player p : Board.getInstance().getPlayers()) {
            if (p instanceof HumanPlayer) {
                human = p;
            }
        }

        setLayout(new GridLayout(0,1));

        outerPanel = new JPanel();
        outerPanel.setLayout(new GridLayout(3,0));
        outerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

        peoplePanel = createPanel(CardType.PERSON, "People");
        roomsPanel = createPanel(CardType.ROOM, "Rooms");
        weaponsPanel = createPanel(CardType.WEAPON, "Weapons");
        
        outerPanel.add(peoplePanel);
        outerPanel.add(roomsPanel);
        outerPanel.add(weaponsPanel);

        add(outerPanel);
    }

    public JPanel createPanel(CardType type, String title) {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(), title));
        panel.setLayout(new GridLayout(0,1));

        JLabel handLabel,seenLabel;
        handLabel = new JLabel("In Hand:");
        seenLabel = new JLabel("Seen:");

        panel.add(handLabel);
        
        int numCardsOfType = 0;
        for (Card c : human.getHand()) {
        	if (c.getCardType() == type) {
        		numCardsOfType++;
        		JTextField cardField = new JTextField(c.getCardName());
        		cardField.setBackground(human.getColor());
        		cardField.setEditable(false);
        		panel.add(cardField);
        	}
        }
        if (numCardsOfType == 0) {
        	JTextField cardField = new JTextField("None");
    		cardField.setBackground(human.getColor());
    		cardField.setEditable(false);
    		panel.add(cardField);
        }

        numCardsOfType = 0;
        panel.add(seenLabel);
        for (Card c : Board.getInstance().getDeck()) {
            if (c.getCardType() == type) {
                if (human.seenCards.contains(c)) {
                    numCardsOfType++;
                    JTextField cardField = new JTextField(c.getCardName());
                    cardField.setEditable(false);
                    for (Player p : Board.getInstance().getPlayers()) {
                        if (p instanceof ComputerPlayer && p.getHand().contains(c)) {
                            cardField.setBackground(p.getColor());
                            panel.add(cardField);
                        }
                    }
                }
            }
        }
        if (numCardsOfType == 0) {
        	JTextField cardField = new JTextField("None");
    		cardField.setBackground(human.getColor());
    		cardField.setEditable(false);
    		panel.add(cardField);
        }
        return panel;
    }

    /**
     * Called to update the panels
     */
    public void updatePanels() {
        peoplePanel  = createPanel(CardType.PERSON, "People");
        roomsPanel  = createPanel(CardType.ROOM, "Rooms");
        weaponsPanel  = createPanel(CardType.WEAPON, "Weapons");
    }

    /**
     * Researched in order to get desirable width for this panel in ClueGame JFrame
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(150, super.getPreferredSize().height);
    }
}
