package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {

	private int col;
	private int row;
	private char initial;
	
	DoorDirection doorDirection;
	
	private boolean isOccupied;
	private boolean isRoomLabel;
	private boolean isRoomCenter;
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
	
	public boolean isRoom() {
		return isRoomLabel;
	}
	
	public void setOccupied(boolean b) {
		isOccupied = b;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}

	@Override
	public String toString() {
		return "[" + Integer.toString(row) + ", " + Integer.toString(col) + "]";
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}

}
