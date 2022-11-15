package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class ClueGame extends JFrame {

    public static final int CELL_SIZE = 24;
    public static final int MENU_SIZE = 150;
    private static Board board;
    private int numRows,numCols,yDim,xDim;
    private static GameControlPanel gcp;
    private static PlayerHandPanel php;

    public ClueGame() {
        initialize();
        // setting up constants
        numRows = board.getNumRows();
        numCols = board.getNumColumns();
        xDim = numCols * CELL_SIZE;
        yDim = numRows * CELL_SIZE;
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
    private void update() {
        php.updatePanels();
        board.repaint();
    }
    
    /**
     * main method to run program
     * 
     * @param args
     */
    public static void main(String[] args) {
        // setting up and running clue
        ClueGame clue = new ClueGame();
        clue.setVisible(true);
        clue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(clue, "You are " + board.getHuman().getName() + ".\nCan you find the solution\nbefore the computer players", "Welcome to Clue", 1);
        clue.update();
    }
}
