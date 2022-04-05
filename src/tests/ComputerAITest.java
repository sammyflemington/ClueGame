// Authors: Eliot Edwards and Sammy Flemington

package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
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
		// get a COMPUTER player
		int turn = board.getTurn();
		Player p = board.getPlayers().get(turn);
		while (p instanceof HumanPlayer) {
			board.nextTurn();
			turn = board.getTurn();
			p = board.getPlayers().get(turn);
		}
		
		ComputerPlayer cp = (ComputerPlayer) p;
		// Player is in the room center that they are suggesting
		cp.moveTo(13, 20); // fluffle study
		Solution suggestion = cp.makeSuggestion(board.getDeck());
		assert(suggestion.getRoom().equals(new Card("Mister Fluffersons Study", CardType.ROOM)) );
		
		// IF more than one card can be used, randomly select both weapon and person
		cp.updateSeen(new Card("Butter Knife", CardType.WEAPON));
		cp.updateSeen(new Card("Small Stick", CardType.WEAPON));
		cp.updateSeen(new Card("Single Marble", CardType.WEAPON));
		cp.updateSeen(new Card("Rusty Nail", CardType.WEAPON));
		cp.updateSeen(new Card("Sammy", CardType.PERSON));
		cp.updateSeen(new Card("Ellie", CardType.PERSON));
		cp.updateSeen(new Card("Luka", CardType.PERSON));
		cp.updateSeen(new Card("Snoop Dogg", CardType.PERSON));
		int c1=0,c2=0,c3=0,c4=0;
		for (int i = 0; i < 100; i++) {
			Solution s = cp.makeSuggestion(board.getDeck());
			if (s.getPerson().equals(new Card("Your Mom", CardType.PERSON)))
				c1++;
			else if (s.getPerson().equals(new Card("Mister Flufferson", CardType.PERSON)))
				c2++;
			if (s.getWeapon().equals(new Card("Plastic Bucket", CardType.WEAPON)))
				c3++;
			else if (s.getWeapon().equals(new Card("Used Needle", CardType.WEAPON)))
				c4++;
		}
		assert(c1 > 10);
		assert(c2 > 10);
		assert(c3 > 10);
		assert(c4 > 10);
		
		// If only one weapon is not seen, it's selected
		cp.updateSeen(new Card("Plastic Bucket", CardType.WEAPON));
		
		suggestion = cp.makeSuggestion(board.getDeck());
		assert(suggestion.getWeapon().equals(new Card("Used Needle", CardType.WEAPON)));
		
		// If only one person is not seen, it's selected
		cp.updateSeen(new Card("Your Mom", CardType.PERSON));
		
		suggestion = cp.makeSuggestion(board.getDeck());
		assert(suggestion.getPerson().equals(new Card("Mister Flufferson", CardType.PERSON)));
		
	}
	
	// Test Computer targets
	@Test
	public void testComputerTargets() {
		
		// If no rooms in targetList, 
		
	}
		
}
