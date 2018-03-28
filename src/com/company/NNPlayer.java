package com.company;

public class NNPlayer extends Player {
    NeuralNetwork NN;
    int sqrtInputLayerSize = 3;

    public NNPlayer(char s, int[] layerSizes) {
        super(s);
        NN = new NeuralNetwork(layerSizes, false);
    }

    public NNPlayer(char s, NeuralNetwork nn) {
        super(s);
        NN = nn;
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

    public void train(int populationSize, int mutationPoints, double[] mutationProbs) {
        NeuralNetwork[] gen = generatePopulation(populationSize, mutationPoints, mutationProbs);

        for (NeuralNetwork adversary : gen) {
            TicTacToe ttt = new TicTacToe(new NNPlayer('X', this.NN), new NNPlayer('O', adversary), sqrtInputLayerSize);
            TicTacToe ttt2 = new TicTacToe(new NNPlayer('O', adversary), new NNPlayer('X', this.NN), sqrtInputLayerSize);
            int sum = ttt.newGame();
            sum += ttt2.newGame();
            if (sum == 0) {
                if (Math.random() < 0.5) {
                    sum += ttt.newGame();
                } else {
                    sum += ttt2.newGame();
                }
            }
            if (sum < 0) this.NN = adversary;
        }
    }

    public NeuralNetwork[] generatePopulation(int amountOfPopulation, int mutationPoints, double[] mutationProbs) {
        NeuralNetwork[] gen = new NeuralNetwork[amountOfPopulation];
        for (int i = 0; i < gen.length; i++) {
            gen[i] = NN.clone();
            for (int j = 0; j < mutationPoints; j++) {
                double rand = Math.random() * mutationProbs.length;
                if (rand < mutationProbs[0]) {
                } else if (rand < mutationProbs[1]) {//adding a connection between existing neurons
                    int index1 = (int) (Math.random() * gen[i].neurons.length - 1);
                    int index2 = (int) (Math.random() * gen[i].neurons[index1].size());
                    int index3 = (int) (Math.random() * gen[i].neurons[index1 + 1].size());
                    if (index2 > 0 && index3 > 0) {
                        gen[i].neurons[index1].get(index2).addSucc(gen[i].neurons[index1 + 1].get(index3));//extremely bad
                    }
                } else if (rand < mutationProbs[1]) {//adding a new neuron into the network
                    Neuron newNeuron = new Neuron();
                    int index1 = (int) (Math.random() * gen[i].neurons.length - 2) + 1;
                    int index2 = (int) (Math.random() * gen[i].neurons[index1 - 1].size());
                    int index3 = (int) (Math.random() * gen[i].neurons[index1 + 1].size());
                    gen[i].neurons[index1].add(newNeuron);
                    gen[i].neurons[index1 - 1].get(index2).addSucc(newNeuron);
                    gen[i].neurons[index1 + 1].get(index3).addPred(newNeuron);
                } else if (rand < mutationProbs[2]) {//removing a neuron
                    int index1 = (int) (Math.random() * gen[i].neurons.length - 2) + 1;
                    int index2 = (int) (Math.random() * gen[i].neurons[index1].size());
                    if (gen[i].neurons[index1].size() > 0) {
                        gen[i].neurons[index1].get(index2).remove();
                        gen[i].neurons[index1].remove(index2);
                    }
                } else if (rand < mutationProbs[3]) {//changing a weight between two neurons
                    int index1 = (int) (Math.random() * gen[i].neurons.length - 1);
                    int index2 = (int) (Math.random() * gen[i].neurons[index1].size());
                    if (gen[i].neurons[index1].size() > 0) {
                        Neuron n = gen[i].neurons[index1].get(index2);
                        if (n.succs.size() > 0) {
                            n.changeWeight(n.succs.get((int) (Math.random() * n.succs.size())), (Math.random() - 0.5) * 100);
                        }
                    }
                }
            }
        }
        return gen;
    }
}
