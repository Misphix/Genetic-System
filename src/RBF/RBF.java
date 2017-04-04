package RBF;

import java.util.Arrays;


public class RBF {

    private int neuronCount = 0; // J number of neuron
    private double weights[]; // w
    private double distance[]; // m
    private double sigma[];
    private double theta;
    Neuron neuron;


    public RBF(int _neuronNumber) {
        assert _neuronNumber > 0;
        this.neuronCount = _neuronNumber;

        neuron = new Neuron();

    }

    public void setParameter(double _theta,double _weights[],double _distance[],double _sigma[]) {

        assert this.neuronCount == _weights.length;

        this.weights = _weights;
        this.theta = _theta;
        this.sigma = _sigma;
        this.distance = _distance;

        //printer.print("RBF","weight",this.weights);

    }

    public int getNeuronCount() {
        return this.neuronCount;
    }

    public double getOutput(double _inputDistance[]) {
        assert _inputDistance.length == this.distance.length / this.neuronCount;
        assert this.neuronCount == sigma.length;
        int dimensions = this.distance.length / this.neuronCount;
        double value = this.theta;
        for (int i = 0;i < this.neuronCount; i++) {
            double result = neuron.getAngle(_inputDistance,Arrays.copyOfRange(this.distance,i*dimensions,i*dimensions+dimensions),this.sigma[i]);
            value += this.weights[i] * result;
        }
        return value;
    }
}
