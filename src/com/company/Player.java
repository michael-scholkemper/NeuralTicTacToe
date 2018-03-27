package com.company;

public class Player {
    public char symbol;

    public Player(char s1) {
        symbol = s1;
    }

    public int play(char[][] board) {
        //TODO
        return (int) (Math.random() * board.length * board[0].length);
    }
}
