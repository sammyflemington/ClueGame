// Authors: Eliot Edwards and Sammy Flemington

package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;

public class MethodTests {

	private static Board board;
	
	@BeforeAll
	public static void initialize() {
		// load stuff
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	// Test accusations
	@Test
	public void testAccusation() {
		
		ArrayList<Card> testDeck = board.getDeck();
		
		// Accusation is correct
		
		// Accusation has wrong room
		
		// Accusation has wrong weapon

		// Accusation has wrong person
		
	}
	
	// Test disproving suggestions
	@Test
	public void testDisproveSuggestion() {
		
		// If player only has one card that can disprove, that card is returned
		
		// If player has more than one card that can disprove, 
		//		thatcard is chosen randomly and returned
		
		// If player has no matching cards, should return null
			
	}
	
	// Test how suggestions are handled
	@Test
	public void testHandleSuggestion() {

		// If no one can disprove suggestion, returns null
		
		// If only the player that makes the suggestion can disprove, returns null
		
		// If only human player can disprove, returns one of their cards that can disprove
		
		// If two players can disprove, next player in order returns their disproving card

	}
	
	// Test computer-generated suggestions are created properly
	@Test
	public void testCompSuggestion() {
		
		// Player is in the room center that they are suggesting
		
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
