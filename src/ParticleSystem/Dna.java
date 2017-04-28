package ParticleSystem;

import Util.DnaValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dna {
    private double weights[], sigma[], distances[], speed[], theta, fitnessValue = Double.MAX_VALUE;
    private double phy1, phy2;
    private Dna best;

    Dna(double weights[], double distances[], double sigma[], double theta) {
        assert weights != null;
        assert distances != null;
        assert sigma != null;

        if (DnaValidation.validate(weights, distances, sigma, theta)) {
            this.weights = weights;
            this.distances = distances;
            this.sigma = sigma;
            this.theta = theta;
            int size = weights.length + distances.length + sigma.length + 1;
            initializeSpeed(size);
            phy1 = Math.random();
            phy2 = 1 - phy1;
            best = null;
        }
    }

    void move(Dna publicBest) {
        assert publicBest.weights != null;
        assert publicBest.distances != null;
        assert publicBest.sigma != null;

        newSpeed(publicBest);
        int wl = weights.length, dl = distances.length, sl = sigma.length;

        for (int i = 0; i < wl; i++) {
            weights[i] += speed[i];
        }

        for (int i = 0; i < dl; i++) {
            distances[i] += speed[i + wl];
        }

        for (int i = 0; i < sl; i++) {
            sigma[i] += speed[i + wl + dl];
        }

        theta += speed[wl + dl + sl];
    }

    private void newSpeed(Dna globalBest) {
        List<Double> myPos = getPos(this), globalPos = getPos(globalBest);

        for (int i = 0; i < speed.length; i++) {
            if (best != null) {
                List<Double> bestPos = getPos(best);
                speed[i] = speed[i] + phy1 * (bestPos.get(i) - myPos.get(i)) + phy2 * (globalPos.get(i) - myPos.get(i));
            } else {
                speed[i] = speed[i] + phy2 * (globalPos.get(i) - myPos.get(i));
            }
        }
    }

    private List<Double> getPos(Dna dna) {
        assert dna != null;
        assert dna.weights != null;
        assert dna.distances != null;
        assert dna.sigma != null;

        List<Double> pos = new ArrayList<>();
        Arrays.stream(dna.weights).forEach(pos::add);
        Arrays.stream(dna.distances).forEach(pos::add);
        Arrays.stream(dna.sigma).forEach(pos::add);
        pos.add(dna.theta);

        return pos;
    }

    void updateLocalBest() {
        assert weights != null;
        assert distances != null;
        assert sigma != null;

        boolean originbest = best == null;
        if (best != null) {
            best = best.fitnessValue < fitnessValue ? best : clone();
        } else {
            best = clone();
        }

//        assert best.weights != null;
//        assert best.distances != null;
//        assert best.sigma != null;
        clone();
    }

    private void initializeSpeed(int size) {
        speed = new double[size];
        for (int i = 0; i < size; i++) {
            speed[i] = 0;
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

    @Override
    public Dna clone() {
        Dna dna = new Dna(copyValue(weights), copyValue(distances), copyValue(sigma), theta);
        dna.speed = copyValue(speed);
        dna.fitnessValue = fitnessValue;
        dna.phy1 = phy1;
        dna.phy2 = phy2;

        return dna;
    }

    private double[] copyValue(double copyValue[]) {
        double value[] = new double[copyValue.length];
        System.arraycopy(copyValue, 0, value, 0, copyValue.length);
        return value;
    }
}
