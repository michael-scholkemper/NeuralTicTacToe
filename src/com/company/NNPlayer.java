package com.company;

public class NNPlayer extends Player {
    NeuralNetwork NN;

    public NNPlayer(char s, int[] layerSizes) {
        super(s);
        NN = new NeuralNetwork(layerSizes, true);
    }

    public int play(char[][] board) {
        double[] input = new double[board.length * board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                input[i * board.length + j] = (double) board[i][j];
            }
        }
        double[] output = NN.forwardPass(input);
        int indexOfMax = 0;
        for (int i = 0; i < output.length; i++) {
            if (output[i] > output[indexOfMax]) indexOfMax = i;
        }
        return indexOfMax;
    }

    public void train() {
        //TODO
    }
}
