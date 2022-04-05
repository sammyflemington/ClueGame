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
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class GameSolutionTest {

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
		Solution solution = board.getSolution();
		Solution accCorrect = new Solution(solution.getRoom(), solution.getPerson(), solution.getWeapon());
		Solution accWrongR = new Solution(new Card("wrong", CardType.ROOM), solution.getPerson(), solution.getWeapon());
		Solution accWrongP = new Solution(solution.getRoom(), new Card("wrong", CardType.PERSON), solution.getWeapon());
		Solution accWrongW = new Solution(solution.getRoom(), solution.getPerson(), new Card("wrong", CardType.WEAPON));
		// Accusation is correct
		assertEquals(board.checkAccusation(accCorrect), true);
		// Accusation has wrong room
		assertEquals(board.checkAccusation(accWrongR), false);
		// Accusation has wrong weapon
		assertEquals(board.checkAccusation(accWrongW), false);
		// Accusation has wrong person
		assertEquals(board.checkAccusation(accWrongP), false);
	}
	
	// Test disproving suggestions
	@Test
	public void testDisproveSuggestion() {
		Card person = new Card("A", CardType.PERSON);
		Card weapon = new Card("B", CardType.WEAPON);
		Card room = new Card("C", CardType.ROOM);
		Solution suggestion = new Solution(room, person, weapon);

		
		// If player only has one card that can disprove, that card is returned
		Player p = board.getPlayers().get(1);
		p.updateHand(room);
		p.updateHand(new Card("D", CardType.ROOM));
		p.updateHand(new Card("E", CardType.PERSON));
		Card result = p.disproveSuggestion(suggestion);
		
		assert(result.equals(room)); // player has room card in hand.
		
		// If player has more than one card that can disprove, 
		//		thatcard is chosen randomly and returned
		p.clearHand();
		p.updateHand(room);
		p.updateHand(person);
		p.updateHand(new Card("E", CardType.PERSON));
		
		// Test 100 times to ensure randomness
		int c1 = 0, c2 = 0;
		for (int i = 0; i < 100; i++) {
			result = p.disproveSuggestion(suggestion);
			if (result.equals(room)){
				c1++;
			}else if (result.equals(person)) {
				c2++;
			}
		}
		assert(c1 + c2 == 100);
		assert(c1 > 10);
		assert(c2 > 10);
		
		// If player has no matching cards, should return null
		p.clearHand();
		p.updateHand(new Card("E", CardType.PERSON));
		p.updateHand(new Card("D", CardType.ROOM));
		p.updateHand(new Card("F", CardType.ROOM));
		result = p.disproveSuggestion(suggestion);
		assert(result.equals(null));
	}
	
	// Test how suggestions are handled
	@Test
	public void testHandleSuggestion() {

		// If no one can disprove suggestion, returns null
		Solution suggestion = board.getSolution(); // solution can never be disproved
		for (Player p : board.getPlayers()) {
			Card c = p.disproveSuggestion(suggestion);
			assert(c.equals(null));
		}
		// If only the player that makes the suggestion can disprove, returns null
		board.nextTurn();
		int turn = board.getTurn();
		Player player = board.getPlayers().get(turn);
		suggestion = new Solution(player.getHand().get(0), new Card("a", CardType.ROOM), new Card("b", CardType.ROOM));
		assertEquals(board.handleSuggestion(suggestion), null);
		
		// If only human player can disprove, returns one of their cards that can disprove
		Player human = null;
		for (Player p : board.getPlayers()) {
			if (p instanceof HumanPlayer) {
				human = p;
				break;
			}
		}
		// make sure it's not human's turn
		board.setTurn(board.getPlayers().indexOf(human));
		board.nextTurn();
		suggestion = new Solution(human.getHand().get(0), new Card("a", CardType.ROOM), new Card("b", CardType.ROOM));
		assertEquals(board.handleSuggestion(suggestion), human.getHand().get(0));
		
		// If two players can disprove, next player in order returns their disproving card

		Player suggester = board.getPlayers().get(board.getTurn());
		board.nextTurn();
		Player next = board.getPlayers().get(board.getTurn());
		board.nextTurn();
		Player third = board.getPlayers().get(board.getTurn());
		
		// next's card should be returned, not third's
		suggestion = new Solution(next.getHand().get(0), third.getHand().get(0), new Card("b", CardType.ROOM));
		
		assertEquals(board.handleSuggestion(suggestion), next.getHand().get(0));
	}
	
}
