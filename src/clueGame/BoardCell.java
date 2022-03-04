package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {

	private int 	col;
	private int 	row;
	private char 	initial;
	
	DoorDirection doorDirection;
	
	private boolean isDoorway = false;
	private boolean isOccupied = false;
	private boolean isRoomLabel = false;
	private boolean isLabel = false;
	private boolean isRoomCenter = false;
	private char 	secretPassage;
	
	private Set<BoardCell> adjList = new HashSet<BoardCell>();
	
	public BoardCell(int r, int c) {
		row = r;
		col = c;
	}
	
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	public void setRoom(boolean b) {
		isRoomLabel = b;
	}
	
	public boolean isLabel() {
		return isRoomLabel;
	}
	
	public boolean isRoomCenter() {
		return isRoomCenter;
	}
	
	public boolean isDoorway() {
		return isDoorway;
	}
	
	public void setOccupied(boolean b) {
		isOccupied = b;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}

	@Override
	public String toString() {
		return "[" + Integer.toString(row) + ", " + Integer.toString(col) + "]";
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}

}
