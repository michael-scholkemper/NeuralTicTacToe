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
                input[i * board.length + j] = (double) (board[i][j] == symbol ? 1 : board[i][j] == ' ' ? 0 : -1);
            }
        }
        double[] output = NN.forwardPass(input);
        int indexOfMax = 0;
        boolean finished;
        do {
            finished = true;
            for (int i = 0; i < output.length; i++) {
                if (output[i] > output[indexOfMax]) indexOfMax = i;
            }

            if (board[indexOfMax / board.length][indexOfMax % board.length] != ' ') {
                output[indexOfMax] = 0;
                finished = false;
            }
        } while (!finished);
        return indexOfMax;
    }

    public void train(int populationSize, int mutationPoints, double[] mutationProbs) {
        NeuralNetwork[] gen = generatePopulation(populationSize, mutationPoints, mutationProbs);
        gen[0] = this.NN;
        /*for (NeuralNetwork adversary : gen) {
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
        }*/
        int indexOfFittest = 0, fitnessOfFittest = -100;
        int[] fitnessArray = new int[gen.length];
        for (int index = 0; index < gen.length; index++) {
            NeuralNetwork adversary = gen[index];
            int fitness = 0;
            TicTacToe ttt = new TicTacToe(new Player('X'), new NNPlayer('O', adversary), sqrtInputLayerSize);
            TicTacToe ttt2 = new TicTacToe(new NNPlayer('X', adversary), new Player('O'), sqrtInputLayerSize);

            for (int i = 0; i < 100; i++) {

                fitness += ttt.newGame() * -2 + 1 + 0.1 * ttt.player2_movesMade;
                fitness += ttt2.newGame() * 2 + 1 + 0.1 * ttt2.player1_movesMade;
            }
            if (fitness > fitnessOfFittest) {
                fitnessOfFittest = fitness;
                indexOfFittest = index;
                System.out.println(fitness);
            }
            fitnessArray[index] = fitness;
        }

        this.NN = gen[indexOfFittest];
    }

    public NeuralNetwork[] generatePopulation(int amountOfPopulation, int mutationPoints, double[] mutationProbs) {
        NeuralNetwork[] gen = new NeuralNetwork[amountOfPopulation];
        for (int i = 0; i < gen.length; i++) {
            gen[i] = NN.clone();
        }

        for (int i = 0; i < gen.length; i++) {
            for (int j = 0; j < mutationPoints; j++) {
                double rand = Math.random();
                if (rand < mutationProbs[0]) {
                } else if (rand < mutationProbs[1]) {//adding a connection between existing neurons
                    addRandomConnection(gen[i]);
                } else if (rand < mutationProbs[1]) {//adding a new neuron into the network
                    addRandomNeuron(gen[i]);
                } else if (rand < mutationProbs[2]) {//removing a neuron
                    removeRandomNeuron(gen[i]);
                } else if (rand < mutationProbs[3]) {//changing a weight between two neurons
                    changeRandomWeight(gen[i]);
                }
            }
        }
        return gen;
    }

    public void addRandomConnection(NeuralNetwork gen) {
        int index1 = (int) (Math.random() * gen.neurons.length - 1);
        int index2 = (int) (Math.random() * gen.neurons[index1].size());
        int index3 = (int) (Math.random() * gen.neurons[index1 + 1].size());
        if (index2 > 0 && index3 > 0) {
            gen.neurons[index1].get(index2).addSucc(gen.neurons[index1 + 1].get(index3));//extremely bad
            gen.neurons[index1].get(index2).changeWeight(gen.neurons[index1 + 1].get(index3), Math.random());
        }
    }

    public void addRandomNeuron(NeuralNetwork gen) {
        int index1 = (int) (Math.random() * (gen.neurons.length - 2)) + 1;
        int index2 = (int) (Math.random() * gen.neurons[index1 - 1].size());
        int index3 = (int) (Math.random() * gen.neurons[index1 + 1].size());
        Neuron newNeuron = new Neuron();
        gen.neurons[index1].add(newNeuron);
        for (Neuron n : gen.neurons[index1 - 1]) {
            n.addSucc(newNeuron);
        }
        for (Neuron n : gen.neurons[index1 + 1]) {
            n.addPred(newNeuron);
        }
    }

    public void removeRandomNeuron(NeuralNetwork gen) {
        int index1 = (int) (Math.random() * gen.neurons.length - 1);
        int index2 = (int) (Math.random() * gen.neurons[index1].size());

        for (int k = 0; k < gen.neurons[index1].get(index2).succsWeights.size(); k++) {
            gen.neurons[index1].get(index2).succsWeights.set(k, 0.0);
        }

    }

    public void changeRandomWeight(NeuralNetwork gen) {
        int index1 = (int) (Math.random() * gen.neurons.length - 1);
        int index2 = (int) (Math.random() * gen.neurons[index1].size());
        if (gen.neurons[index1].size() > 0) {
            Neuron n = gen.neurons[index1].get(index2);
            if (n.succs.size() > 0) {
                n.changeWeight(n.succs.get((int) (Math.random() * n.succs.size())), (Math.random() - 0.5) * 5);
            }
        }
    }
}
