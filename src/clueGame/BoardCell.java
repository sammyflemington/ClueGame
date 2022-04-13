
// Authors: Eliot Edwards and Sammy Flemington

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import experiment.TestBoardCell;
import javax.swing.JFrame;
/* 
 * The BoardCell class allows us to handle
 * the necessary components of each individual cell.
 * This includes its doorway, room, label, room center,
 * initial, occupancy, and secret passage status.
 */
public class BoardCell{

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
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	
	public BoardCell(int r, int c) {
		row = r;
		col = c;
	}
	
	public void draw(Graphics g, int w, int h) {
		// Determine position based on row and col and cell size
		int x = w * col;
		int y = h * row;
		if (initial == 'X') {
			g.setColor(Color.BLACK);
			g.fillRect(x, y, w, h);
		}else if (isDoorway) { 
			// draw walkway tile behind door
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, w, h);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, w, h);
			
			g.setColor(Color.BLUE);
			int marginW = (3 * w) / 4;
			int marginH = (3*h)/4;
			
			switch(doorDirection) {
				case UP:
					g.fillRect(x, y, w, h-marginH);
					break;
				case DOWN:
					g.fillRect(x, y+marginH, w, h-marginH);
					break;
				case LEFT:
					g.fillRect(x, y, w-marginW, h);
					break;
				case RIGHT:
					g.fillRect(x+marginW, y, w-marginW, h);
					break;
				default:
					//g.fillRect(x, y, w, h);
					break;
					
				}
			//g.fillRect(x, y, w, h);
		}else if (isRoom) {
			g.setColor(Color.GRAY);
			g.fillRect(x, y, w, h);

		}else if (initial == 'W'){
			g.setColor(Color.YELLOW);
			g.fillRect(x, y, w, h);
			g.setColor(Color.BLACK);
			g.drawRect(x, y, w, h);
		}
		
		
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
	
	public void setTargets(Set<BoardCell> targetsList) {
		targets = targetsList;
	}
	
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public char getInitial() {
		return initial;
	}
	@Override
	public String toString() {
		return "[" + Integer.toString(row) + ", " + Integer.toString(col) + "]";
	}
		
}
