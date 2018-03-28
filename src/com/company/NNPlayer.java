package com.company;

public class NNPlayer extends Player {
    NeuralNetwork NN;

    public NNPlayer(char s, int[] layerSizes) {
        super(s);
        NN = new NeuralNetwork(layerSizes, false);
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

    public void train(int amountOfGens, int mutationPoints, double[] mutationProbs) {
        NeuralNetwork[] gen = new NeuralNetwork[amountOfGens];
        for (int i = 0; i < gen.length; i++) {
            gen[i] = NN.clone();
            for (int j = 0; j < mutationPoints; j++) {
                double rand = Math.random() * mutationProbs.length;
                if (rand < mutationProbs[0]) {
                } else if (rand < mutationProbs[1]) {//adding a connection between existing neurons
                    int index1 = (int) (Math.random() * gen[i].neurons.length);
                    int index2 = (int) (Math.random() * gen[i].neurons[index1].size());
                    int index3 = (int) (Math.random() * gen[i].neurons[index1 + 1].size());
                    gen[i].neurons[index1].get(index2).addSucc(gen[i].neurons[index1 + 1].get(index3));//extremely bad
                } else if (rand < mutationProbs[1]) {//adding a new neuron into the network
                    Neuron newNeuron = new Neuron();
                    int index1 = (int) (Math.random() * gen[i].neurons.length - 1);
                    int index2 = (int) (Math.random() * gen[i].neurons[index1 - 1].size());
                    int index3 = (int) (Math.random() * gen[i].neurons[index1 + 1].size());
                    gen[i].neurons[index1].add(newNeuron);
                    gen[i].neurons[index1 - 1].get(index2).addSucc(newNeuron);
                    gen[i].neurons[index1 + 1].get(index3).addPred(newNeuron);
                }//TODO more mutations
            }
        }
    }
}
