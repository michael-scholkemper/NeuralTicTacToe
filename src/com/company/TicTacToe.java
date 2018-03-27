package com.company;

public class TicTacToe {
    Player player1, player2;
    char[][] board;
    int boardSize;
    char playerSymbol;

    public TicTacToe(Player p1, Player p2, int size) {
        if (p1.symbol == p2.symbol) {
            System.err.println("Players cannot have the same Symbol!");
            return;
        }
        boardSize = size;


        player1 = p1;
        player2 = p2;

        newGame();
    }

    public static void drawField(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length - 1; j++) {
                System.out.print(field[i][j] + " | ");
            }
            System.out.println(field[i][field[i].length - 1]);
        }
        System.out.println();
    }

    public char newGame() {
        board = new char[boardSize][boardSize];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }

        while (!gameFinished()) {
            //drawField(board);
            while (performAction(player1.play(), player1) != 0) ;
            if (gameFinished()) break;
            //drawField(board);
            while (performAction(player2.play(), player2) != 0) ;
        }
        drawField(board);
        if (playerSymbol == '%') {
            System.out.println("It's a draw!");
        } else {
            System.out.println(playerSymbol + " won!");
        }
        return playerSymbol;

    }

    public int performAction(int action, Player p) {
        if (board[action / boardSize][action % boardSize] == ' ') {
            board[action / boardSize][action % boardSize] = p.symbol;
            return 0;
        } else return 1;
    }

    public boolean gameFinished() {
        boolean boardFull = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                playerSymbol = board[i][j];
                if (playerSymbol != ' ') {
                    int count = 1;
                    while (i + count < board.length && j < board.length) {
                        if (board[i + count][j] == playerSymbol) count++;
                        else break;
                    }
                    if (count >= boardSize) return true;
                    count = 1;
                    while (i < board.length && j + count < board.length) {
                        if (board[i][j + count] == playerSymbol) count++;
                        else break;
                    }
                    if (count >= boardSize) return true;
                    count = 1;
                    while (i + count < board.length && j + count < board.length) {
                        if (board[i + count][j + count] == playerSymbol) count++;
                        else break;
                    }
                    if (count >= boardSize) return true;
                } else {
                    boardFull = false;
                }
            }
        }
        if (boardFull) playerSymbol = '%';
        return boardFull;
    }
}