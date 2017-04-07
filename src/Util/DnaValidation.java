package Util;

import Constants.Constants;

public class DnaValidation {

    public static boolean validate (double _weights[],double _distances[],double _sigma[],double _theta) {
        return validateDistances(_distances) && validateWeights(_weights) && validateSigma(_sigma) && validateTheta(_theta);
    }

    public static boolean validateDistances(double value[]) {
        for(int i =0 ; i< value.length;i++) {
            if(!validateDistances(value[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateWeights(double value[]) {
        for(int i =0 ; i< value.length;i++) {
            if(!validateWeights(value[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateSigma(double value[]) {
        for(int i =0 ; i< value.length;i++) {
            if(!validateSigma(value[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean validateTheta(double value) {
        if(!(value >= Constants.DNA_MIN_THETA && value <= Constants.DNA_MAX_THETA )) {
            return false;
        }
        return true;
    }

    public static boolean validateDistances(double value) {
        if(!(value >= Constants.DNA_MIN_DISTANCES && value <= Constants.DNA_MAX_DISTANCES)) {
            return false;
        }
        return true;
    }

    public static boolean validateWeights(double value) {
        if(!(value >= Constants.DNA_MIN_WEIGHT && value <= Constants.DNA_MAX_WEIGHT)) {
            return false;
        }

        return true;
    }

    public static boolean validateSigma(double value) {
        if(!(value >= Constants.DNA_MIN_SIGMA && value <= Constants.DNA_MAX_SIGMA)) {
            return false;
        }

        return true;
    }


}
