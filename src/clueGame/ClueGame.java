package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class ClueGame extends JFrame {

    private static ClueGame theInstance = new ClueGame();
    public static final int CELL_SIZE = 24;
    public static final int MENU_SIZE = 150;
    private static Board board;
    private int numRows,numCols,yDim,xDim;
    private static GameControlPanel gcp;
    private static PlayerHandPanel php;

    private ClueGame() {
        // setting up constants
        initialize();
        xDim = board.getNumColumns() * CELL_SIZE;
        yDim = board.getNumRows() * CELL_SIZE;
        // JFrame specifications
        setSize(xDim + MENU_SIZE, yDim + MENU_SIZE);
        setLayout(new BorderLayout());
        // subcomponents in frame
        gcp = new GameControlPanel();
        php = new PlayerHandPanel();
        add(php, BorderLayout.EAST);
        add(gcp, BorderLayout.SOUTH);
        add(board, BorderLayout.CENTER);
    }
    
    /**
     * setting up the game board
     */
    private static void initialize() {
        board = Board.getInstance();
        board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
        board.initialize();
    }
    
    /**
     * updating all components
     */
    public static void update() {
        php.updatePanels();
        board.repaint();
    }

    /**
     * print an error message in a JOptionPane
     * 
     * @param message
     */
    public static void errorMessage(String message) {
        JOptionPane.showMessageDialog(theInstance, message, "Message", 1);
    }

    /**
     * singleton pattern method
     * 
     * @return
     */
    public static ClueGame getInstance() {
        return theInstance;
    }
    
    /**
     * main method to run program
     * 
     * @param args
     */
    public static void main(String[] args) {
        // setting up and running clue
        ClueGame clue = ClueGame.getInstance();
        clue.setVisible(true);
        clue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(clue, "<html><center>You are " + board.getHuman().getName() + ".<br>Can you find the solution<br>before the Computer Players?", "Welcome to Clue", 1);
        ClueGame.update();
    }

}
