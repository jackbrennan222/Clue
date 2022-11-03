package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashSet;

import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.*;

import clueGame.*;

public class GameSetupTests {
    private Board board;

    @BeforeEach
    void setup() {
        // get board instance because it's singleton
		board = Board.getInstance();
		// set the proper config files
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		// initialize the board
		board.initialize();
    }

    @Test
    void playersTest() {
        ArrayList<Player> players = board.getPlayers();
        assertEquals(players.size(), 6);
        
        int humans = 0;
        int robots = 0;
        
        for (Player p : players) {
        	if (p instanceof HumanPlayer) { humans++; }
        	else if (p instanceof ComputerPlayer) { robots++; }
        }
        
        assertEquals(humans, 1);
        assertEquals(robots, 5);
    }

    @Test
    void createDeckTest() {
        ArrayList<Card> deck = board.getDeck();
        int rooms = 0;
        int weapons = 0;
        int people = 0;

        for (Card c : deck) {
            CardType type = c.getCardType();
            if (type == CardType.ROOM) { rooms++; }
            else if (type == CardType.WEAPON) { weapons++; }
            else if (type == CardType.PERSON) { people++; }
        }
        
        assertEquals(deck.size(), 21);
        assertEquals(rooms, 9);
        assertEquals(weapons, 6);
        assertEquals(people, 6);
    }

    @Test
    void testSolution() {
        Solution ans = board.getTheAnswer();
        assertTrue(ans.getPerson().getCardType() == CardType.PERSON);
        assertTrue(ans.getRoom().getCardType() == CardType.ROOM);
        assertTrue(ans.getWeapon().getCardType() == CardType.WEAPON);
    }
    
    @Test
    void testPlayerHands() {
    	ArrayList<Player> players = board.getPlayers();
        assertEquals(players.size(), 6);
        HashSet<Card> cardSet = new HashSet<Card>();
        for (Player p : players) {
            for (Card c : p.getHand()) {
            	assertFalse(cardSet.contains(c));
            	cardSet.add(c);
            }
        }
    }
}
