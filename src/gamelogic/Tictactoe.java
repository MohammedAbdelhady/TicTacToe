package gamelogic;

public class Tictactoe {

    private int[][] board = {{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
    private int currentPlayerMark;

    public int getCurrentPlayerMark() {
        return currentPlayerMark;
    }

    
    public Tictactoe() {
        currentPlayerMark = 1;
    }

    public boolean isBoardFull() {
        boolean isFull = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == -1) {
                    isFull = false;
                }
            }
        }

        return isFull;
    }

    public boolean checkForWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }

    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2]) == true) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i]) == true) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        return ((checkRowCol(board[0][0], board[1][1], board[2][2]) == true) || (checkRowCol(board[0][2], board[1][1], board[2][0]) == true));
    }

    private boolean checkRowCol(int c1, int c2, int c3) {
        return ((c1 != -1) && (c1 == c2) && (c2 == c3));
    }

    public void changePlayer() {
        if (currentPlayerMark == 1) {
            currentPlayerMark = 0;
        } else {
            currentPlayerMark = 1;
        }
        
    }

    public boolean placeMark(int row, int col) {

        if ((row >= 0) && (row < 3)) {
            if ((col >= 0) && (col < 3)) {
                if (board[row][col] == -1) {
                    board[row][col] = currentPlayerMark;
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {

        Tictactoe game = new Tictactoe();

        game.placeMark(0, 2);

        if (game.checkForWin()) {
            System.out.println("We have a winner! Congrats!");
            System.exit(0);
        } else if (game.isBoardFull()) {
            System.out.println("Appears we have a draw!");
            System.exit(0);
        }

        game.changePlayer();

    }
}
