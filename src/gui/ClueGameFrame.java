package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

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
		add(board, BorderLayout.CENTER);
		cardPanel = new CardPanel();
		gameControlPanel = new GameControlPanel();
		add(cardPanel, BorderLayout.EAST);
		add(gameControlPanel, BorderLayout.SOUTH);
		setTitle("Really Good Lookin' Clue!");
	}
	
	public static void main(String[] args) {
		ClueGameFrame game = new ClueGameFrame();
		HumanPlayer p = board.getHumanPlayer();
		cardPanel.update(p);
		game.setSize(900, 900);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		game.setVisible(true); // make it visible
	}
}
