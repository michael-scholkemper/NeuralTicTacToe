package com.company;

public class Main {

    public static void main(String[] args) {
        char[][] board = new char[3][3];
        int[] NNSize = new int[5];
        NNSize[0] = 9;
        NNSize[1] = 3;
        NNSize[2] = 3;
        NNSize[3] = 3;
        NNSize[4] = 9;
        double[] input = new double[2];
        input[0] = 2;
        input[1] = -1.5;
        NNPlayer nnp = new NNPlayer('O', NNSize);
        nnp.NN.neurons[1].add(new Neuron());
        //nnp.NN.neurons[0].get(0).changeWeight(nnp.NN.neurons[1].get(1), 2);
        //System.out.print(nnp.NN.forwardPass(input)[0]);
        double[] mutationProbs = new double[4];
        mutationProbs[0] = 0.3;
        mutationProbs[1] = 0.6;
        mutationProbs[2] = 0.6;
        mutationProbs[3] = 1;

        for (int i = 0; i < 1000; i++) {
            nnp.train(1000, 3, mutationProbs);
        }

        nnp.NN.print();

        TicTacToe ttt = new TicTacToe(new HumanPlayer('x'), nnp, 3);


        int Xwin = 0, Xlose = 0, draw = 0;
        for (int i = 0; i < 1; i++) {
            int outcome = ttt.newGame();
            if (outcome == 1) Xwin++;
            else if (outcome == -1) Xlose++;
            else draw++;
        }
        System.out.println("wins: " + Xwin + ", losses: " + Xlose + ", draws: " + draw);

    }


}
