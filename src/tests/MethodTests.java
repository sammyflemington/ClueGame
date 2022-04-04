// Authors: Eliot Edwards and Sammy Flemington

package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
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
		

		
	}
	
	// Test how suggestions are handled
	@Test
	public void testHandleSuggestion() {
		
		// Player disproves suggestion
		
		
		// Handle a suggestion that was made
		
		
	}
	
	// Test computer-generated suggestions are created properly
	@Test
	public void testCompSuggestion() {
		
		

		
		
	}
	
}
