package clueGame;

import java.io.*;
import java.util.*;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

public class Board {
	private static Board theInstance = new Board();
	private BoardCell[][] board;
	private Set<BoardCell> targets;
	static int numColumns;
	static int numRows;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	Map<Character, BoardCell> roomLabels;
	Map<Character, BoardCell> roomCenters;
	Map<Character, Set<Character>> secretPassages;	// first char is the current room, second char set is room(s) it goes to
	ArrayList<Character> validChars;

	// only one instance created
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
		}catch(BadConfigFormatException e) {
			e.printStackTrace();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

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
			// Check format
			if (parts.length != 3) throw new BadConfigFormatException("Wrong number of elements in setup file line!");

			if (parts[0].equals("Room")) {
				char label = parts[2].charAt(0);
				Room room = new Room(roomCenters.get(label), roomLabels.get(label));
				room.setName(parts[1]);
				roomMap.put(parts[2].charAt(0), room);
				// Remove from valid list to make sure we don't have mismatch
				if (validChars.contains(parts[2].charAt(0)))
					validChars.remove(validChars.indexOf(parts[2].charAt(0)));
				//else throw new BadConfigFormatException();
			} else if (parts[0].equals("Space")) {
				char label = parts[2].charAt(0);
				BoardCell uselessCell = new BoardCell(0,0);
				Room room = new Room(uselessCell, uselessCell);
				room.setName(parts[1]);
				roomMap.put(parts[2].charAt(0), room);
				// Remove from valid list to make sure we don't have mismatch
				if (validChars.contains(parts[2].charAt(0)))
					validChars.remove(validChars.indexOf(parts[2].charAt(0)));
				//else throw new BadConfigFormatException();
			} else {
				throw new BadConfigFormatException(parts[0] + " is not a valid cell label!");
			}

		}
		reader.close();

		// Check format
		System.out.println(validChars);
		if (validChars.size() != 0) throw new BadConfigFormatException("Layout refers to a room not in setup!");
	}

	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
		roomLabels = new HashMap<Character, BoardCell>();
		roomCenters = new HashMap<Character, BoardCell>();
		
		secretPassages = new HashMap<Character, Set<Character>>();
		Set<Character> multiplePassages = new HashSet<Character>();
		
		validChars = new ArrayList<Character>();

		File file = new File(layoutConfigFile);
		Scanner reader = new Scanner(file);

		// First find the size of the level layout
		int i = 1, j = 0;
		String line = reader.nextLine();
		String[] chars = line.split(",");

		for (String c : chars) {
			j ++;
		}

		while (reader.hasNextLine()) {
			line = reader.nextLine();
			i++;
		}

		numRows = i;
		numColumns = j;
		board = new BoardCell[numRows][numColumns];

		i = j = 0;
		char sameChar = 'X';
		
		reader.close();
		reader = new Scanner(file);

		while(reader.hasNextLine()) {

			line = reader.nextLine();
			if (line.split(",").length != numColumns) throw new BadConfigFormatException("File does not have same number of entries in every row!");


			for (String s : line.split(",")) {
				BoardCell cell = new BoardCell(i, j);
				board[i][j] = cell;
				cell.setInitial(s.charAt(0));

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
					default:
						// secret passage
						if (!Character.isLetter(s.charAt(1))) throw new BadConfigFormatException();
						cell.setRoom(true);
						cell.setSecretPassage(s.charAt(1));
						
						// if the current room hasn't already had one secret passage
						if (s.charAt(0) != sameChar) {
							multiplePassages = new HashSet<Character>();
							multiplePassages.add(s.charAt(1));
							sameChar = s.charAt(0);
						} else {
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

	}

	public void setConfigFiles(String f1, String f2) {
		layoutConfigFile = "data/" + f1;
		setupConfigFile = "data/" + f2;
	}

	public void calcTargets(BoardCell startCell, int pathLength) {
		// start
		Set<BoardCell> visited = new HashSet<BoardCell>();
		visited.clear();
		visited.add(startCell);
		calculate(startCell, pathLength - 1, visited);

	}

	// Does a recursive search of the grid and avoids obstacles. 
	// TODO: No support yet for entering walkways!
	public void calculate(BoardCell startCell, int pathLength, Set<BoardCell> visited) {
		for (BoardCell c : startCell.getAdjList()) {
			if (!visited.contains(c) && !c.getOccupied() && !c.isRoom()) {
				if (pathLength == 0) {
					targets.add(c);
				} else {
					// Create copy of visited list for this branch
					Set<BoardCell> v = new HashSet<BoardCell>(visited);
					v.add(c);
					calculate(c, pathLength - 1, v);
				}
			}
		}
	}

	// Adjacency Calculations
	public void calcAdjacencies() {
		
		// go through each cell in the board
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				
				// if it's a room but not a room center
				if (board[i][j].isRoom() && !board[i][j].isRoomCenter()) {
					
					continue; // no adjacencies
					
				} else if (board[i][j].isRoomCenter()) {
				
					if (secretPassages.containsKey(board[i][j].getInitial())) { // if there exists secret passage in room
				
						// what I put here confuses me because it passes a set of characters as the key for room Centers
						board[i][j].addAdjacency(roomCenters.get(secretPassages.get(board[i][j].getInitial()))); //// HMMMMM
						
						// TODO: adjacencies out doorway(s)
						
					} else { // no secret passage in room
				
						// TODO: adjacencies out doorway(s)
						
					}
				
				} else if (i == 0) { // if in the top row
				
					// if in first column = no adj left or up
					if (j == 0) {	
					
						if (board[i][j].isDoorway()) {	// if the current cell is a doorway
							
							// TODO: 	Check which room doorway is for
							//			Add that room center to adj list
							
						}
						
						if (!board[i][j + 1].isRoom()) {	// if cell to the right is not a room cell
							board[i][j].addAdjacency(board[i][j + 1]);
						}
						
						if (!board[i + 1][j].isRoom()) {	// if cell below is not a room cell
							board[i][j].addAdjacency(board[i + 1][j]);							
						} 				
						
					// if in last column = no adj right or up
					} else if (j == numColumns - 1) { 
					
						if (board[i][j].isDoorway()) {	// if the current cell is a doorway
							
							// TODO: 	Check which room doorway is for
							//			Add that room center to adj list
							
						}
						
						if (!board[i][j - 1].isRoom()) {	// if cell to the left is not a room cell
							board[i][j].addAdjacency(board[i][j - 1]);
						}
						
						if (!board[i + 1][j].isRoom()) {	// if cell below is not a room cell
							board[i][j].addAdjacency(board[i + 1][j]);							
						} 							
				
					// else not in first or last column = no adj up
					} else {
						
						if (board[i][j].isDoorway()) {	// if the current cell is a doorway
							
							// TODO: 	Check which room doorway is for
							//			Add that room center to adj list
							
						}
						
						if (!board[i][j - 1].isRoom()) {	// if cell to the left is not a room cell
							board[i][j].addAdjacency(board[i][j - 1]);
						}
						
						if (!board[i][j + 1].isRoom()) {	// if cell to the right is not a room cell
							board[i][j].addAdjacency(board[i][j + 1]);
						}
						
						if (!board[i + 1][j].isRoom()) {	// if cell below is not a room cell
							board[i][j].addAdjacency(board[i + 1][j]);							
						} 		
												
					}
					
				} else if (i == numRows - 1) { // if in the bottom row 
					
					// if in first column = no adj left or down
					if (j == 0) {	
					
						if (board[i][j].isDoorway()) {	// if the current cell is a doorway
							
							// TODO: 	Check which room doorway is for
							//			Add that room center to adj list
							
						}
						
						if (!board[i][j + 1].isRoom()) {	// if cell to the right is not a room cell
							board[i][j].addAdjacency(board[i][j + 1]);
						}
						
						if (!board[i - 1][j].isRoom()) {	// if cell above is not a room cell
							board[i][j].addAdjacency(board[i - 1][j]);							
						} 				
						
					// if in last column = no adj right or down
					} else if (j == numColumns - 1) { 
					
						if (board[i][j].isDoorway()) {	// if the current cell is a doorway
							
							// TODO: 	Check which room doorway is for
							//			Add that room center to adj list
							
						}
						
						if (!board[i][j - 1].isRoom()) {	// if cell to the left is not a room cell
							board[i][j].addAdjacency(board[i][j - 1]);
						}
						
						if (!board[i - 1][j].isRoom()) {	// if cell above is not a room cell
							board[i][j].addAdjacency(board[i - 1][j]);							
						} 							
				
					
					} else { // not in first or last column = no adj down
						
						if (board[i][j].isDoorway()) {	// if the current cell is a doorway
							
							// TODO: 	Check which room doorway is for
							//			Add that room center to adj list
							
						}
						
						if (!board[i][j - 1].isRoom()) {	// if cell to the left is not a room cell
							board[i][j].addAdjacency(board[i][j - 1]);
						}
						
						if (!board[i][j + 1].isRoom()) {	// if cell to the right is not a room cell
							board[i][j].addAdjacency(board[i][j + 1]);
						}
						
						if (!board[i - 1][j].isRoom()) {	// if cell above is not a room cell
							board[i][j].addAdjacency(board[i - 1][j]);							
						} 		
												
					}
				
				} else { // not top or bottom row)

					// if in first column = no adj left
					if (j == 0) {	
					
						if (board[i][j].isDoorway()) {	// if the current cell is a doorway
							
							// TODO: 	Check which room doorway is for
							//			Add that room center to adj list
							
						}
						
						if (!board[i][j + 1].isRoom()) {	// if cell to the right is not a room cell
							board[i][j].addAdjacency(board[i][j + 1]);
						}
						
						if (!board[i - 1][j].isRoom()) {	// if cell above is not a room cell
							board[i][j].addAdjacency(board[i - 1][j]);							
						} 
						
						if (!board[i + 1][j].isRoom()) {	// if cell below is not a room cell
							board[i][j].addAdjacency(board[i + 1][j]);							
						} 	
						
					// if in last column = no adj right
					} else if (j == numColumns - 1) { 
					
						if (board[i][j].isDoorway()) {	// if the current cell is a doorway
							
							// TODO: 	Check which room doorway is for
							//			Add that room center to adj list
							
						}
						
						if (!board[i][j - 1].isRoom()) {	// if cell to the left is not a room cell
							board[i][j].addAdjacency(board[i][j - 1]);
						}
						
						if (!board[i - 1][j].isRoom()) {	// if cell above is not a room cell
							board[i][j].addAdjacency(board[i - 1][j]);							
						} 					
						
						if (!board[i + 1][j].isRoom()) {	// if cell below is not a room cell
							board[i][j].addAdjacency(board[i + 1][j]);							
						} 	
				
					} else {
						
						if (board[i][j].isDoorway()) {	// if the current cell is a doorway
							
							// TODO: 	Check which room doorway is for
							//			Add that room center to adj list
							
						}
						
						if (!board[i][j - 1].isRoom()) {	// if cell to the left is not a room cell
							board[i][j].addAdjacency(board[i][j - 1]);
						}
						
						if (!board[i][j + 1].isRoom()) {	// if cell to the right is not a room cell
							board[i][j].addAdjacency(board[i][j + 1]);
						}
						
						if (!board[i - 1][j].isRoom()) {	// if cell above is not a room cell
							board[i][j].addAdjacency(board[i - 1][j]);							
						} 	
						
						if (!board[i + 1][j].isRoom()) {	// if cell below is not a room cell
							board[i][j].addAdjacency(board[i + 1][j]);							
						} 	
												
					}
		
				}
				
	
			}
			
		}

	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell(int row, int col) {
		return board[row][col];
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

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public Room getRoom(BoardCell c) {
		return roomMap.get(c.getInitial());
	}

}
