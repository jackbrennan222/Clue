package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Jack Brennan
 * @author Erik Swanson
 */
public class GameControlPanel extends JPanel {
	private JPanel topPanel,topLeft,topNextLeft,bottomPanel,bottomLeft,bottomRight;
	private JLabel whoseTurn, rollLabel;
	private JTextField turnPlayer,rollField,guessField,guessResultField;
	private JButton accuseButton,nextButton;
	
	public GameControlPanel() {
		setLayout(new GridLayout(2,0));
		// setup the top panel
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4));
		// setup panel for turn info
		topLeft = new JPanel();
		topLeft.setLayout(new GridLayout(3,1));
		whoseTurn = new JLabel("Whose Turn?", SwingConstants.CENTER);
		turnPlayer = new JTextField(Board.getInstance().getCurrentPlayer().getName());
		turnPlayer.setBackground(Board.getInstance().getCurrentPlayer().getColor());
		turnPlayer.setEditable(false);
		topLeft.add(whoseTurn);
		topLeft.add(turnPlayer);
		// add panel for roll info
		topNextLeft = new JPanel();
		topNextLeft.setLayout(new BorderLayout());
		topNextLeft.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
		rollLabel = new JLabel("Roll:", SwingConstants.RIGHT);
		rollField = new JTextField(Integer.toString(Board.getInstance().getDice()));
		rollField.setEditable(false);
		JPanel holder = new JPanel();
		holder.setLayout(new GridLayout(0,2));
		holder.add(rollLabel);
		holder.add(rollField);
		topNextLeft.add(holder, BorderLayout.NORTH);
		// add buttons for game control
		accuseButton = new JButton("Make Accusation");
		accuseButton.addActionListener(new accuseButtonListener());
		nextButton = new JButton("NEXT!");
		nextButton.addActionListener(new nextButtonListener());
		// add other panels to top panel
		topPanel.add(topLeft);
		topPanel.add(topNextLeft);
		topPanel.add(accuseButton);
		topPanel.add(nextButton);
		add(topPanel);
		// add new panel for guess info
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0,2));
		// create left grid for guess
		bottomLeft = new JPanel();
		bottomLeft.setLayout(new GridLayout(1,0));
		bottomLeft.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guessField = new JTextField("");
		guessField.setEditable(false);
		bottomLeft.add(guessField);
		// create right grid for guess result
		bottomRight = new JPanel();
		bottomRight.setLayout(new GridLayout(1,0));
		bottomRight.setBorder(new TitledBorder( new EtchedBorder(), "Guess Result"));
		guessResultField = new JTextField("");
		guessResultField.setEditable(false);
		bottomRight.add(guessResultField);
		// add all of bottom panel to window
		bottomPanel.add(bottomLeft);
		bottomPanel.add(bottomRight);
		add(bottomPanel);
	}
	
	/** 
	 * @param player
	 * @param roll
	 */
	public void setTurn(Player player, int roll) {
		turnPlayer.setText(player.getName());
		turnPlayer.setBackground(player.getColor());
		rollField.setText(Integer.toString(roll));
	}
	
	/** 
	 * @param guess
	 */
	public void setGuess(Solution sol) {
		guessField.setText(sol.getPerson().getCardName() + ", " + sol.getRoom().getCardName() + ", " + sol.getWeapon().getCardName());
		guessField.setBackground(Board.getInstance().getCurrentPlayer().getColor());
	}
	
	/** 
	 * @param result
	 */
	public void setGuessResult(String result, Color color) {
		guessResultField.setText(result);
		guessResultField.setBackground(color);
	}

	/**
     * Researched in order to get desirable width for this panel in ClueGame JFrame
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(super.getPreferredSize().width, 150);
    }

	/**
	 * logic for the next button
	 */
	public class nextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Board board = Board.getInstance();
			board.next();
			Player curPlayer = board.getCurrentPlayer();
			setTurn(curPlayer, board.getDice());
		}
	}

	public class accuseButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Board.getInstance().makeAccusation();
		}
	}
}
