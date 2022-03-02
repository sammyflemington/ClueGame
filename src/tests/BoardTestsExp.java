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
	public void testAdjacency_1() {
		
		TestBoardCell cell = board.getCell(1,1);
		Set<TestBoardCell> testList = cell.getAdjList();
		
		// Middle
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertEquals(4, testList.size());
		
	}
	@Test
	public void testAdjacency_2() {
		
		TestBoardCell cell = board.getCell(1,1);
		Set<TestBoardCell> testList = cell.getAdjList();
		
		// Corner
		cell = board.getCell(0,0);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency_3() {
		
		TestBoardCell cell = board.getCell(1,1);
		Set<TestBoardCell> testList = cell.getAdjList();
		
		// Edge
		cell = board.getCell(2,3);
		testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1,3)));
		assertTrue(testList.contains(board.getCell(3,3)));
		assertTrue(testList.contains(board.getCell(2,2)));
		assertEquals(3, testList.size());
	}
	@Test
	public void testTargetsNormal_1() {
		TestBoardCell cell = board.getCell(0,0);
		System.out.println("N1");
		board.calcTargets(cell, 2);
		
		Set<TestBoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(2,0)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(0,2)));
		
		assertEquals(3, targets.size());
		
	}
	
	@Test
	public void testTargetsNormal_2() {
		TestBoardCell cell = board.getCell(2,1);
		System.out.println("N2");
		board.calcTargets(cell, 1);
		
		Set<TestBoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(3,1)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(2,2)));
		assertTrue(targets.contains(board.getCell(2,0)));
		
		assertEquals(4, targets.size());
		
	}
	
	@Test
	public void testTargetsMixed_1() {
		TestBoardCell cell = board.getCell(1,0);
		board.getCell(0,2).setOccupied(true);
		board.getCell(3,1).setRoom(true);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(0,0)));
		assertTrue(targets.contains(board.getCell(1,3)));
		assertTrue(targets.contains(board.getCell(2,0)));
		assertTrue(targets.contains(board.getCell(2,2)));

		assertEquals(4, targets.size());
	}
	
	@Test
	public void testTargetsMixed_2() {
		TestBoardCell cell = board.getCell(2,3);
		board.getCell(0,2).setOccupied(true);
		board.getCell(3,1).setRoom(true);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		
		assertTrue(targets.contains(board.getCell(1,0)));
		assertTrue(targets.contains(board.getCell(0,1)));
		assertTrue(targets.contains(board.getCell(3,0)));
		assertTrue(targets.contains(board.getCell(0,3)));
		assertTrue(targets.contains(board.getCell(3,2))); // This one is not found for some reason

		assertEquals(5, targets.size());
	}
	
	@Test
	public void testTargetsMixed_3(){
		TestBoardCell cell = board.getCell(1,2);
		board.getCell(0,2).setOccupied(true);
		board.getCell(1,1).setOccupied(true);
		board.getCell(1,3).setOccupied(true);

		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();

		assertTrue(targets.contains(board.getCell(2,1)));
		assertTrue(targets.contains(board.getCell(2,3)));
		assertTrue(targets.contains(board.getCell(3,2)));
		
		assertEquals(3, targets.size());
	}
}
