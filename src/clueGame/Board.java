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
	final static int numColumns = 4;
	final static int numRows = 4;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	
	// only one instance created
	private Board() {
		super();
	}
	
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		try {
			loadSetupConfig();
			loadLayoutConfig();
		}catch(BadConfigFormatException e) {
			e.printStackTrace();
			// uh oh 
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		File setup = new File(setupConfigFile);
	}
	
	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
		File layoutFile = new File(layoutConfigFile);
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
		// DEBUG
		System.out.println();
		for (BoardCell t : targets)
			System.out.println(t);
		return targets;
	}
	
	public BoardCell getCell(int row, int col) {
		return new BoardCell(0,0);
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
	public Room getRoom(char c) {
		return new Room(new BoardCell(0,0),new BoardCell(0,0));
	}
	
	public Room getRoom(BoardCell c) {
		return new Room(new BoardCell(0,0),new BoardCell(0,0));
	}
	
}
