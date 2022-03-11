package tests;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;
import experiment.TestBoardCell;

public class BoardAdjTargetTest {
	private static int ROWS = 27;
	private static int COLUMNS = 22;
	
	private static Board board;
	
	@BeforeAll
	public static void initialize() {
		// load stuff
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testWalkwaysToAdjacentWalkways() {
		//Walkways only connect to adjacent walkways.
		
		BoardCell cell = board.getCell(10, 14);	// walkway
		Set<BoardCell> testList = cell.getAdjList();

		assertTrue(testList.contains(board.getCell(9, 14)));	
		assertTrue(testList.contains(board.getCell(11, 14))); 
		assertTrue(testList.contains(board.getCell(10, 15))); 
		assertEquals(3, testList.size());
		
		cell = board.getCell(22, 14); 	// walkway next to doorway
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(22, 13)));	
		assertTrue(testList.contains(board.getCell(21, 14))); 
		assertTrue(testList.contains(board.getCell(23, 14))); 
		assertEquals(3, testList.size());
		
		
	}
	
	@Test
	public void testWalkwayDoorConnections() {
		//Walkways with doors will also connect to the room center the door points to.
		
		BoardCell cell = board.getCell(22,1);	// doorway
		Set<BoardCell> testList = cell.getAdjList();

		assertTrue(testList.contains(board.getCell(22, 0)));	
		assertTrue(testList.contains(board.getCell(21, 1))); 
		assertTrue(testList.contains(board.getCell(22, 2))); 
		assertTrue(testList.contains(board.getCell(25, 2))); 
		assertEquals(4, testList.size());
		
		cell = board.getCell(17, 19); 	// doorway between two rooms
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(17, 18)));	
		assertTrue(testList.contains(board.getCell(17, 20))); 
		assertTrue(testList.contains(board.getCell(19, 19))); 
		assertEquals(3, testList.size());
		
		cell = board.getCell(23, 14); 	// doorway
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(23, 13)));	
		assertTrue(testList.contains(board.getCell(22, 14))); 
		assertTrue(testList.contains(board.getCell(24, 14))); 
		assertTrue(testList.contains(board.getCell(23, 17))); 
		assertEquals(4, testList.size());
		
		
	}
	
	@Test
	public void testRoomCenters() {
		//The cell that represents the Room (i.e. connects to walkway) is the cell 
		// with a second character of ‘*’ (no other cells in a room should have adjacencies).
		
		BoardCell cell = board.getCell(3,1);	// inside room
		Set<BoardCell> testList = cell.getAdjList();
		assertEquals(0, testList.size());
		
	}
	
	@Test
	public void testRoomCentersConnections() {
		//Room center cells ONLY connect to 1) door walkways that enter the room and 2) another
		//room center cell if there is a secret passage connecting.
	
		BoardCell cell = board.getCell(14, 2);	// room center
		Set<BoardCell> testList = cell.getAdjList();

		assertTrue(testList.contains(board.getCell(9, 3)));	
		assertTrue(testList.contains(board.getCell(19, 3))); 
		assertTrue(testList.contains(board.getCell(23, 17))); 
		assertEquals(3, testList.size());
			
	}
	
	@Test
	public void testWalkwayTargets() {
		
		BoardCell cell = board.getCell(16, 14);
		board.calcTargets(cell, 1);
		
		Set<BoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(15, 14)));
		assertTrue(targets.contains(board.getCell(17, 14)));
		assertTrue(targets.contains(board.getCell(16, 15)));
		
	}
	
	@Test
	public void testFromRoomTargets() {
		
		BoardCell cell = board.getCell(13, 20);
		board.calcTargets(cell, 3);
		
		Set<BoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(11, 16)));
		assertTrue(targets.contains(board.getCell(12, 15)));
		assertTrue(targets.contains(board.getCell(13, 14)));
		assertTrue(targets.contains(board.getCell(14, 15)));
		assertTrue(targets.contains(board.getCell(15, 16)));
		assertTrue(targets.contains(board.getCell(23, 17)));
		assertTrue(targets.contains(board.getCell(25, 2)));
		assertEquals(7, targets.size());
		
	}
	
	@Test
	public void testToRoomTargets() {
		
		BoardCell cell = board.getCell(25, 6); // is a doorway
		board.getCell(24,  6).setOccupied(true);
		board.calcTargets(cell, 2);
		Set<BoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(24, 7)));
		assertTrue(targets.contains(board.getCell(26, 7)));
		assertTrue(targets.contains(board.getCell(25, 2)));
		assertTrue(targets.contains(board.getCell(13, 20)));
		assertEquals(4, targets.size());
		
	}
	
	
}
