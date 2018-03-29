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
        double[] input = new double[9];
        input[0] = 2;
        input[1] = -1.5;
        NNPlayer nnp = new NNPlayer('O', NNSize);
        //nnp.NN.neurons[1].add(new Neuron());
        //nnp.NN.neurons[0].get(0).changeWeight(nnp.NN.neurons[1].get(1), 2);

        double[] mutationProbs = new double[4];
        mutationProbs[0] = 0.0;
        mutationProbs[1] = 0.1;
        mutationProbs[2] = 0.0;
        mutationProbs[3] = 1;

        for (int i = 0; i < 500; i++) {
            nnp.train(100, 5, mutationProbs);
        }


        nnp.NN.print();


        System.out.print(nnp.NN.forwardPass(input)[0] + ", ");
        System.out.print(nnp.NN.forwardPass(input)[1] + ", ");
        System.out.print(nnp.NN.forwardPass(input)[2] + ", ");
        System.out.print(nnp.NN.forwardPass(input)[3] + ", ");
        System.out.print(nnp.NN.forwardPass(input)[4] + ", ");
        System.out.print(nnp.NN.forwardPass(input)[5] + ", ");
        System.out.print(nnp.NN.forwardPass(input)[6] + ", ");
        System.out.print(nnp.NN.forwardPass(input)[7] + ", ");
        System.out.print(nnp.NN.forwardPass(input)[8]);


        TicTacToe ttt = new TicTacToe(new Player('X'), nnp, 3);
        TicTacToe tt2 = new TicTacToe(nnp, new Player('X'), 3);

        int Xwin = 0, Xlose = 0, draw = 0;
        for (int i = 0; i < 500; i++) {
            int outcome = ttt.newGame();
            if (outcome == -1) Xwin++;
            else if (outcome == 1) {
                Xlose++;
                ttt.drawField(ttt.board);
            }
            else draw++;
            int outcome2 = tt2.newGame();
            if (outcome2 == -1) Xwin++;
            else if (outcome2 == 1) {
                Xlose++;
                ttt.drawField(ttt.board);
            } else draw++;
        }
        System.out.println("wins: " + Xwin + ", losses: " + Xlose + ", draws: " + draw);

    }


}
