package Util;

import Constants.Constants;

public class DnaValidation {

    public static boolean validate (double _weights[],double _distances[],double _sigma[],double _theta) {
        return validateDistances(_distances) && validateWeights(_weights) && validateSigma(_sigma) && validateTheta(_theta);
    }

    private static boolean validateDistances(double value[]) {
        for (double aValue : value) {
            if (!validateDistances(aValue)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateWeights(double value[]) {
        for (double aValue : value) {
            if (!validateWeights(aValue)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateSigma(double value[]) {
        for (double aValue : value) {
            if (!validateSigma(aValue)) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateTheta(double value) {
        return value >= Constants.DNA_MIN_THETA && value <= Constants.DNA_MAX_THETA;
    }

    public static boolean validateDistances(double value) {
        return value >= Constants.DNA_MIN_DISTANCES && value <= Constants.DNA_MAX_DISTANCES;
    }

    public static boolean validateWeights(double value) {
        return value >= Constants.DNA_MIN_WEIGHT && value <= Constants.DNA_MAX_WEIGHT;
    }

    public static boolean validateSigma(double value) {
        return value >= Constants.DNA_MIN_SIGMA && value <= Constants.DNA_MAX_SIGMA;
    }
}
