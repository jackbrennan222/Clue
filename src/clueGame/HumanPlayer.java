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
	
	JDialog suggestionDialog;
	
	public HumanPlayer(String name, Color color) {
		super(name, color);
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}

	public void updateSeen(Card card) {
		return;
	}

	@Override
	public void doAccusation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeSuggestion(Room room) {
		suggestionDialog = new JDialog(ClueGame.getInstance(), "Make a Suggestion", true);
		suggestionDialog.setLayout(new GridLayout(4,0));
		
		JPanel roomPanel = createSuggestionSubPanel("", CardType.ROOM, room);
		JPanel personPanel = createSuggestionSubPanel("", CardType.PERSON, room);
		JPanel weaponPanel = createSuggestionSubPanel("", CardType.WEAPON, room);
		
		suggestionDialog.add(roomPanel);
		suggestionDialog.add(personPanel);
		suggestionDialog.add(weaponPanel);
		
		JPanel bottomPanel = new JPanel(new GridLayout(0,2));
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new submitButtonListener());
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new cancelButtonListener());
		
		bottomPanel.add(submitButton);
		bottomPanel.add(cancelButton);
		
		suggestionDialog.add(bottomPanel);
		
		suggestionDialog.setVisible(true);
		
	}
	
	private JPanel createSuggestionSubPanel(String labelString, CardType type, Room room) {
		JPanel panel = new JPanel(new GridLayout(0,2));
		
		JLabel label = new JLabel(labelString);
		panel.add(label);
		
		if (type == CardType.ROOM) {
			JTextField textField = new JTextField(room.getName());
			textField.setEditable(false);
			panel.add(textField);
		} else {
			String[] list = new String[Board.getInstance().getRoomCards().size()];
			int i = 0;
			for (Card c : Board.getInstance().getDeck()) {
				if (c.getCardType() == type) {
					list[i] = c.getCardName();
					i++;
				}
			}
			JComboBox<String> comboBox = new JComboBox<String>(list);
			panel.add(comboBox);
		}
		
		return panel;
	}
	
	private class submitButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object[] components = suggestionDialog.getComponents();
			for (Object o : components) {
				if (o instanceof JComboBox) {
					
				}
			}
		}
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
