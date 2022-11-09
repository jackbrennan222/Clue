package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.*;

public class ComputerAITest {
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
    void selectTargets() {
        // checks to see if random cell is selected if no room is found
        ComputerPlayer player = (ComputerPlayer) board.getPlayers().get(1);
        BoardCell pos = board.getCell(8, 10);
        board.calcTargets(pos, 3);
        BoardCell dest = player.selectTarget();
        assertFalse(dest.isRoom());
        // checks to see if room is selected when available to move to
        board.calcTargets(pos, 4);
        dest = player.selectTarget();
        assertTrue(dest.isRoom());
        // checks to see if random cell is selected when only visited rooms are available
        Set<BoardCell> visited = new HashSet<>();
        visited.add(board.getCell(3, 12));
        player.setVisitedRooms(visited);
        dest = player.selectTarget();
        assertFalse(dest.isRoom());
    }

    @Test
    void createSuggestion() {
        // setup for tests, seeing all but two cards
        ComputerPlayer comp = new ComputerPlayer("Dude", Color.MAGENTA);
        comp.setPos(0, 0);
        for (int i = 0; i < board.getDeck().size(); i++) {
            if (i != 13 && i != 20) {
                comp.updateSeen(board.getDeck().get(i));      
            } 
        }
        // tests to see if room person and weapon are correct
        Solution suggestion = comp.createSuggestion();
        assertTrue(suggestion.getRoom().equals(board.getDeck().get(0)));
        assertEquals(suggestion.getPerson(), board.getDeck().get(13));
        assertEquals(suggestion.getWeapon(), board.getDeck().get(20));

        // setup for tests, seeing all but four cards
        comp = new ComputerPlayer("Dude", Color.MAGENTA);
        comp.setPos(0, 0);
        for (int i = 0; i < board.getDeck().size(); i++) {
            if (i != 13 && i != 14 && i != 19 && i != 20) {
                comp.updateSeen(board.getDeck().get(i));      
            } 
        }

        // tests to see if person and weapon are correct
        suggestion = comp.createSuggestion();
        assertTrue(suggestion.getPerson().equals(board.getDeck().get(13)) || suggestion.getPerson().equals(board.getDeck().get(14)));
        assertTrue(suggestion.getWeapon().equals(board.getDeck().get(19)) || suggestion.getWeapon().equals(board.getDeck().get(20)));
    }
}
