package RBF;

import java.util.Arrays;


public class RBF {

    private int neuronCount = 0; // J number of neuron
    private double weights[]; // w
    private double distance[]; // m
    private double sigma[];
    private double theta;
    private Neuron neuron;


    public RBF(int neuronNumber) {
        assert neuronNumber > 0;

        neuronCount = neuronNumber;
        neuron = new Neuron();
    }

    public void setParameter(double theta, double weights[], double distance[], double sigma[]) {

        assert this.neuronCount == weights.length;

        this.weights = weights;
        this.theta = theta;
        this.sigma = sigma;
        this.distance = distance;
    }

    public int getNeuronCount() {
        return neuronCount;
    }

    public double getOutput(double inputDistance[]) {
        assert inputDistance.length == distance.length / neuronCount;
        assert neuronCount == sigma.length;

        int dimensions = distance.length / neuronCount;
        double value = theta;

        for (int i = 0; i < neuronCount; i++) {
            double result = neuron.getAngle(inputDistance, Arrays.copyOfRange(distance, i * dimensions, i * dimensions + dimensions), sigma[i]);
            value += weights[i] * result;
        }
        return value;
    }
}
