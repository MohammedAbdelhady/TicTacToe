package gamelogic;

public class Tictactoe {

    private int[][] board = {{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
    private int currentPlayerMark;
    private int comRow;
    private int comCol;

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

    public int playComputer() {
        comRow = (int) Math.floor(Math.random() * 3);
        comCol = (int) Math.floor(Math.random() * 3);

        while (board[comRow][comCol] != -1 && comCol < 3 && comRow < 3) {
            comRow = (int) Math.floor(Math.random() * 3);
            comCol = (int) Math.floor(Math.random() * 3);
        }

        board[comRow][comCol] = 0;
        System.out.println(comRow + " " + comCol);

        int comImageNo = 0;
        if (comRow == 0 && comCol == 0) {
            comImageNo = 1;
        } else if (comRow == 0 && comCol == 1) {
            comImageNo = 2;
        } else if (comRow == 0 && comCol == 2) {
            comImageNo = 3;
        } else if (comRow == 1 && comCol == 0) {
            comImageNo = 4;
        } else if (comRow == 1 && comCol == 1) {
            comImageNo = 5;
        } else if (comRow == 1 && comCol == 2) {
            comImageNo = 6;
        } else if (comRow == 2 && comCol == 0) {
            comImageNo = 7;
        } else if (comRow == 2 && comCol == 1) {
            comImageNo = 8;
        } else if (comRow == 2 && comCol == 2) {
            comImageNo = 9;
        }

        return comImageNo;
    }

}
