package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
/*
 * Draws board, draws cells in board, draws rooms, draws players,
 * contains entry point for application.
 * ALso holds control panel and card panels
 */
public class ClueGameFrame extends JFrame{
	static Board board = Board.getInstance();
	static GameControlPanel gameControlPanel;
	static CardPanel cardPanel;
	public ClueGameFrame() {
		super();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		add(board, BorderLayout.CENTER);			// put board in center to start
		cardPanel = new CardPanel();
		gameControlPanel = new GameControlPanel();
		add(cardPanel, BorderLayout.EAST);			// put card panel on right, shifts board over
		add(gameControlPanel, BorderLayout.SOUTH);	// put game control panel on bottom
		setTitle("Really Good Lookin' Clue!");
	}
	
	public static void main(String[] args) {		
		ClueGameFrame game = new ClueGameFrame();
		HumanPlayer p = board.getHumanPlayer();
		
		// Splash screen
		// arguments: frame (JFrame), message (String), frame title (string), icon type
		JFrame splash = new JFrame("");
		String text = "You are " + board.getHumanPlayer().getName() + ".<br>" + "Can you find the killer<br>" + "before the Computer players?";
		JLabel label = new JLabel("<html><p align=center>" + text);
        //label.setHorizontalAlignment(JLabel.CENTER);		-- this may be redundant
		JOptionPane.showMessageDialog(splash, label, "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
		
		cardPanel.update(p);
		game.setSize(900, 900);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		game.setVisible(true); // make it visible
	}
}
