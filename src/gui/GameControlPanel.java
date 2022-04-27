// Authors: Eliot Edwards and Sammy Flemington

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

/*
 * GameControlPanel class:
 * 
 * 	- Extends JPanel
 * 	- Displays and regulates buttons the user needs to play the game
 */
public class GameControlPanel extends JPanel implements ActionListener {
	private static Board board = Board.getInstance();
	
	private JTextField guessText;
	private String guess;
	
	private JTextField guessResultText;
	private JTextField playerName;
	private String name;
	
	private JTextField rollNum;
	private String roll;
	
	private JButton nextButton;
	private JButton accuseButton;
	
	private Player currentPlayer;
	
	public GameControlPanel() {
		currentPlayer = board.getHumanPlayer();
		name = currentPlayer.getName();
		roll = Integer.toString(board.getRoll());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		
		// Holds current player status, accusation and next buttons.
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4));
		mainPanel.add(topPanel);
		
		JPanel turnPanel = new JPanel();
		JLabel whoseTurn = new JLabel("Whose turn?");
		turnPanel.setLayout(new GridLayout(2, 1));
		playerName = new JTextField(name);
		playerName.setEditable(false);
		turnPanel.add(whoseTurn, BorderLayout.NORTH); 
		turnPanel.add(playerName, BorderLayout.SOUTH);
		topPanel.add(turnPanel);
		
		JPanel rollPanel = new JPanel();
		JLabel rollLabel = new JLabel("Roll: ");
		rollNum = new JTextField(roll);
		rollNum.setEditable(false);
		rollPanel.add(rollLabel);
		rollPanel.add(rollNum);
		topPanel.add(rollPanel);
		
		accuseButton = new JButton("ACCUSATION!");
		topPanel.add(accuseButton);
		accuseButton.addActionListener(this);
		
		nextButton = new JButton("Next!");
		topPanel.add(nextButton);
		nextButton.addActionListener(this);
		
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
		
		setLayout(new GridLayout(1, 1));
		add(mainPanel, BorderLayout.CENTER);
		
	}
	
	public void updateGameControl(String nameStr, String guessStr, String rollStr) {
		this.name = nameStr;
		this.guess = guessStr;
		this.roll = rollStr;
	}
	
	public JTextField getGuessText() {
		return guessText;
	}

	public void setGuessText(JTextField guessText) {
		this.guessText = guessText;
	}

	public JTextField getGuessResultText() {
		return guessResultText;
	}

	public void setGuessResultText(JTextField guessResultText) {
		this.guessResultText = guessResultText;
	}

	public String getPlayerName() {
		return playerName.getText();
	}

	public void setPlayerName(String str) {
		this.playerName.setText(str);
		updateUI();
	}

	public JTextField getRollNum() {
		return rollNum;
	}

	public void setRollNum(int rollNum) {
		this.rollNum.setText(Integer.toString(rollNum));
		updateUI();
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// Accuse button was clicked
		if (e.getSource() == accuseButton) {
			// allow human player to make accusation
			board.makeAccusationBox();
		}
		
		// Next button was clicked
		if (e.getSource() == nextButton) {		
			// Human player, turn is over
			if ((currentPlayer.isTurnOver()) && (currentPlayer instanceof HumanPlayer)) {
				board.nextPlayer();
				setRollNum(board.getRoll());
				setPlayerName(board.getCurrentPlayer().getName());
			} else if (!(currentPlayer instanceof HumanPlayer)) {
				board.nextPlayer();
				setRollNum(board.getRoll());
				setPlayerName(board.getCurrentPlayer().getName());
			} else {
				JOptionPane.showMessageDialog(this, "Wait! Your turn is not finished!", "CLUE ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(600, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", Color.orange, 0, 0), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

	
}
