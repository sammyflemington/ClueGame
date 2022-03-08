package tests;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import clueGame.Board;
import clueGame.DoorDirection;

public class FinalInitTest {
	
	private static int ROWS = 27;
	private static int COLUMNS = 22;
	
	private static Board board;
	
	@BeforeAll
	public static void initialize() {
		// load stuff
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testRoomCount() {
		// Correct # of rooms
		assertTrue(board.getRoomMap().size() == 9 + 2); // +2 for unused spaces
	}
	
	@Test
	public void testStartAndEnd() {
		// Start and end of file read correctly
		assertTrue(board.getCell(0, 0).getInitial() == 'C');
		assertTrue(board.getCell(26, 21).getInitial() == 'P');
	}
	
	@Test
	public void testMapSize() {
		// Correct number of rows and columns
		assertTrue(board.getNumRows() == ROWS);
		assertTrue(board.getNumColumns() == COLUMNS);
	}
	@Test
	public void testDoorways() {
		// doorways
		assertTrue(board.getCell(9, 3).getDoorDirection() == DoorDirection.DOWN);
		assertTrue(board.getCell(19, 3).getDoorDirection() == DoorDirection.LEFT);
		assertTrue(board.getCell(4, 7).getDoorDirection() == DoorDirection.UP);
		assertTrue(board.getCell(4, 17).getDoorDirection() == DoorDirection.RIGHT);
		
		// correct # of doors
		int numDoors = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				if (board.getCell(i,j).isDoorway()) {
					numDoors ++;
				}
			}
		}
		assertTrue(numDoors == 12);
		
		
		// non-door tiles are not doors
		assertTrue(board.getCell(26, 21).isDoorway() == false);
	}
	@Test
	public void testCells() {
		// check some cells for correct readings
		//board.printBoard();
		//System.out.println(board.getCell(2,14).getInitial());
		assertTrue(board.getCell(2,14).getInitial() == 'S');
		assertTrue(board.getCell(9,10).getInitial() == 'X');
		assertTrue(board.getCell(22,10).getInitial() == 'T');
		assertTrue(board.getCell(18,20).getInitial() == 'B');
		
		// Check room center and labels
		assertTrue(board.getCell(3,  20).isRoomCenter());
		assertTrue(board.getCell(2,  19).isLabel());
		assertTrue(board.getCell(23,  17).isRoomCenter());
		assertTrue(board.getCell(25,  10).isLabel());
	}
	
}
