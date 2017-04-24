package ParticleSystem;

import Util.DnaValidation;

public class Dna {
    private double weights[];
    private double sigma[];
    private double distances[];
    private double theta;
    private double fitnessValue = Double.MAX_VALUE;

    Dna(double weights[], double distances[], double sigma[], double theta) {
        if (DnaValidation.validate(weights, distances, sigma, theta)) {
            this.weights = weights;
            this.distances = distances;
            this.sigma = sigma;
            this.theta = theta;
        }
    }

    void setFitnessValue(double value) {
        fitnessValue = value;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    private void setTheta(double theta) {
        if (DnaValidation.validateTheta(theta)) {
            this.theta = theta;
        } else {
            assert false;
        }

    }

    double[] getWeights() {
        return weights;
    }

    double[] getDistances() {
        return distances;
    }

    double[] getSigma() {
        return sigma;
    }

    double getTheta() {
        return theta;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append(String.format("%.10f", theta));

        for (double value : weights) {
            result.append(String.format(" %.10f", value));
        }

        for (double value : distances) {
            result.append(String.format(" %.10f", value));
        }

        for (double value : sigma) {
            result.append(String.format(" %.10f", value));
        }

        return result.toString();
    }
}
