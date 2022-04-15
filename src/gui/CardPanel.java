// Authors: Eliot Edwards and Sammy Flemington

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;

/*
 * CardPanel Class:
 * 
 * 	- Extends JPanel
 * 	- Organizes and displays the cards the HumanPlayer has seen
 */
public class CardPanel extends JPanel{
	CardHolder peopleHolder;
	CardHolder roomHolder;
	CardHolder weaponHolder;
	
	public CardPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 1));

		// Make each card holder panel
		peopleHolder = new CardHolder("PEOPLE");
		roomHolder = new CardHolder("ROOMS");
		weaponHolder = new CardHolder("WEAPONS");
		
		// Add each card holder panel to main card panel
		mainPanel.add(peopleHolder);
		mainPanel.add(roomHolder);
		mainPanel.add(weaponHolder);
		setLayout(new GridLayout(1, 1));
		add(mainPanel, BorderLayout.CENTER);
	}
	
	public void update(Player p) {
		for (Card c : p.getSeen()) {
			addToSeen(c);
		}
		addToHand(p.getHand());
	}
	
	private void addToSeen(Card c) {
		switch (c.getCardType()) {
		case PERSON:
			peopleHolder.addToSeen(c);
			break;
		case WEAPON:
			weaponHolder.addToSeen(c);
			break;
		case ROOM:
			roomHolder.addToSeen(c);
			break;
		}
	}
	
	private void addToHand(ArrayList<Card> hand) {
		for (Card c : hand) {
			switch(c.getCardType()) {
			case PERSON:
				peopleHolder.addToHand(c);
				break;
			case WEAPON:
				weaponHolder.addToHand(c);
				break;
			case ROOM:
				roomHolder.addToHand(c);
				break;
			}
		}
	}
	
	class CardHolder extends JPanel {
		private JPanel hand;
		private JPanel seen;
		private static Color handColor;
		private static Color seenColor;
		
		public CardHolder(String title) {
			setLayout(new GridLayout(2, 1));
			setBorder(new TitledBorder(new EtchedBorder(), title, TitledBorder.CENTER, TitledBorder.CENTER));
			
			handColor = getRandomColor();	// Set hand cards to a random color			
			seenColor = getRandomColor();	// Set seen cards to a different color
			
			hand = new JPanel();
			hand.setLayout(new GridLayout(0, 1));
			hand.setBorder(new TitledBorder(new EtchedBorder(), " In Hand "));
			seen = new JPanel();
			seen.setLayout(new GridLayout(0, 1));
			seen.setBorder(new TitledBorder(new EtchedBorder(), " Seen "));
			
			add(hand);
			add(seen);
		}
		
		// Set the card category (seen or in hand) to a random color - do once for each
		// From C24 Class Prep Video 2
		private Color getRandomColor() {
			Random rand = new Random();
			int red = rand.nextInt(256);
			int blue = rand.nextInt(256);
			int green = rand.nextInt(256);
			
			// Get new color if first color is too dark for black font on top
			if (red + green + blue > 450) {
				return getRandomColor();
			} else {
				return new Color(red, green, blue);
			}
		}
		
		public void addToHand(Card c) {
			JTextField card = new JTextField(c.toString());
			card.setHorizontalAlignment(JTextField.CENTER);
			card.setFont(card.getFont().deriveFont(Font.ITALIC + Font.BOLD, 12f));
			card.setForeground(Color.white);	// Font color
			card.setEditable(false);
			card.setBackground(handColor);
			hand.add(card);
			updateUI();
		}
		
		public void addToSeen(Card c) {
			JTextField card = new JTextField(c.toString());
			card.setHorizontalAlignment(JTextField.CENTER);
			card.setFont(card.getFont().deriveFont(Font.ITALIC + Font.BOLD, 12f));
			card.setForeground(Color.white);	// Font color
			card.setEditable(false);
			card.setBackground(seenColor);
			seen.add(card);
			updateUI();
		}
	}
	
	public static void main(String[] args) {
		CardPanel panel = new CardPanel();
		JFrame frame = new JFrame(); 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		HumanPlayer p = new HumanPlayer("Sammy", Color.RED, 0,0);
		p.updateHand(new Card("Mister Flufferson", CardType.PERSON));
		p.updateHand(new Card("Bathroom", CardType.ROOM));
		p.updateHand(new Card("Ellie", CardType.PERSON));
		
		p.updateSeen(new Card("Tom", CardType.PERSON));
		p.updateSeen(new Card("Hammer", CardType.WEAPON));
		p.updateSeen(new Card("Grow Room", CardType.ROOM));
		
		panel.update(p);
	}
}
