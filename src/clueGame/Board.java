package clueGame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import experiment.TestBoardCell;

public class Board {
	private static Board theInstance = new Board();
	private TestBoardCell[][] board;
	private Set<TestBoardCell> targets = new HashSet<TestBoardCell>();
	final static int numColumns = 4;
	final static int numRows = 4;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	
	private Board() {
		super();
	}
	
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		board = new TestBoardCell[numRows][numColumns];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				board[i][j] = new TestBoardCell(i, j);
			}
		}
		
		// Calculate adjacencies
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (i > 0) 			board[i][j].addAdjacency(board[i-1][j]); // Left
				if (i < numRows - 1) 	board[i][j].addAdjacency(board[i+1][j]); // right
				if (j > 0)			board[i][j].addAdjacency(board[i][j-1]); // up
				if (j < numColumns - 1) 	board[i][j].addAdjacency(board[i][j+1]); // down
			}
		}
	}
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	
	public void setConfigFiles(String f1, String f2) {
		layoutConfigFile = f1;
		setupConfigFile = f2;
	}
	
	public void calcTargets(TestBoardCell startCell, int pathLength) {
		// start
		Set<TestBoardCell> visited = new HashSet<TestBoardCell>();
		visited.clear();
		visited.add(startCell);
		calculate(startCell, pathLength - 1, visited);
		
	}
	
	// Does a recursive search of the grid and avoids obstacles. 
	// TODO: No support yet for entering walkways!
	public void calculate(TestBoardCell startCell, int pathLength, Set<TestBoardCell> visited) {
		for (TestBoardCell c : startCell.getAdjList()) {
			if (!visited.contains(c) && !c.getOccupied() && !c.isRoom()) {
				if (pathLength == 0) {
					targets.add(c);
				} else {
					// Create copy of visited list for this branch
					Set<TestBoardCell> v = new HashSet<TestBoardCell>(visited);
					v.add(c);
					calculate(c, pathLength - 1, v);
				}
			}
		}
	}
	
	public Set<TestBoardCell> getTargets() {
		// DEBUG
		System.out.println();
		for (TestBoardCell t : targets)
			System.out.println(t);
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return board[row][col];
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public Room getRoom(char c) {
		return null;
	}
	
}
