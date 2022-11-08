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
    private static Card room;
    private static Card person;
    private static Card weapon;
    private static Card room2;
    private static Card person2;
    private static Card weapon2;

    @BeforeAll
    static void fixedSetup() {
        room = new Card("room", CardType.ROOM);
        person = new Card("person", CardType.PERSON);
        weapon = new Card("weapon", CardType.WEAPON);

        room2 = new Card("room2", CardType.ROOM);
        person2 = new Card("person2", CardType.PERSON);
        weapon2 = new Card("weapon2", CardType.WEAPON);

        player = new HumanPlayer("name", Color.RED);
        player2 = new ComputerPlayer("name2", Color.BLUE);

        player.updateHand(room);
        player.updateHand(person);
        player.updateHand(weapon);

        player2.updateHand(room2);
        player2.updateHand(person2);
        player2.updateHand(weapon2);
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
    void testAccusation() {
        Solution solution = board.getTheAnswer();
        Card solRoom = solution.getRoom();
        Card solPerson = solution.getPerson();
        Card solWeapon = solution.getWeapon();
        Boolean accusation = board.checkAccusation(solRoom, solPerson, solWeapon);
        assertTrue(accusation);
        ArrayList<Card> deck = board.getDeck();
        Card altRoom = deck.get(0).equals(solRoom) ? deck.get(1) : deck.get(0);
        accusation = board.checkAccusation(altRoom, solPerson, solWeapon);
        assertFalse(accusation);
        Card altPerson = deck.get(15).equals(solPerson) ? deck.get(16) : deck.get(15);
        accusation = board.checkAccusation(solRoom, altPerson, solWeapon);
        assertFalse(accusation);
        Card altWeapon = deck.get(9).equals(solPerson) ? deck.get(10) : deck.get(9);
        accusation = board.checkAccusation(solRoom, solPerson, altWeapon);
        assertFalse(accusation);
    }

    @Test 
    void testDisproveSuggestion() {
        Card disprove = player.disproveSuggestion(room, person2, weapon2);
        assertEquals(disprove, room);
        disprove = player.disproveSuggestion(room, person, weapon2);
        assertTrue(disprove.equals(room) || disprove.equals(person));
        disprove = player.disproveSuggestion(room2, person2, weapon2);
        assertEquals(disprove, null);
    }

    @Test
    void testHandleSuggestion() {
        Player player = board.getPlayers().get(0);
        Card solRoom = board.getTheAnswer().getRoom();
        Card solPerson = board.getTheAnswer().getPerson();
        Card solWeapon = board.getTheAnswer().getWeapon();
        Solution suggestion = new Solution(solRoom, solPerson, solWeapon);
        Card disprove = board.handleSuggestion(player, suggestion);
        assertEquals(disprove, null);
        Card pCard = player.getHand().get(0);
        if (pCard.getCardType() == CardType.ROOM) {
            suggestion = new Solution(pCard, solPerson, solWeapon);
        } else if (pCard.getCardType() == CardType.PERSON) {
            suggestion = new Solution(solRoom, pCard, solWeapon);
        } else if (pCard.getCardType() == CardType.WEAPON) {
            suggestion = new Solution(solRoom, solPerson, pCard);
        }
        disprove = board.handleSuggestion(player, suggestion);
        assertEquals(disprove, null);

    }
}
