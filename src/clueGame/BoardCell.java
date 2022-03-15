
// Authors: Eliot Edwards and Sammy Flemington

package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

/* 
 * The BoardCell class allows us to handle
 * the necessary components of each individual cell.
 * This includes its doorway, room, label, room center,
 * initial, occupancy, and secret passage status.
 */
public class BoardCell {

	private int 	col;
	private int 	row;
	private char 	initial;
	
	DoorDirection doorDirection;
	
	private boolean isDoorway = false;
	private boolean isOccupied = false;
	private boolean isRoomLabel = false;
	private boolean isLabel = false;
	private boolean isRoom = false;
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
		isRoom = b;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
		
	public void setInitial(char c) {
		initial = c;
	}
	public void setRoomLabel(boolean b) {
		isRoomLabel = b;		
	}
	
	public boolean isLabel() {
		return isRoomLabel;
	}
	
	public void setRoomCenter(boolean b) {
		isRoomCenter = b;
	}
	
	public boolean isRoomCenter() {
		return isRoomCenter;
	}
	
	public void setDoorway(boolean b) {
		isDoorway = b;
	}
	
	public boolean isDoorway() {
		return isDoorway;
	}
	
	public void setDoorDirection(DoorDirection d) {
		doorDirection = d;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public void setOccupied(boolean b) {
		isOccupied = b;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
	public void setSecretPassage(char c) {
		secretPassage = c;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}
	
	public void setAdjList(Set<BoardCell> adjacencyList) {
		adjList = adjacencyList;
	}
	
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	public char getInitial() {
		return initial;
	}
	@Override
	public String toString() {
		return "[" + Integer.toString(row) + ", " + Integer.toString(col) + "]";
	}
		
}
