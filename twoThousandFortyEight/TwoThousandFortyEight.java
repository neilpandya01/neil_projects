import java.util.Scanner;
import java.util.ArrayList;

public class TwoThousandFortyEight {
    public static int boardSize = 4;
    public static int[][] gameBoard = new int[boardSize][boardSize];
    public static void main (String[] args) {
        Scanner scan = new Scanner (System.in);
        System.out.println("Directions: Play 2048 with typing using WASD to control movement of numbers. Assume zeros are empty squares.");
        int attempts = 0;
        replaceZeroWithTwo();
        while (true) {
            replaceZeroWithTwo();
            System.out.println("Your current board: ");
            printBoard();
            System.out.println("What is your move? Type anything but WASD to quit.");
            String inp = scan.nextLine();
            if (inp.equalsIgnoreCase("w")) {
                adjustUp();
            }
            else if (inp.equalsIgnoreCase("s")) {
                adjustDown();
            }
            else if (inp.equalsIgnoreCase("a")) {
                adjustLeft();
            }
            else if (inp.equalsIgnoreCase("d")) {
                adjustRight();
            }
            else {
                break;
            }
            System.out.print('\u000C');
            attempts++;
            if (checkFor2048()) {
                System.out.println("You got 2048!!");
                break;
            }
        }
        System.out.println("The game is over. Attempts this round: " + attempts);
    }

    public static void printBoard () {
        for (int r=0; r<boardSize; r++) {
            for (int c=0; c<boardSize; c++) {
                String str = "" + gameBoard[r][c];
                if (c == boardSize-1) {
                    if (str.length() == 4) {
                        System.out.println("" + gameBoard[r][c] + "| ");
                    }
                    else if (str.length() == 3) {
                        System.out.println("" + gameBoard[r][c] + " | ");
                    }
                    else if (str.length() == 2) {
                        System.out.println("" + gameBoard[r][c] + "  | ");
                    }
                    else if (str.length() == 1) {
                        System.out.println("" + gameBoard[r][c] + "   | ");
                    }
                }
                else {
                    if (str.length() == 4) {
                        System.out.print("" + gameBoard[r][c] + "| ");
                    }
                    else if (str.length() == 3) {
                        System.out.print("" + gameBoard[r][c] + " | ");
                    }
                    else if (str.length() == 2) {
                        System.out.print("" + gameBoard[r][c] + "  | ");
                    }
                    else if (str.length() == 1) {
                        System.out.print("" + gameBoard[r][c] + "   | ");
                    }
                }
            }
        }
    }

    public static void replaceZeroWithTwo () {
        ArrayList<Integer> availableRows = new ArrayList();
        ArrayList<Integer> availableColumns = new ArrayList();
        for (int r=0; r<boardSize; r++) {
            for (int c=0; c<boardSize; c++) {
                if (gameBoard[r][c] == 0) {
                    availableRows.add(r);
                    availableColumns.add(c);
                }
            }
        }
        if (availableRows.size() >= 2) {
            int rand = (int) (Math.random()*availableRows.size());
            gameBoard[availableRows.get(rand)][availableColumns.get(rand)] = 2;
        }
    }

    public static boolean checkFor2048 () {
        for (int r=0; r<boardSize; r++) {
            for (int c=0; c<boardSize; c++) {
                if (gameBoard[r][c] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void adjustRight () {
        for (int r=0; r<boardSize; r++) {
            boolean multiply = true;
            for (int i=0; i<4; i++) {
                for (int c=boardSize-1; c>0; c--) {
                    if (gameBoard[r][c] == 0) {
                        gameBoard[r][c] = gameBoard[r][c-1];
                        gameBoard[r][c-1] = 0;
                    }
                    else if ((gameBoard[r][c] == gameBoard[r][c-1]) && multiply) {
                        gameBoard[r][c] *= 2;
                        gameBoard[r][c-1] = 0;
                        multiply = false;
                    }
                }
            }
        }
    }

    public static void adjustLeft () {
        for (int r=0; r<boardSize; r++) {
            boolean multiply = true;
            for (int i=0; i<4; i++) {
                for (int c=0; c<boardSize-1; c++) {
                    if (gameBoard[r][c] == 0) {
                        gameBoard[r][c] = gameBoard[r][c+1];
                        gameBoard[r][c+1] = 0;
                    }
                    else if ((gameBoard[r][c] == gameBoard[r][c+1]) && multiply) {
                        gameBoard[r][c] *= 2;
                        gameBoard[r][c+1] = 0;
                        multiply = false;
                    }
                }
            }
        }
    }

    public static void adjustUp () {
        int numRows = boardSize-1;
        for (int c=0; c<boardSize; c++) {
            boolean multiply = true;
            for (int i=0; i<4; i++) { 
                for (int r=0; r<boardSize-1; r++) {
                    if (gameBoard[r][c] == 0) {
                        gameBoard[r][c] = gameBoard[r+1][c];
                        gameBoard[r+1][c] = 0;
                    }
                    else if ((gameBoard[r][c] == gameBoard[r+1][c]) && multiply) {
                        gameBoard[r][c] *= 2;
                        gameBoard[r+1][c] = 0;
                        multiply = false;
                    }
                }
            }
        }
    }

    public static void adjustDown () {
        for (int c=0; c<boardSize; c++) {
            boolean multiply = true;
            for (int i=0; i<4; i++) {
                for (int r=boardSize-1; r>0; r--) {
                    if (gameBoard[r][c] == 0) {
                        gameBoard[r][c] = gameBoard[r-1][c];
                        gameBoard[r-1][c] = 0;
                    }
                    else if ((gameBoard[r][c] == gameBoard[r-1][c]) && multiply) {
                        gameBoard[r][c] *= 2;
                        gameBoard[r-1][c] = 0;
                        multiply = false;
                    }
                }
            }
        }
    }
}

