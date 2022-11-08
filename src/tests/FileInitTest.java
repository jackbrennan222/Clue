package tests;

import static org.junit.Assert.*;

import org.junit.jupiter.api.*;

import clueGame.*;

public class FileInitTest {
	private static Board board;

    @BeforeAll
    static void setup() {
        // get board instance because it's singleton
		board = Board.getInstance();
		// set the proper config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// initialize the board
		board.initialize();
    }
	
	@Test
	public void testRoomLabels() {
		// test first and last room with some others
		assertEquals("CTLM", board.getRoom('C').getName());
		assertEquals("Kaff", board.getRoom('K').getName());
		assertEquals("Hill", board.getRoom('H').getName());
		assertEquals("Library", board.getRoom('L').getName());
	}
	
	@Test
	public void testDimensions() {
		// test right number of rows and cols are read from the file
		assertEquals(25, board.getNumRows());
		assertEquals(25, board.getNumColumns());
	}
	
	@Test
	public void fourDoorDirections() {
		// test one of each door direction
		BoardCell cell = board.getCell(3, 7);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		cell = board.getCell(16, 21);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		cell = board.getCell(21, 17);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		cell = board.getCell(7, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		// test that walkways are not doors
		cell = board.getCell(0, 8);
		assertFalse(cell.isDoorway());
	}
	
	@Test
	public void testNumberOfDoorways() {
		// count to make sure correct number of doors was read
		int numDoors = 0;
		for (int row = 0; row < board.getNumRows(); row++)
			for (int col = 0; col < board.getNumColumns(); col++) {
				BoardCell cell = board.getCell(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		assertEquals(9, numDoors);
	}
	
	@Test
	public void testRooms() {
		// just test a standard room location
		BoardCell cell = board.getCell(18, 18);
		Room room = board.getRoom(cell) ;
		assertTrue(room != null);
		assertEquals(room.getName(), "Kaff") ;
		assertFalse(cell.isLabel());
		assertFalse(cell.isRoomCenter()) ;
		assertFalse(cell.isDoorway()) ;

		// this is a label cell to test
		cell = board.getCell(1, 1);
		room = board.getRoom(cell) ;
		assertTrue(room != null);
		assertEquals(room.getName(), "CTLM") ;
		assertTrue(cell.isLabel());
		assertTrue(room.getLabelCell() == cell);
		
		// this is a room center cell to test
		cell = board.getCell(3, 12);
		room = board.getRoom(cell) ;
		assertTrue(room != null);
		assertEquals(room.getName(), "Marquez") ;
		assertTrue(cell.isRoomCenter());
		assertTrue(room.getCenterCell() == cell);
		
		// this is a secret passage test
		cell = board.getCell(5, 1);
		room = board.getRoom(cell) ;
		assertTrue(room != null);
		assertEquals(room.getName(), "CTLM") ;
		assertTrue(cell.getSecretPassage() == 'K');
		
		// test a walkway
		cell = board.getCell(0, 8);
		room = board.getRoom(cell) ;
		// Note for our purposes, walkways and closets are rooms
		assertTrue(room != null);
		assertEquals(room.getName(), "Walkway") ;
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());
		
		// test a closet
		cell = board.getCell(0, 5);
		room = board.getRoom(cell) ;
		assertTrue(room != null);
		assertEquals(room.getName(), "Unused") ;
		assertFalse(cell.isRoomCenter());
		assertFalse(cell.isLabel());
	}
}
