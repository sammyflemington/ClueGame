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
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	static int numColumns = 4;
	static int numRows = 4;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	Map<Character, BoardCell> roomLabels = new HashMap<Character, BoardCell>();
	Map<Character, BoardCell> roomCenters = new HashMap<Character, BoardCell>();
	
	// only one instance created
	private Board() {
		super();
	}
	
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		board = new BoardCell[numRows][numColumns];
		try {
			loadLayoutConfig();
			loadSetupConfig();
		}catch(BadConfigFormatException e) {
			e.printStackTrace();
			// uh oh 
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		File file = new File(setupConfigFile);
		Scanner reader = new Scanner(file);
		
		while (reader.hasNextLine()) {
			String line = reader.nextLine(); 
			
			if (line.charAt(0) == '/' && line.charAt(1) == '/') 
				continue;
			
			String[] parts = line.split(", ");
			//System.out.println(parts[0]);
			// check if its a room or space
			if (parts[0].equals("Room")) {
				char label = parts[2].charAt(0);
				Room room = new Room(roomCenters.get(label), roomLabels.get(label));
				room.setName(parts[1]);
				roomMap.put(parts[2].charAt(0), room);
			} else if (parts[0].equals("Space")) {
				char label = parts[2].charAt(0);
				BoardCell uselessCell = new BoardCell(0,0);
				Room room = new Room(uselessCell, uselessCell);
				room.setName(parts[1]);
				roomMap.put(parts[2].charAt(0), room);
			}
			
		}
		reader.close();
		
		
	}
	
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
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
		reader.close();
		reader = new Scanner(file);
		while(reader.hasNextLine()) {
			line = reader.nextLine();
			
			for (String s : line.split(",")) {
				BoardCell cell = new BoardCell(i, j);
				board[i][j] = cell;
				cell.setInitial(s.charAt(0));
				
				if (s.length() > 1) {
					switch(s.charAt(1)) {
					case '#':
						roomLabels.put(cell.getInitial(), cell);
						cell.setRoomLabel(true);
						break;
					case '*':
						roomCenters.put(cell.getInitial(), cell);
						cell.setRoomCenter(true);
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
						cell.setSecretPassage(s.charAt(1));
						break;
					}
				}
				
				j++;
			}
			//System.out.println();
			i++;
			j = 0;
		}
		reader.close();
		
		// Calculate adjacencies
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++) {
				if (i > 0) 				board[i][j].addAdjacency(board[i-1][j]); // Left
				if (i < numRows - 1) 	board[i][j].addAdjacency(board[i+1][j]); // right
				if (j > 0)				board[i][j].addAdjacency(board[i][j-1]); // up
				if (j < numColumns - 1) board[i][j].addAdjacency(board[i][j+1]); // down
			}
		}
	}
	
	public void setConfigFiles(String f1, String f2) {
		layoutConfigFile = f1;
		setupConfigFile = f2;
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
