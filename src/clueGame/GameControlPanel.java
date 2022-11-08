package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JPanel topPanel,topLeft,topNextLeft,bottomPanel,bottomLeft,bottomRight;
	private JLabel whoseTurn, rollLabel;
	private JTextField turnPlayer,rollField,guessField,guessResultField;
	private JButton accuseButton,nextButton;
	
	public GameControlPanel() {
		setLayout(new GridLayout(2,0));
		
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4));
		
		topLeft = new JPanel();
		topLeft.setLayout(new GridLayout(3,1));
		whoseTurn = new JLabel("Whose Turn?", SwingConstants.CENTER);
		turnPlayer = new JTextField();
		turnPlayer.setEditable(false);
		topLeft.add(whoseTurn);
		topLeft.add(turnPlayer);
		
		topNextLeft = new JPanel();
		topNextLeft.setLayout(new BorderLayout());
		topNextLeft.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
		rollLabel = new JLabel("Roll:", SwingConstants.RIGHT);
		rollField = new JTextField();
		rollField.setEditable(false);
		JPanel holder = new JPanel();
		holder.setLayout(new GridLayout(0,2));
		holder.add(rollLabel);
		holder.add(rollField);
		topNextLeft.add(holder, BorderLayout.NORTH);
		
		accuseButton = new JButton("Make Accusation");
		nextButton = new JButton("NEXT!");
		
		topPanel.add(topLeft);
		topPanel.add(topNextLeft);
		topPanel.add(accuseButton);
		topPanel.add(nextButton);
		add(topPanel);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0,2));
		
		bottomLeft = new JPanel();
		bottomLeft.setLayout(new GridLayout(1,0));
		bottomLeft.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guessField = new JTextField("");
		guessField.setEditable(false);
		bottomLeft.add(guessField);
		
		bottomRight = new JPanel();
		bottomRight.setLayout(new GridLayout(1,0));
		bottomRight.setBorder(new TitledBorder( new EtchedBorder(), "Guess Result"));
		guessResultField = new JTextField("");
		guessResultField.setEditable(false);
		bottomRight.add(guessResultField);
		
		bottomPanel.add(bottomLeft);
		bottomPanel.add(bottomRight);
		add(bottomPanel);
	}
	
	public void setTurn(Player player, int roll) {
		turnPlayer.setText(player.getName());
		turnPlayer.setBackground(player.getColor());
		rollField.setText(Integer.toString(roll));
	}
	
	public void setGuess(String guess) {
		guessField.setText(guess);
	}
	
	public void setGuessResult(String result) {
		guessResultField.setText(result);
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		frame.setResizable(false);
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, Color.orange), 5);
		panel.setGuessResult( "So you have nothing?");
		panel.setGuess( "I have no guess!");
	}
}
