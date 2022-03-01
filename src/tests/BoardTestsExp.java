package tests;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {

	TestBoard board;
	
	@BeforeEach // So it runs before each test
	public void setUp() {
		board = new TestBoard();		
	}
	
	@Test
	public void testAdjacency() {
		
		TestBoardCell cell = board.get(1,1);
		Set<TestBoardCell> testList = cell.getAdjList();
		
		// Middle
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertEquals(4, testList.size());
		
		// Corner
		cell = board.get(0,0);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertEquals(2, testList.size());
		
		// Edge
		cell = board.get(9,21);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(10,21)));
		assertTrue(testList.contains(board.getCell(8,21)));
		assertTrue(testList.contains(board.getCell(9,20)));
		assertEquals(3, testList.size());
		
		
	}
	
}
