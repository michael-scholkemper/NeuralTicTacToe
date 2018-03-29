package com.company;

public class Player {
    public char symbol;

    public Player(char s1) {
        symbol = s1;
    }

    public int play(char[][] board) {
        //TODO
        return (int) (Math.random() * board.length * board[0].length);
        /*for(int i = 0; i < board.length * board.length;i++){
            if(board[i/board.length][i%board.length] == ' ')return i;
        }
        return 0;*/
    }
}
