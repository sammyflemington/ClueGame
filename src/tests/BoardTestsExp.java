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
	
	@Test
	public void testTargetsMixed_1() {
		TestBoardCell cell = board.getCell(18, 10);
		board.calcTargets(cell, 2);
		
		Set<TestBoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(18, 8)));
		assertTrue(targets.contains(board.getCell(19, 9)));
		assertTrue(targets.contains(board.getCell(19, 11)));
		assertTrue(targets.contains(board.getCell(18, 12)));
		assertTrue(targets.contains(board.getCell(23, 10)));
		assertEquals(5, targets.size());
		
	}
	
	@Test
	public void testTargetsMixed_2() {
		TestBoardCell cell = board.getCell(18, 10);
		board.calcTargets(cell, 4);
		
		Set<TestBoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(18, 6)));
		assertTrue(targets.contains(board.getCell(18, 8)));
		assertTrue(targets.contains(board.getCell(18, 12)));
		assertTrue(targets.contains(board.getCell(18, 14)));
		
		assertTrue(targets.contains(board.getCell(19, 7)));
		assertTrue(targets.contains(board.getCell(19, 9)));
		assertTrue(targets.contains(board.getCell(19, 11)));
		assertTrue(targets.contains(board.getCell(19, 13)));
		
		assertTrue(targets.contains(board.getCell(20, 8)));
		assertTrue(targets.contains(board.getCell(20, 12)));
		assertTrue(targets.contains(board.getCell(23, 10)));
		
		assertEquals(11, targets.size());
	}
	
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(7, 6);
		board.calcTargets(cell, 2);
		
		Set<TestBoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(5, 6)));
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertTrue(targets.contains(board.getCell(6, 7)));
		assertTrue(targets.contains(board.getCell(7, 4)));
		assertTrue(targets.contains(board.getCell(7, 8)));
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(8, 7)));
		assertTrue(targets.contains(board.getCell(9, 6)));
		
		assertEquals(8, targets.size());
	}
	
	@Test
	public void testTargetsMixed_3() { // CELL IS OCCUPIED
		TestBoardCell cell = board.getCell(21, 5);
		// Cell 19, 6 is occupied by another player!
		board.calcTargets(cell, 3);
		
		Set<TestBoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(18, 5)));
		assertTrue(targets.contains(board.getCell(19, 4)));
		assertTrue(targets.contains(board.getCell(20, 3)));
		assertTrue(targets.contains(board.getCell(20, 5)));
		assertTrue(targets.contains(board.getCell(20, 7)));
		assertTrue(targets.contains(board.getCell(21, 2)));
		assertTrue(targets.contains(board.getCell(21, 4)));
		assertTrue(targets.contains(board.getCell(21, 6)));
		assertTrue(targets.contains(board.getCell(22, 3)));
		assertTrue(targets.contains(board.getCell(22, 5)));
		assertTrue(targets.contains(board.getCell(22, 7)));
		assertTrue(targets.contains(board.getCell(23, 6)));
		
		assertEquals(12, targets.size());
	}
	
	@Test
	public void testTargestNormal_2(){
		TestBoardCell cell = board.getCell(7, 5);
		board.calcTargets(cell, 1);
		
		Set<TestBoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertTrue(targets.contains(board.getCell(8, 5)));
		assertTrue(targets.contains(board.getCell(7, 6)));
		assertTrue(targets.contains(board.getCell(7, 4)));
		
		assertEquals(4, targets.size());
	}
}
