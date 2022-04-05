// Authors: Eliot Edwards and Sammy Flemington

package tests;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

public class ComputerAITest {

	private static Board board;
	
	@BeforeAll
	public static void initialize() {
		// load stuff
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	// Test computer-generated suggestions are created properly
	@Test
	public void testCompSuggestion() {
		
		int turn = board.getTurn();
		ComputerPlayer p = (ComputerPlayer) board.getPlayers().get(turn);

		// Player is in the room center that they are suggesting
		p.makeSuggestion(board.getDeck(), new Card("Magic Room", CardType.ROOM));
		
		// If only one weapon is not seen, it's selected
		
		// If only one person is not seen, it's selected
		
		// Otherwise, randomly select both weapon and person
		
	}
	
	// Test Computer targets
	@Test
	public void testComputerTargets() {
		
		// If no rooms in targetList, 
		
	}
		
}
