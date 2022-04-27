/**Authors:
 * @Author Eliot Edwards
 * @Author Sammy Flemington
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
/*
 * ClueGameFrame class:
 * 
 *	- Contains ClueListener class which implements MouseListener (keeps track of mouse activity)
 *	- Draws board, draws cells in board, draws rooms, draws players, contains entry point for application.
 *	- Holds control panel and card panels
 */
public class ClueGameFrame extends JFrame {
	
	static Board board = Board.getInstance();
	static GameControlPanel gameControlPanel;
	static CardPanel cardPanel;
	private Player currentPlayer;
	
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
		addMouseListener(new ClueListener());		// Add listener to the panel
		currentPlayer = board.getHumanPlayer();
		board.setPanel(gameControlPanel);
	}
	
	/*
	 * ClueListener class:
	 * 
	 * This class keeps track of the mouse events, including:
	 * 	- mouse clicked
	 * 	- mouse pressed
	 * 	- mouse released
	 * 	- mouse entered
	 * 	- mouse exited
	 */
	private class ClueListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {			
		// Press and release of the mouse button
					
			int x = e.getX();
			int y = e.getY();
			
			// Check if human player's turn and if a valid target was clicked
			if (currentPlayer instanceof HumanPlayer) {
				if (board.checkTargetClicked(x, y) == null) {
					// Error msg box
					JFrame splash = new JFrame("");
					String text = "Please choose a valid target.";
					JLabel label = new JLabel("<html><p align=center>" + text);
					JOptionPane.showMessageDialog(splash, label, "CLUE ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					// Choose that target
					board.setHumanMove(board.checkTargetClicked(x, y));
				}
			}
			
			repaint();	// Repaint the panel
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		
	}
	
	// Main for game play!!
	public static void main(String[] args) {		
		ClueGameFrame game = new ClueGameFrame();
		HumanPlayer p = board.getHumanPlayer();
		
		// Splash screen
		// arguments (type): frame (JFrame), message (String), frame title (String), icon type
		JFrame splash = new JFrame("");
		String text = "You are " + board.getHumanPlayer().getName() + ".<br>" + "Can you find the killer<br>" + "before the Computer players?";
		JLabel label = new JLabel("<html><p align=center>" + text);
        //label.setHorizontalAlignment(JLabel.CENTER);		-- this may be redundant
		JOptionPane.showMessageDialog(splash, label, "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);
		
		// Game play screen
		cardPanel.update(p);
		game.setSize(850, 800);	// had to change this because it wouldn't fit on my screen lol -E
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		game.setVisible(true); 
		board.humanTurn();
		gameControlPanel.setRollNum(board.getRoll());
		
		
		
	}
}
