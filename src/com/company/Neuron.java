package com.company;

import javafx.util.Pair;

import java.util.ArrayList;

import static java.lang.Math.exp;

public class Neuron {
    ArrayList<Neuron> preds, succs;
    ArrayList<Double> predsWeights, succsWeights;
    boolean isBias = false;
    double currentvalue;

    public Neuron() {
        preds = new ArrayList<>();
        succs = new ArrayList<>();
        succsWeights = new ArrayList<>();
    }

    public Neuron(boolean pisBias) {
        preds = new ArrayList<>();
        succs = new ArrayList<>();
        succsWeights = new ArrayList<>();
        isBias = pisBias;
    }

    public void addPred(Neuron pred) {
        if (!preds.contains(pred)) {
            preds.add(pred);
        }
    }

    public void addSucc(Neuron succ) {
        if (!succs.contains(succ)) {
            succs.add(succ);
            succsWeights.add((double) 1);
            succ.addPred(this);
        }
    }

    public void removeSucc(Neuron n) {
        if (!succs.contains(n)) {
            return;
        }
        succsWeights.remove(succs.indexOf(n));
        n.removePred(this);
        succs.remove(n);
    }

    public void removePred(Neuron n) {
        if (!preds.contains(n)) return;
        preds.remove(n);
    }

    public void changeWeight(Neuron n, double newWeight) {
        if (succs.contains(n)) {
            succsWeights.set(succs.indexOf(n), newWeight);
        } else if (preds.contains(n)) {
            preds.get(preds.indexOf(n)).changeWeight(this, newWeight);
        } else {
            System.err.println("Tried to change weigth vaule of node not in succs.");
        }
    }

    public void updateCurrentValue() {
        if (isBias) {
            currentvalue = 1;
            return;
        }
        double sum = 0;
        for (Neuron n : preds) {
            sum += n.currentvalue;
        }
        currentvalue = 1 / (1 + exp(-sum));
    }

}
