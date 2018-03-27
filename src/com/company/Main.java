package com.company;

public class Main {

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        TicTacToe ttt = new TicTacToe(new Player('X'), new Player('O'), 3);
        int Xwin = 0, Xlose = 0, draw = 0;
        for (int i = 0; i < 100000; i++) {
            char outcome = ttt.newGame();
            if (outcome == 'X') Xwin++;
            else if (outcome == 'O') Xlose++;
            else draw++;
        }
        System.out.println("wins: " + Xwin + ", losses: " + Xlose + ", draws: " + draw);
    }


}
