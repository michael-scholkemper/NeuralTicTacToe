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
            for (int j = 0; j < layerSizes[i]; j++) {
                Neuron n = new Neuron();
                neurons[i].add(n);
                if (i > 0 && fullyConnected) {
                    for (int k = 0; k < layerSizes[i - 1]; k++) {
                        neurons[i - 1].get(k).addSucc(n);
                    }
                }
            }
            neurons[i].add(new Neuron(true));
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
}