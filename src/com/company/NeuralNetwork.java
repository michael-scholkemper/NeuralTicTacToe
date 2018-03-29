package com.company;

import java.util.ArrayList;

public class NeuralNetwork {
    int[] layerSizes;
    ArrayList<Neuron>[] neurons;
    ArrayList<Double>[] weights;

    public NeuralNetwork(int[] pLayerSizes, boolean fullyConnected) {
        layerSizes = pLayerSizes;
        weights = new ArrayList[layerSizes.length];
        neurons = new ArrayList[layerSizes.length];

        for (int i = 0; i < layerSizes.length; i++) {
            neurons[i] = new ArrayList<>();
            weights[i] = new ArrayList<>();
            for (int j = 0; j < layerSizes[i]; j++) {
                Neuron n = new Neuron();
                neurons[i].add(n);
                if (i > 0) {
                    for (int k = 0; k < neurons[i - 1].size(); k++) {
                        neurons[i - 1].get(k).addSucc(n);
                        if (fullyConnected) {
                            neurons[i - 1].get(k).changeWeight(n, Math.random());
                        }
                    }
                }
            }
            Neuron biasNeuron = new Neuron(true);
            if (i < layerSizes.length - 1) neurons[i].add(biasNeuron);
        }
    }

    public NeuralNetwork(int[] pLayerSizes, ArrayList<Neuron>[] pNeurons, ArrayList<Double>[] pWeights) {
        layerSizes = new int[pLayerSizes.length];
        for (int i = 0; i < pLayerSizes.length; i++) {
            layerSizes[i] = pLayerSizes[i];
        }
        weights = new ArrayList[pWeights.length];
        for (int i = 0; i < pWeights.length; i++) {
            weights[i] = new ArrayList<>();
            for (int j = 0; j < pWeights[i].size(); j++) {
                weights[i].add(pWeights[i].get(j));
            }
        }
        neurons = new ArrayList[pNeurons.length];
        for (int i = 0; i < pNeurons.length; i++) {
            neurons[i] = new ArrayList<>();
            for (int j = 0; j < pNeurons[i].size(); j++) {
                neurons[i].add(pNeurons[i].get(j));
            }
        }
    }

    //the networks forwardPass-method uses that the network has a layered structure
    public double[] forwardPass(double[] input) {
        for (int i = 0; i < input.length && i < neurons[0].size(); i++) {
            neurons[0].get(i).currentvalue = input[i];
        }

        for (int i = 0; i < neurons.length; i++) {
            for (int j = 0; j < neurons[i].size(); j++) {
                neurons[i].get(j).updateCurrentValue();
            }
        }

        double[] output = new double[neurons[neurons.length - 1].size()];

        for (int i = 0; i < output.length; i++) {
            output[i] = neurons[neurons.length - 1].get(i).currentvalue;
        }
        return output;
    }

    public NeuralNetwork clone() {
        NeuralNetwork clone = new NeuralNetwork(layerSizes, false);
        for (int i = 0; i < clone.neurons.length; i++) {
            for (int j = 0; j < clone.neurons[i].size(); j++) {
                for (int k = 0; k < clone.neurons[i].get(j).succsWeights.size(); k++)
                    clone.neurons[i].get(j).succsWeights.set(k, this.neurons[i].get(j).succsWeights.get(k).doubleValue());
            }
        }
        return clone;
    }

    public void print() {
        for (ArrayList<Neuron> an : neurons) {
            for (Neuron n : an) {
                for (int i = 0; i < n.succsWeights.size(); i++) {
                    if (n.succsWeights.get(i) != null)
                        System.out.print(n.succsWeights.get(i) + ", ");
                }
                System.out.print(" | ");
            }
            System.out.println("");
        }
    }
}
