package RBF;

class Neuron {
    double getAngle(double input[], double distance[], double sigma) {
        assert input.length == distance.length;

        double vectorValue = 0;
        for (int i = 0; i < input.length; i++) {
            vectorValue += Math.pow(input[i] - distance[i], 2);
        }

        return Math.exp((-1) * (vectorValue / (2 * Math.pow(sigma, 2))));
    }
}
