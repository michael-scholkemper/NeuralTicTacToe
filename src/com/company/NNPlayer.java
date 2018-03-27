package com.company;

public class NNPlayer extends Player {
    NeuralNetwork NN;

    public NNPlayer(char s, int[] layerSizes) {
        super(s);
        NN = new NeuralNetwork(layerSizes, true);
    }

    public int play() {
        //TODO implement use of NN

        return 0;
    }
}
