package com.company;

public class Main {

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        int[] NNSize = new int[3];
        NNSize[0] = 9;
        NNSize[1] = 3;
        NNSize[2] = 9;
        double[] input = new double[2];
        input[0] = 2;
        input[1] = -1.5;
        NNPlayer nnp = new NNPlayer('O', NNSize);

        nnp.NN.neurons[0].get(0).changeWeight(nnp.NN.neurons[1].get(1), 2);
        System.out.print(nnp.NN.forwardPass(input)[0]);


        TicTacToe ttt = new TicTacToe(new HumanPlayer('X'), nnp, 3);



        /*int Xwin = 0, Xlose = 0, draw = 0;
        for (int i = 0; i < 1; i++) {
            char outcome = ttt.newGame();
            if (outcome == 'X') Xwin++;
            else if (outcome == 'O') Xlose++;
            else draw++;
        }
        System.out.println("wins: " + Xwin + ", losses: " + Xlose + ", draws: " + draw);*/

    }


}
