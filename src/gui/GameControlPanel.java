package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.ComputerPlayer;

public class GameControlPanel extends JPanel{
	private JTextField guessText;
	private JTextField guessResultText;
	private JTextField playerName;
	private JTextField rollNum;
	
	public GameControlPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		
		// Holds current player status, accusation and next buttons.
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4));
		mainPanel.add(topPanel);
		
		JPanel turnPanel = new JPanel();
		JLabel whoseTurn = new JLabel("Whose turn?");
		turnPanel.setLayout(new GridLayout(2, 1));
		playerName = new JTextField("");
		playerName.setEditable(false);
		turnPanel.add(whoseTurn, BorderLayout.NORTH); 
		turnPanel.add(playerName, BorderLayout.SOUTH);
		topPanel.add(turnPanel);
		
		JPanel rollPanel = new JPanel();
		JLabel roll = new JLabel("Roll: ");
		rollNum = new JTextField();
		rollNum.setEditable(false);
		rollPanel.add(roll);
		rollPanel.add(rollNum);
		topPanel.add(rollPanel);
		
		JButton accuseButton = new JButton("ACCUSATION!");
		topPanel.add(accuseButton);
		
		JButton nextButton = new JButton("Next!");
		topPanel.add(nextButton);
		
		
		// Holds guess information
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0, 2));
		mainPanel.add(bottomPanel);
		
		JPanel guessPanel = new JPanel();
		guessText = new JTextField("");
		guessText.setEditable(false);
		guessPanel.add(guessText);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		bottomPanel.add(guessPanel);
		
		JPanel guessResultPanel = new JPanel();
		guessResultText = new JTextField("");
		guessResultText.setEditable(false);
		guessResultPanel.add(guessResultText);
		guessResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		bottomPanel.add(guessResultPanel);
		
		setLayout(new GridLayout(1,1));
		add(mainPanel, BorderLayout.CENTER);
		//pack();
		
		
	}
	
	public void setGuess(String guess) {
		guessText.setText(guess);
		updateUI();
	}
	
	public void setGuessResult(String result) {
		guessResultText.setText(result);
		updateUI();
	}
	
	private void setTurn(ComputerPlayer computerPlayer, int i) {
		playerName.setText(computerPlayer.getName());
		playerName.setBackground(computerPlayer.getColor());
		rollNum.setText(Integer.toString(i));
		updateUI();
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.orange, 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

	
}
