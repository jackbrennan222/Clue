package clueGame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HumanPlayer extends Player {
	
	JDialog suggestionDialog,accusationDialog;
	
	public HumanPlayer(String name, Color color) {
		super(name, color);
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}

	@Override
	public void doAccusation() {
		accusationDialog = new JDialog(ClueGame.getInstance(), "Make a Suggestion", true);
		accusationDialog.setLayout(new GridLayout(4,0));

		JPanel roomPanel = new JPanel(new GridLayout(0,2));
		JPanel personPanel = new JPanel(new GridLayout(0,2));
		JPanel weaponPanel = new JPanel(new GridLayout(0,2));
		
		JLabel roomLabel = new JLabel("Room");		
		JLabel personLabel = new JLabel("Person");		
		JLabel weaponLabel = new JLabel("Weapon");		
		
		JComboBox<String> roomCombo = createComboBox(CardType.ROOM);
		JComboBox<String> personCombo = createComboBox(CardType.PERSON);
		JComboBox<String> weaponCombo = createComboBox(CardType.WEAPON);

		roomPanel.add(roomLabel);
		roomPanel.add(roomCombo);

		personPanel.add(personLabel);
		personPanel.add(personCombo);

		weaponPanel.add(weaponLabel);
		weaponPanel.add(weaponCombo);
		
		accusationDialog.add(roomPanel);
		accusationDialog.add(personPanel);
		accusationDialog.add(weaponPanel);
		
		JPanel bottomPanel = new JPanel(new GridLayout(0,2));
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new submitAccusationButtonListener());
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelButtonListener());
		
		bottomPanel.add(submitButton);
		bottomPanel.add(cancelButton);
		
		accusationDialog.add(bottomPanel);
		
		accusationDialog.setSize(300, 200);
		accusationDialog.setLocation(Board.getInstance().getWidth() / 2, Board.getInstance().getHeight() / 2);
		accusationDialog.setVisible(true);
		
	}

	public Solution createSuggestion() {
		return new Solution(null, null, null);
	}

	public void selectSuggestion() {
		Board board = Board.getInstance();
		Room room = board.getRoom(getCell());

		suggestionDialog = new JDialog(ClueGame.getInstance(), "Make a Suggestion", true);
		suggestionDialog.setLayout(new GridLayout(4,0));

		JPanel roomPanel = new JPanel(new GridLayout(0,2));
		JPanel personPanel = new JPanel(new GridLayout(0,2));
		JPanel weaponPanel = new JPanel(new GridLayout(0,2));
		
		JLabel roomLabel = new JLabel("Current Room");		
		JLabel personLabel = new JLabel("Person");		
		JLabel weaponLabel = new JLabel("Weapon");		
		
		JTextField roomTextField = new JTextField(room.getName());
		roomTextField.setEditable(false);
		JComboBox<String> personCombo = createComboBox(CardType.PERSON);
		JComboBox<String> weaponCombo = createComboBox(CardType.WEAPON);

		roomPanel.add(roomLabel);
		roomPanel.add(roomTextField);

		personPanel.add(personLabel);
		personPanel.add(personCombo);

		weaponPanel.add(weaponLabel);
		weaponPanel.add(weaponCombo);
		
		suggestionDialog.add(roomPanel);
		suggestionDialog.add(personPanel);
		suggestionDialog.add(weaponPanel);
		
		JPanel bottomPanel = new JPanel(new GridLayout(0,2));
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new submitSuggestionButtonListener());
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelButtonListener());
		
		bottomPanel.add(submitButton);
		bottomPanel.add(cancelButton);
		
		suggestionDialog.add(bottomPanel);
		
		suggestionDialog.setSize(300, 200);
		suggestionDialog.setLocation(Board.getInstance().getWidth() / 2, Board.getInstance().getHeight() / 2);
		suggestionDialog.setVisible(true);
	}
	
	private JComboBox<String> createComboBox(CardType type) {
		String[] list = new String[Board.getInstance().getRoomCards().size()];
		int i = 0;
		for (Card c : Board.getInstance().getDeck()) {
			if (c.getCardType() == type) {
				list[i] = c.getCardName();
				i++;
			}
		}
		return new JComboBox<String>(list);
	}
	
	private class submitSuggestionButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] selectedStrings = new String[3];
			int i = 0;
			Object[] components = suggestionDialog.getContentPane().getComponents();
			for (Object panel : components) {
				JPanel panelObj = (JPanel) panel;
				for (Object o : panelObj.getComponents()) {
					if (o instanceof JTextField) {
						JTextField tf = (JTextField) o;
						selectedStrings[i] = tf.getText();
						i++;
					} else if (o instanceof JComboBox) {
						JComboBox cb = (JComboBox) o;
						selectedStrings[i] = cb.getSelectedItem().toString();
						i++;
					}
				}
			}
			Card roomCard = new Card();
			Card personCard = new Card();
			Card weaponCard = new Card();
			for (Card c: Board.getInstance().getDeck()) {
				if (c.getCardName().equals(selectedStrings[0])) { roomCard = c; }
				if (c.getCardName().equals(selectedStrings[1])) { personCard = c; }
				if (c.getCardName().equals(selectedStrings[2])) { weaponCard = c; }
			}
			Solution suggestion = new Solution(roomCard, personCard, weaponCard);
			sendSolution(suggestion);
			ClueGame.getInstance().gamePanelUpdate(suggestion);
			suggestionDialog.dispose();
		}
	}

	private class submitAccusationButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String[] selectedStrings = new String[3];
			int i = 0;
			Object[] components = accusationDialog.getContentPane().getComponents();
			for (Object panel : components) {
				JPanel panelObj = (JPanel) panel;
				for (Object o : panelObj.getComponents()) {
					if (o instanceof JComboBox) {
						JComboBox cb = (JComboBox) o;
						selectedStrings[i] = cb.getSelectedItem().toString();
						i++;
					}
				}
			}
			Card roomCard = new Card();
			Card personCard = new Card();
			Card weaponCard = new Card();
			for (Card c: Board.getInstance().getDeck()) {
				if (c.getCardName().equals(selectedStrings[0])) { roomCard = c; }
				if (c.getCardName().equals(selectedStrings[1])) { personCard = c; }
				if (c.getCardName().equals(selectedStrings[2])) { weaponCard = c; }
			}
			accusationDialog.dispose();
			ClueGame.humanAccuse(Board.getInstance().checkAccusation(roomCard, personCard, weaponCard));
		}
		
	}
	
	private void sendSolution(Solution solution) {
		seenCards.add(Board.getInstance().handleSuggestion(this, solution));
	}
	
	private class cancelButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			suggestionDialog.dispose();
		}
	}

	public void doMove(BoardCell cell) {
		getCell().setOccupied(false);
		setPos(cell);
		cell.setOccupied(true);
	}
}
