package clueGame;

import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Jack Brennan
 * @author Erik Swanson
 */
public class PlayerHandPanel extends JPanel {
    private JPanel outerPanel,peoplePanel,roomsPanel,weaponsPanel;
    private Player human;

    public PlayerHandPanel() {
        // assign humanPlayer to the human var
        for (Player p : Board.getInstance().getPlayers()) {
            if (p instanceof HumanPlayer) {
                human = p;
            }
        }

        setLayout(new GridLayout(0,1));
        // create outer panel and set properties
        outerPanel = new JPanel();
        outerPanel.setLayout(new GridLayout(3,0));
        outerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
        // create sub panels for each type of card
        peoplePanel = createPanel(CardType.PERSON, "People");
        roomsPanel = createPanel(CardType.ROOM, "Rooms");
        weaponsPanel = createPanel(CardType.WEAPON, "Weapons");
        // add sub panels to outer panel
        outerPanel.add(peoplePanel);
        outerPanel.add(roomsPanel);
        outerPanel.add(weaponsPanel);
        // add outer panel to main panel
        add(outerPanel);
    }

    /**
     * @param type
     * @param title
     * @return
     */
    public JPanel createPanel(CardType type, String title) {
        // new panel to make and return with properties specified below
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(new EtchedBorder(), title));
        panel.setLayout(new GridLayout(0,1));
        // create labels for hand cards and seen cards
        JLabel handLabel,seenLabel;
        handLabel = new JLabel("In Hand:");
        seenLabel = new JLabel("Seen:");
        // add first label
        panel.add(handLabel);
        // iterate through hand and add all cards of specified type
        int numCardsOfType = 0;
        for (Card c : human.getHand()) {
        	if (c.getCardType() == type) {
        		numCardsOfType++;
                // create TextField with properties below to display card
        		JTextField cardField = new JTextField(c.getCardName());
        		cardField.setBackground(human.getColor());
        		cardField.setEditable(false);
        		panel.add(cardField);
        	}
        }
        // display "None" if no cards in hand of specified type
        if (numCardsOfType == 0) {
            // create TextField with properties below to display card
        	JTextField cardField = new JTextField("None");
    		cardField.setBackground(human.getColor());
    		cardField.setEditable(false);
    		panel.add(cardField);
        }

        // iterate through deck and add all cards in the seenCards set
        numCardsOfType = 0;
        panel.add(seenLabel);
        for (Card c : Board.getInstance().getDeck()) {
            if (c.getCardType() == type) {
                if (human.seenCards.contains(c)) {
                    numCardsOfType++;
                    // create TextField with properties below to display card
                    JTextField cardField = new JTextField(c.getCardName());
                    cardField.setEditable(false);
                    // iterate through players hands to find which player has the card
                    for (Player p : Board.getInstance().getPlayers()) {
                        if (p instanceof ComputerPlayer && p.getHand().contains(c)) {
                            // set the color of the card to the color of the player whose hand the card is in
                            cardField.setBackground(p.getColor());
                            break;
                        }
                    }
                    panel.add(cardField);
                }
            }
        }
        // display "None" if in seen of specified type
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
        // remove elements so we can repaint them
        removeAll();
        // recreate outer panels with specifications
        outerPanel = new JPanel();
        outerPanel.setLayout(new GridLayout(3,0));
        outerPanel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
        // recreate sub panels with new information
        peoplePanel = createPanel(CardType.PERSON, "People");
        roomsPanel = createPanel(CardType.ROOM, "Rooms");
        weaponsPanel = createPanel(CardType.WEAPON, "Weapons");
        // add sub panels to outer panel
        outerPanel.add(peoplePanel);
        outerPanel.add(roomsPanel);
        outerPanel.add(weaponsPanel);
        // add outer panel and instruct swing to refresh
        add(outerPanel);
        revalidate();
    }

    /**
     * Researched in order to get desirable width for this panel in ClueGame JFrame
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(150, super.getPreferredSize().height);
    }
}
