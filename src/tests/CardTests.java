// Author: Eliot Edwards and Sammy Flemington

package tests;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Set;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.DoorDirection;
import clueGame.Player;
import clueGame.Solution;
import experiment.TestBoardCell;

public class CardTests {

	private static Board board;
	
	@BeforeAll
	public static void initialize() {
		// load stuff
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	// Test that deck has necessary components
	@Test
	public void testDeck() {
		
		ArrayList<Card> testDeck = board.getDeck();
		int rooms = 0, people = 0, weapons = 0;
		
		
		// Count types of cards
		for (int i = 0; i < testDeck.size(); i++) {
			if (testDeck.get(i).getCardType() == CardType.ROOM) {
				rooms++;
			} else if (testDeck.get(i).getCardType() == CardType.PERSON) {
				people++;
			} else if (testDeck.get(i).getCardType() == CardType.WEAPON) {
				weapons++;
			} else {
				assertEquals(1, 0);	// false, shouldn't happen
			}
		}
		
		// Check for correct numbers of each card
		assertEquals(9, rooms);
		assertEquals(6, people);
		assertEquals(6, weapons);
		assertEquals(21, testDeck.size());	// 6 weapons + 6 players + 9 rooms
		
		/*
		// Check for some specific cards
		for (Card card : testDeck) {
			
			assertTrue(testDeck.contains("Butter Knife"));	
			assertTrue(testDeck.contains("Butter Knife"));	
			
		}
		*/
		
	}
	
	// Test that players have appropriate cards
	@Test
	public void testPlayerHands() {
		
		ArrayList<Player> players = board.getPlayers();
		
		// Each player should have 3 cards		
		for (Player player : players) {
			ArrayList<Card> currentHand = player.getHand();
			assertEquals(3, currentHand.size());	
		}
		
		// No player should have the same card as another player
		ArrayList<Card> seenCards = new ArrayList<Card>();
		for (Player player : players) {
			ArrayList<Card> currentHand = player.getHand();
			for (Card card : currentHand) {
				if (seenCards.contains(card)) {
					assertEquals(0, 1); 	// false, shouldn't happen
				} else {
					seenCards.add(card);	// add player's hand to seen cards
				}
			}

		}
		
	}
	
	@Test
	public void testSolutionCards() {
		
		// Should be exactly 1 room card, 1 person card, and 1 weapon card
		Solution newSolution = board.getSolution();
		ArrayList<Card> solutionCards = newSolution.getCards();
		
		assertEquals(3, solutionCards.size());
		
		// Count types of cards
		int rooms = 0, people = 0, weapons = 0;
		for (Card c : solutionCards) {
			if (c.getCardType() == CardType.ROOM) {
				rooms++;
			} else if (c.getCardType() == CardType.PERSON) {
				people++;
			} else if (c.getCardType() == CardType.WEAPON) {
				weapons++;
			} else {
				assertEquals(1, 0);	// false, shouldn't happen
			}
		}
		
		// Check for correct numbers of each card
		assertEquals(1, rooms);
		assertEquals(1, people);
		assertEquals(1, weapons);
		
		// No player should have a card that is one of the solutions
		ArrayList<Player> players = board.getPlayers();
		
		for (Player player : players) {
			ArrayList<Card> currentHand = player.getHand();
			for (Card card : currentHand) {
				if (solutionCards.contains(card)) {
					assertEquals(0, 1); 	// false, shouldn't happen
				}
			}
		}
		
	}
	
}
