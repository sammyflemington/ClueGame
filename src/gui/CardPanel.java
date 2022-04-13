package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;

public class CardPanel extends JPanel{
	CardHolder peopleHolder;
	CardHolder roomHolder;
	CardHolder weaponHolder;
	public CardPanel() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 1));
		
		peopleHolder = new CardHolder("People");
		
		roomHolder = new CardHolder("Rooms");
		
		weaponHolder = new CardHolder("Weapons");
		mainPanel.add(peopleHolder);
		mainPanel.add(roomHolder);
		mainPanel.add(weaponHolder);
		setLayout(new GridLayout(1,1));
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
	
	class CardHolder extends JPanel{
		private JPanel hand;
		private JPanel seen;
		public CardHolder(String title) {
			setLayout(new GridLayout(2, 1));
			setBorder(new TitledBorder(new EtchedBorder(), title));
			hand = new JPanel();
			hand.setLayout(new GridLayout(0, 1));
			hand.setBorder(new TitledBorder(new EtchedBorder(), "In Hand"));
			seen = new JPanel();
			seen.setLayout(new GridLayout(0, 1));
			seen.setBorder(new TitledBorder(new EtchedBorder(), "Seen"));
			
			add(hand);
			add(seen);
		}
		
		public void addToHand(Card c) {
			JTextField card = new JTextField(c.toString());
			card.setEditable(false);
			hand.add(card);
			updateUI();
		}
		
		public void addToSeen(Card c) {
			JTextField card = new JTextField(c.toString());
			card.setEditable(false);
			seen.add(card);
			updateUI();
		}
	}
	
	public static void main(String[] args) {
		CardPanel panel = new CardPanel();
		JFrame frame = new JFrame(); 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
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
