package GeneticSystem;


import Util.DnaValidation;
import Constants.Constants;

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

    public void setWeights(double weights[]) {
        if (DnaValidation.validateWeights(weights)) {
            this.weights = weights;
        } else {
            assert false;
        }

    }

    public void setDistances(double distances[]) {
        if (DnaValidation.validateDistances(distances)) {
            this.distances = distances;
        } else {
            assert false;
        }

    }

    public void setSigma(double sigma[]) {
        if (DnaValidation.validateSigma(sigma)) {
            this.sigma = sigma;
        } else {
            assert false;
        }
    }

    private void setTheta(double theta) {
        if (DnaValidation.validateTheta(theta)) {
            this.theta = theta;
        } else {
            assert false;
        }

    }

    public double[] getWeights() {
        return weights;
    }

    public double[] getDistances() {
        return distances;
    }

    public double[] getSigma() {
        return sigma;
    }

    public double getTheta() {
        return theta;
    }


    void crossOver(Dna dna) {
        double sigma = Math.random();
        for (int k = 0; k < getDistances().length; k++) {
            double diffValue = getDistances()[k] - dna.getDistances()[k];
            if (DnaValidation.validateDistances(getDistances()[k] + (sigma * diffValue))) {
                getDistances()[k] += sigma * diffValue;
            } else {
                if (diffValue > 0) {
                    getDistances()[k] = sigma * Constants.DNA_MAX_DISTANCES;
                } else {
                    getDistances()[k] = sigma * Constants.DNA_MIN_DISTANCES;
                }
            }

            if (DnaValidation.validateDistances(dna.getDistances()[k] - (sigma * diffValue))) {
                dna.getDistances()[k] -= sigma * diffValue;
            } else {
                if (diffValue > 0) {
                    dna.getDistances()[k] = sigma * Constants.DNA_MIN_DISTANCES;
                } else {
                    dna.getDistances()[k] = sigma * Constants.DNA_MAX_DISTANCES;
                }
            }
        }

        for (int k = 0; k < getWeights().length; k++) {
            double diffValue = getWeights()[k] - dna.getWeights()[k];

            if (DnaValidation.validateWeights(getWeights()[k] + (sigma * diffValue))) {
                getWeights()[k] += sigma * diffValue;
            } else {
                if (diffValue > 0) {
                    getWeights()[k] = sigma * Constants.DNA_MAX_WEIGHT;
                } else {
                    getWeights()[k] = sigma * Constants.DNA_MIN_WEIGHT;
                }

            }

            if (DnaValidation.validateWeights(dna.getWeights()[k] - sigma * diffValue)) {
                dna.getWeights()[k] -= sigma * diffValue;
            } else {
                if (diffValue > 0) {
                    dna.getWeights()[k] = sigma * Constants.DNA_MAX_WEIGHT;
                } else {
                    dna.getWeights()[k] = sigma * Constants.DNA_MIN_WEIGHT;
                }
            }
        }

        for (int k = 0; k < getSigma().length; k++) {
            double diffValue = getSigma()[k] - dna.getSigma()[k];
            if (DnaValidation.validateSigma(getSigma()[k] + (sigma * diffValue))) {
                getSigma()[k] += sigma * diffValue;
            } else {
                if (diffValue > 0) {
                    getSigma()[k] = sigma * Constants.DNA_MAX_SIGMA;
                } else {
                    getSigma()[k] = sigma * Constants.DNA_MIN_SIGMA;
                }
            }


            if (DnaValidation.validateSigma(dna.getSigma()[k] - (sigma * diffValue))) {
                dna.getSigma()[k] -= sigma * diffValue;
            } else {
                if (diffValue > 0) {
                    dna.getSigma()[k] = sigma * Constants.DNA_MIN_SIGMA;
                } else {
                    dna.getSigma()[k] = sigma * Constants.DNA_MAX_SIGMA;
                }
            }

        }

        double diffValue = getTheta() - dna.getTheta();
        if (DnaValidation.validateTheta(getTheta() + (sigma * Math.pow(diffValue, 2)))) {
            setTheta(getTheta() + sigma * Math.pow(diffValue, 2));
        } else {
            if (diffValue > 0) {
                setTheta(sigma * Constants.DNA_MAX_THETA);
            } else {
                setTheta(sigma * Constants.DNA_MIN_THETA);
            }
        }

        if (DnaValidation.validateTheta(dna.getTheta() - (sigma * Math.pow(diffValue, 2)))) {
            dna.setTheta(dna.getTheta() - sigma * Math.pow(diffValue, 2));
        } else {
            if (diffValue > 0) {
                dna.setTheta(sigma * Constants.DNA_MIN_THETA);
            } else {
                dna.setTheta(sigma * Constants.DNA_MAX_THETA);
            }
        }

    }

    void mutate() {
        double random = Math.random();
        for (int j = 0; j < getDistances().length; j++) {
            double value = Constants.DNA_MAX_DISTANCES - getDistances()[j];
            getDistances()[j] = getDistances()[j] + ((random > 0.5) ? -1 * (Math.random() * getDistances()[j]) : Math.random() * value);
        }

        for (int j = 0; j < getWeights().length; j++) {
            double value = Constants.DNA_MAX_WEIGHT - getWeights()[j];
            getWeights()[j] = getWeights()[j] + ((random > 0.5) ? (Math.random() * ((-1 * Constants.DNA_MAX_WEIGHT) - getWeights()[j])) : Math.random() * value);
        }

        for (int j = 0; j < getSigma().length; j++) {
            double value = Constants.DNA_MAX_SIGMA - getSigma()[j];
            getSigma()[j] = getSigma()[j] + ((random > 0.5) ? -1 * (Math.random() * getSigma()[j]) : Math.random() * value);
        }

        double value = Constants.DNA_MAX_THETA - getTheta();
        setTheta(getTheta() + ((random > 0.5) ? -1 * (Math.random() * getTheta()) : Math.random() * value));
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
