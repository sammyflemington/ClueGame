/**Authors:
 * @Author Eliot Edwards
 * @Author Sammy Flemington
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;

import javax.swing.JPanel;

import java.io.File;

import clueGame.BoardCell;
import gui.GameControlPanel;

/*
 * Board class:
 * 
 *	- Stores layout of the board, handles calculating player moves and loading level data.
 *	- Is a singleton
 */
public class Board extends JPanel {
	private static Board theInstance = new Board();
	private BoardCell[][] board;
	private Set<BoardCell> targets;
	private GameControlPanel panel;
	
	static int numColumns = 1;
	static int numRows = 1;
	
	private String layoutConfigFile;
	private String setupConfigFile;
	
	private Map<Character, Room> roomMap;
	private Map<Character, BoardCell> roomLabels;
	private Map<Character, BoardCell> roomCenters;
	private Map<Character, Set<Character>> secretPassages;	// first char is the current room, second char set is room(s) it goes to
	private ArrayList<Character> validChars;

	private ArrayList<Card> fullDeck;
	private ArrayList<Room> rooms;
	private ArrayList<Player> players;
	private ArrayList<Point> points;
	private ArrayList<String> weapons;

	private Solution solution;	// current game solution

	private Player currentPlayer;
	private int turn = 0;
	private int roll = 0;
	private boolean playing = true;

	// So only one Board instance is created
	private Board() {
		super();
	}

	public static Board getInstance() {
		return theInstance;
	}

	public void initialize() {
		// Allocate Memory
		board = new BoardCell[numRows][numColumns];
		targets = new HashSet<BoardCell>();

		try {
			loadLayoutConfig();
			loadSetupConfig();
		} catch(BadConfigFormatException e) {
			e.printStackTrace();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPanel(GameControlPanel panel) {
		this.panel = panel;
	}
	
	// Next button events for human player
	public void humanTurn(BoardCell target) {
		
		calcTargets(board[currentPlayer.row][currentPlayer.column], roll);
		
		currentPlayer.moveTo(target.getRow(), target.getCol());

		
		if (target.isRoom()) {
			// TODO: Can make suggestion
		}
		
		currentPlayer.setTurnOver(true);			// Set turn over

	}

	public void displayTargets(Graphics g, int width, int height) {
		g.setColor(getHumanPlayer().getColor()); // Set color to human player's color

		// Tell target cells to draw themselves
		for (BoardCell cell : targets) {
			board[cell.getRow()][cell.getCol()].drawTarget(g, width, height);
		}
	}
	
	// Checks if the human player clicked on a valid target cell
	public BoardCell checkTargetClicked(int x, int y) {

		// Check each target rectangle for the coordinates (x, y)
		for (BoardCell target : targets) {
			if (target.contains(x,  y)) {
				return target;
			}
		}

		return null;

	}

	// Next button events for computer player
	public void computerTurn() {

		BoardCell target = currentPlayer.selectTarget(roll);
		currentPlayer.moveTo(target.getRow(), target.getCol()); 		

		currentPlayer.setTurnOver(true);			// Set turn over
		nextTurn();									// Next players turn

	}

	public void nextPlayer() {
		
		currentPlayer = players.get(turn);			// Update current player
		currentPlayer.setTurnOver(false);			// Set turn not over
		setRoll();
		int roll = getRoll();
		calcTargets(board[currentPlayer.row][currentPlayer.column], roll);
		
		panel.setPlayerName(currentPlayer.getName());
		
		
		if (currentPlayer instanceof HumanPlayer) {
			// TODO: Human player's turn 
		} else {
			computerTurn();
		}

	}

	// Returns a random number from 1-6
	public void setRoll() {
		Random rand = new Random();
		this.roll = rand.nextInt(6) + 1;
	}
	
	public int getRoll() {
		return roll;
	}

	// Goes to next turn
	public void nextTurn() {
		turn++;
		turn = turn % players.size();
	}

	// Draws the board
	public void paintComponent(Graphics g) {
		// Must call super method to do the preliminary stuffs
		super.paintComponent(g);

		// Recalculate cell size in case the window gets resized
		int cellWidth = Math.round(getWidth() / numColumns);
		int cellHeight = Math.round(getHeight() / numRows);

		// Have board cells draw themselves
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				board[i][j].draw(g, cellWidth, cellHeight);
			}
		}

		// Tell rooms to draw their labels
		for (Room r : rooms) {
			r.drawLabel(g, cellWidth, cellHeight);
		}

		// Tell players to draw themselves
		for (Player p : players) {
			p.draw(g, cellWidth, cellHeight);
		}
		
		displayTargets(g, cellWidth, cellHeight);

		repaint();
	}

	// Loads setup file with rooms, weapons, and players
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		roomMap = new HashMap<Character, Room>();
		File file = new File(setupConfigFile);
		Scanner reader = new Scanner(file);

		while (reader.hasNextLine()) {
			String line = reader.nextLine(); 
			// Skip comments
			if (line.charAt(0) == '/' && line.charAt(1) == '/') 
				continue;

			String[] parts = line.split(", ");

			// Check card type
			if (parts[0].equalsIgnoreCase("Room")) {
				// Create room and store in map
				char label = parts[2].charAt(0);
				Room room = new Room(roomCenters.get(label), roomLabels.get(label));
				room.setName(parts[1]);
				roomMap.put(parts[2].charAt(0), room);

				rooms.add(room);// Add to rooms array for easy card initialization

				// Remove from valid list to make sure we don't have mismatch
				if (validChars.contains(parts[2].charAt(0)))
					validChars.remove(validChars.indexOf(parts[2].charAt(0)));
			} else if (parts[0].equalsIgnoreCase("Space")) {
				// Create a garbage "room" for use with empty space
				BoardCell uselessCell = new BoardCell(0,0);
				Room room = new Room(uselessCell, uselessCell);
				room.setName(parts[1]);
				roomMap.put(parts[2].charAt(0), room);

				// Remove from valid list to make sure we don't have mismatch
				if (validChars.contains(parts[2].charAt(0)))
					validChars.remove(validChars.indexOf(parts[2].charAt(0)));

			} else if (parts[0].equalsIgnoreCase("Weapon")) {

				if (parts.length == 2) {
					weapons.add(parts[1]);	// Add to weapons array for easy card initialization
				} else {
					throw new BadConfigFormatException ("Weapons not formatted correctly");
				}

			} else if (parts[0].equalsIgnoreCase("HumanPlayer")) {

				if (parts.length == 5) {	// Make sure we have all 5 player components
					HumanPlayer newPlayer = new HumanPlayer(parts[1], stringToColor(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
					getCell(Integer.parseInt(parts[3]), Integer.parseInt(parts[4])).setOccupied(true);
					newPlayer.setBoard(this);
					players.add(newPlayer);	// Add to players array for easy card initialization
					currentPlayer = newPlayer;
				} else {
					throw new BadConfigFormatException ("Human player not formatted correctly");
				}				

			} else if (parts[0].equalsIgnoreCase("ComputerPlayer")) {

				if (parts.length == 5) {	// Make sure we have all 5 player components
					ComputerPlayer newPlayer = new ComputerPlayer(parts[1], stringToColor(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
					getCell(Integer.parseInt(parts[3]), Integer.parseInt(parts[4])).setOccupied(true);
					newPlayer.setBoard(this);
					players.add(newPlayer);	// Add to players array for easy card initialization
				} else {
					throw new BadConfigFormatException ("Computer players not formatted correctly");
				}

			} else {
				throw new BadConfigFormatException(parts[0] + " is not a valid setup input!");
			}

		}
		reader.close();

		// Finally, check format
		if (validChars.size() != 0) throw new BadConfigFormatException("Layout refers to a room not in setup!");

		createDeck();	// If no errors, initialize deck

	}

	private Color stringToColor(String s) {
		switch (s){
		case "Blue": return Color.blue;	
		case "Red": return Color.red; 
		case "Black": return Color.black; 
		case "Green": return Color.green; 
		case "Pink": return Color.pink; 
		case "Orange": return Color.orange; 
		default: return Color.white;
		}
	}

	// Loads layout file with room layout.
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
		// Allocate memory
		roomLabels = new HashMap<Character, BoardCell>();
		roomCenters = new HashMap<Character, BoardCell>();
		fullDeck = new ArrayList<Card>();	// All game cards
		rooms = new ArrayList<Room>();		// Rooms
		players = new ArrayList<Player>();// Players
		weapons = new ArrayList<String>();// Weapons
		secretPassages = new HashMap<Character, Set<Character>>();
		Set<Character> multiplePassages = new HashSet<Character>();

		validChars = new ArrayList<Character>();

		File file = new File(layoutConfigFile);
		Scanner reader = new Scanner(file);

		// First find the size of the level layout
		int i = 1, j = 0;
		String line = reader.nextLine();

		j = line.split(",").length;
		while (reader.hasNextLine()) {
			reader.nextLine();
			i++;
		}
		numRows = i;
		numColumns = j;

		// Now load the level data
		board = new BoardCell[numRows][numColumns];
		i = j = 0;

		reader.close();
		reader = new Scanner(file);

		while(reader.hasNextLine()) {

			line = reader.nextLine();
			if (line.split(",").length != numColumns) {
				reader.close();
				throw new BadConfigFormatException("File does not have same number of entries in every row!");
			}


			for (String s : line.split(",")) {
				BoardCell cell = new BoardCell(i, j);
				board[i][j] = cell;
				// First letter is always the cell initial
				cell.setInitial(s.charAt(0));

				// If length > 1, we know that this is a special cell and must handle the second character
				if (s.length() > 1) {
					switch(s.charAt(1)) {
					case '#':
						roomLabels.put(cell.getInitial(), cell);
						cell.setRoomLabel(true);
						cell.setRoom(true);
						break;
					case '*':
						roomCenters.put(cell.getInitial(), cell);
						cell.setRoomCenter(true);
						cell.setRoom(true);
						break;
					case 'v':
						cell.setDoorDirection(DoorDirection.DOWN);
						cell.setDoorway(true);
						break;
					case '<':
						cell.setDoorDirection(DoorDirection.LEFT);
						cell.setDoorway(true);
						break;
					case '>':
						cell.setDoorDirection(DoorDirection.RIGHT);
						cell.setDoorway(true);
						break;
					case '^':
						cell.setDoorDirection(DoorDirection.UP);
						cell.setDoorway(true);
						break;
					default: // must be secret passage
						if (!Character.isLetter(s.charAt(1))) throw new BadConfigFormatException();
						cell.setRoom(true);
						cell.setSecretPassage(s.charAt(1));

						// if the current room hasn't already had one secret passage
						if (secretPassages.containsKey(s.charAt(0))) {
							secretPassages.get(s.charAt(0)).add(s.charAt(1));
						} else {
							multiplePassages = new HashSet<Character>();
							multiplePassages.add(s.charAt(1));
							secretPassages.put(s.charAt(0), multiplePassages);
						}
						break;
					}

					// if the initial is one character, and isn't a walkway, it's a room cell
					// Xs ARE TECHNICALLY ROOM CELLS
				} else if (s.charAt(0) != 'W') {
					cell.setRoom(true);
				}

				// Add to valid character list for format checking
				if (!validChars.contains(s.charAt(0)))
					validChars.add(s.charAt(0));
				j++;
			}
			i++;
			j = 0;
		}
		reader.close();
		calcAdjacencies();
	}

	public void setConfigFiles(String f1, String f2) {
		layoutConfigFile = "data/" + f1;
		setupConfigFile = "data/" + f2;
	}

	// Initializes the game deck using the clue setup inputs from loadSetupConfig
	public void createDeck() {

		// Room cards
		for (Room room : rooms) {
			Card newCard = new Card(room.getName(), CardType.ROOM);
			fullDeck.add(newCard);
		}

		// Weapon cards
		for (String weapon : weapons) {
			Card newCard = new Card(weapon, CardType.WEAPON);
			fullDeck.add(newCard);
		}

		// Player cards
		for (Player player : players) {
			Card newCard = new Card(player.getName(), CardType.PERSON);
			fullDeck.add(newCard);
		}

		dealCards();

	}

	// Choose solution cards randomly, and then deal the rest of the cards to the players
	public void dealCards() {

		Collections.shuffle(fullDeck);		// Shuffle deck
		ArrayList<Card> cards = new ArrayList<Card>(fullDeck);	// Make copy of deck

		Card roomSolution = null, personSolution = null, weaponSolution = null;

		// Randomly choose 1 room, 1 person, and 1 weapon, and put them in solution
		for (Card card : fullDeck) {

			if (roomSolution == null && card.getCardType() == CardType.ROOM) {
				roomSolution = card;
				cards.remove(card);
			} else if (personSolution == null && card.getCardType() == CardType.PERSON) {
				personSolution = card;
				cards.remove(card);
			} else if (weaponSolution == null && card.getCardType() == CardType.WEAPON) {
				weaponSolution = card;
				cards.remove(card);
			}

		}

		// Assign solution
		solution = new Solution(roomSolution, personSolution, weaponSolution);

		// Deal the rest of the cards to players 1-6
		int i = 0;
		while (!cards.isEmpty()) {
			players.get(i++ % 6).updateHand(cards.remove(0));
		}
	}

	// Calculate valid moves from a given cell and given roll, store in "targets" set
	public void calcTargets(BoardCell startCell, int pathLength) {
		// start
		Set<BoardCell> visited = new HashSet<BoardCell>();
		targets.clear();
		visited.clear();
		visited.add(startCell);
		calculate(startCell, pathLength - 1, visited);

	}

	// Does a recursive search of the grid and avoids obstacles. 
	public void calculate(BoardCell startCell, int pathLength, Set<BoardCell> visited) {
		for (BoardCell c : startCell.getAdjList()) {
			if (!visited.contains(c) && ! c.getOccupied()) {
				if (pathLength == 0 || c.isRoomCenter()) {
					targets.add(c);
				} else {
					// Create copy of visited list for this branch
					Set<BoardCell> v = new HashSet<BoardCell>(visited);
					v.add(c);
					calculate(c, pathLength - 1, v);
				}
			}
		}

		startCell.setTargets(targets);	// set targets for that cell

	}

	// Adjacency Calculations
	public void calcAdjacencies() throws BadConfigFormatException{

		// go through each cell in the board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {

				// if it's a room but not a room center
				if (board[i][j].isRoom() && !board[i][j].isRoomCenter()) {
					continue; // no adjacencies
				} else if (board[i][j].isRoomCenter()) {
					if (secretPassages.containsKey(board[i][j].getInitial())) { // if there exists secret passage in room
						for (Character c : secretPassages.get(board[i][j].getInitial())) {
							board[i][j].addAdjacency(roomCenters.get(c));
						}
					}

				} else {
					char ch;
					if (board[i][j].isDoorway()) {	// if the current cell is a doorway

						// Check which room doorway is for add that room center to adj list
						try {
							switch (board[i][j].getDoorDirection()) {
							case UP:
								ch = board[i - 1][j].getInitial();
								break;
							case DOWN:
								ch = board[i + 1][j].getInitial();
								break;
							case LEFT:
								ch = board[i][j - 1].getInitial();
								break;
							case RIGHT:
								ch = board[i][j + 1].getInitial();
								break;
							default:
								throw new BadConfigFormatException("Something went wrong during door initialization");
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							throw new BadConfigFormatException("A door is placed illegally!");
						}
						board[i][j].addAdjacency(roomCenters.get(ch));// Add adjacency both ways
						roomCenters.get(ch).addAdjacency(board[i][j]);
					}

					// Handle edge cases for adjacencies
					if (i > 0)
						if (!board[i - 1][j].isRoom())
							board[i][j].addAdjacency(board[i - 1][j]);

					if (j > 0)
						if (!board[i][j - 1].isRoom())
							board[i][j].addAdjacency(board[i][j - 1]);

					if (i < numRows - 1)
						if (!board[i + 1][j].isRoom())
							board[i][j].addAdjacency(board[i + 1][j]);

					if (j < numColumns - 1)
						if (!board[i][j + 1].isRoom())
							board[i][j].addAdjacency(board[i][j + 1]);

				}
			}	
		}
	}

	// Check if the player's accusation was correct or not
	// If true: that player wins the game and the game is over
	// If false: player is no longer playing the game
	public boolean checkAccusation(Solution acc) {
		if (acc.equals(solution)) {
			return true;
		}else {
			return false;
		}
	}

	// Check if the player's suggestion was correct or not
	public Card handleSuggestion(Solution suggestion) {
		// go through all players and see if they can disprove
		for (int i = 1; i < players.size(); i++) {
			int playerNum = (i + getTurn()) % players.size(); // loop through players starting with whose turn it is
			Player p = players.get(playerNum);
			Card c = p.disproveSuggestion(suggestion);
			if (c != null) {
				// this player can disprove the suggestion
				return c;
			}
		}
		// Nobody could disprove the suggestion!
		return null;
	}


	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell(int row, int col) {
		return board[row][col];
	}

	public ArrayList<Card> getDeck() {
		return fullDeck;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Map<Character, Room> getRoomMap(){
		return roomMap;
	}

	public void printBoard() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				System.out.print(board[i][j].getInitial());
			}
			System.out.print("\r\n");
		}
	}

	public int getTurn() {
		return turn;
	}
	
	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public Room getRoom(BoardCell c) {
		return roomMap.get(c.getInitial());
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public HumanPlayer getHumanPlayer() {
		for (Player p : players) {
			if (p instanceof HumanPlayer) {
				return (HumanPlayer) p;
			}
		}
		return null;
	}

	public Solution getSolution() {
		return solution;
	}

	public String getRoomLabel(char c){
		return roomMap.get(c).getName();
	}

}
