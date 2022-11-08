package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.Color;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import clueGame.*;

public class GameSolutionTest {
    private Board board;
    private static Player player;
    private static Player player2;
    private static Player player3;
    private static Card room;
    private static Card person;
    private static Card weapon;
    private static Card room2;
    private static Card person2;
    private static Card weapon2;
    private static Card room3;
    private static Card person3;
    private static Card weapon3;
    private static Card room4;
    private static Card person4;
    private static Card weapon4;
    private static ArrayList<Player> players;

    @BeforeAll
    static void fixedSetup() {
        // create players with fixed hands
        room = new Card("room", CardType.ROOM);
        person = new Card("person", CardType.PERSON);
        weapon = new Card("weapon", CardType.WEAPON);

        room2 = new Card("room2", CardType.ROOM);
        person2 = new Card("person2", CardType.PERSON);
        weapon2 = new Card("weapon2", CardType.WEAPON);

        room3 = new Card("room3", CardType.ROOM);
        person3 = new Card("person3", CardType.PERSON);
        weapon3 = new Card("weapon3", CardType.WEAPON);

        room4 = new Card("room3", CardType.ROOM);
        person4 = new Card("person3", CardType.PERSON);
        weapon4 = new Card("weapon3", CardType.WEAPON);

        player = new HumanPlayer("name", Color.RED);
        player2 = new ComputerPlayer("name2", Color.BLUE);
        player3 = new ComputerPlayer("name", Color.GREEN);

        player.updateHand(room);
        player.updateHand(person);
        player.updateHand(weapon);

        player2.updateHand(room2);
        player2.updateHand(person2);
        player2.updateHand(weapon2);

        player3.updateHand(room3);
        player3.updateHand(person3);
        player3.updateHand(weapon3);

        players = new ArrayList<>();
        players.add(player);
        players.add(player2);
        players.add(player3);
    }

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
    void checkAccusation() {
        Solution solution = board.getTheAnswer();
        Card solRoom = solution.getRoom();
        Card solPerson = solution.getPerson();
        Card solWeapon = solution.getWeapon();
        // make sure accusation returns true when correct solution is passed in
        Boolean accusation = board.checkAccusation(solRoom, solPerson, solWeapon);
        assertTrue(accusation);
        // make sure accusation with wrong room returns false
        ArrayList<Card> deck = board.getDeck();
        Card altRoom = deck.get(0).equals(solRoom) ? deck.get(1) : deck.get(0);
        accusation = board.checkAccusation(altRoom, solPerson, solWeapon);
        assertFalse(accusation);
        // make sure accusation with wrong person returns false
        Card altPerson = deck.get(15).equals(solPerson) ? deck.get(16) : deck.get(15);
        accusation = board.checkAccusation(solRoom, altPerson, solWeapon);
        assertFalse(accusation);
        // make sure accusation with wrong weapon returns false
        Card altWeapon = deck.get(9).equals(solPerson) ? deck.get(10) : deck.get(9);
        accusation = board.checkAccusation(solRoom, solPerson, altWeapon);
        assertFalse(accusation);
    }

    @Test 
    void disproveSuggestion() {
        // make sure player returns proper card when one card is in their hand
        Card disprove = player.disproveSuggestion(room, person2, weapon2);
        assertEquals(disprove, room);
    // make sure player returns proper card when two cards are in their hand
        disprove = player.disproveSuggestion(room, person, weapon2);
        assertTrue(disprove.equals(room) || disprove.equals(person));
        // make sure player returns null when no card is in their hand
        disprove = player.disproveSuggestion(room2, person2, weapon2);
        assertEquals(disprove, null);
    }

    @Test
    void handleSuggestion() {
        // make sure null is returned when no players have the cards
        Solution suggestion = new Solution(room4, person4, weapon4);
        Card disprove = handleSuggestion(player, suggestion);
        assertEquals(disprove, null);
        // make sure null is returned when player suggesting has all 3 cards
        suggestion = new Solution(room, person, weapon);
        disprove = handleSuggestion(player, suggestion);
        assertEquals(disprove, null);
        // make sure proper card is returned when only 1 player has a card to disprove
        suggestion = new Solution(room2, person4, weapon4);
        disprove = handleSuggestion(player, suggestion);
        assertEquals(disprove, room2);
        // make sure proper card is returned when 2 players have a card to disprove
        suggestion = new Solution(room2, person3, weapon4);
        disprove = handleSuggestion(player, suggestion);
        assertEquals(disprove, room2);
    }
    // copied from board to be used with fixed players
    private Card handleSuggestion(Player player, Solution suggestion) {
		for (Player p : players) {
			if (p.equals(player)) { continue; }
			Card dispute = p.disproveSuggestion(suggestion.getRoom(), suggestion.getPerson(), suggestion.getWeapon());
			if (dispute != null) {
				return dispute;
			}
		}
		return null;
	}
}
