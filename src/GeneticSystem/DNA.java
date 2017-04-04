package GeneticSystem;


import Util.DNAValidation;
import Constants.Constants;

public class DNA {
    private double weights[];
    private double sigma[];
    private double distances[];
    private double theta;
    private double fitnessVaule = Double.MAX_VALUE;

    public DNA(double _weights[],double _distances[],double _sigma[],double _theta) {
        DNAValidation.validate(_weights,_distances,_sigma,_theta);
        this.weights = _weights;
        this.distances = _distances;
        this.sigma = _sigma;
        this.theta = _theta;

    }

    public void setFitnessValue(double value) {
        this.fitnessVaule = value;
    }

    public double getFitnessVaule() {
        return this.fitnessVaule;
    }

    public void setWeights(double _weights[]) {
        if(DNAValidation.validateWeights(_weights)) {
            this.weights = _weights;
        } else {
            assert false;
        }

    }

    public void setDistances(double _distances[]) {
        if(DNAValidation.validateDistances(_distances)) {
            this.distances = _distances;
        } else {
            assert false;
        }

    }

    public void setSigma(double _sigma[]) {
        if(DNAValidation.validateSigma(_sigma)) {
            this.sigma = _sigma;
        } else {
            assert false;
        }
    }

    public void setTheta(double _theta) {
        if(DNAValidation.validateTheta(_theta)) {
            this.theta = _theta;
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


    public void crossOver(DNA dna) {
        double sigma = Math.random();
        for(int k=0 ;k <this.getDistances().length;k++) {
            double diffValue = this.getDistances()[k] - dna.getDistances()[k];
            if(DNAValidation.validateDistances(this.getDistances()[k] + (sigma*diffValue))) {
                this.getDistances()[k] += sigma * diffValue;
            } else {
                if(diffValue > 0) {
                    this.getDistances()[k] = sigma * Constants.DNA_MAX_DISTANCES;
                } else {
                    this.getDistances()[k] = sigma * Constants.DNA_MIN_DISTANCES;
                }
            }

            if(DNAValidation.validateDistances(dna.getDistances()[k] - (sigma*diffValue))) {
                dna.getDistances()[k] -= sigma * diffValue;
            } else {
                if(diffValue > 0) {
                    dna.getDistances()[k] = sigma * Constants.DNA_MIN_DISTANCES;
                } else {
                    dna.getDistances()[k] = sigma * Constants.DNA_MAX_DISTANCES;
                }
            }
        }

        for(int k=0 ;k <this.getWeights().length;k++) {
            double diffValue = this.getWeights()[k] - dna.getWeights()[k];

            if(DNAValidation.validateWeights(this.getWeights()[k] + (sigma * diffValue))) {
                this.getWeights()[k] += sigma * diffValue;
            } else {
                if(diffValue > 0 ){
                    this.getWeights()[k] = sigma * Constants.DNA_MAX_WEIGHT;
                }else {
                    this.getWeights()[k] = sigma * Constants.DNA_MIN_WEIGHT;
                }

            }

            if(DNAValidation.validateWeights(dna.getWeights()[k] - sigma * diffValue)) {
                dna.getWeights()[k] -= sigma * diffValue;
            } else {
                if(diffValue > 0 ){
                    dna.getWeights()[k] = sigma * Constants.DNA_MAX_WEIGHT  ;
                } else {
                    dna.getWeights()[k] = sigma * Constants.DNA_MIN_WEIGHT ;
                }
            }
        }

        for(int k=0 ;k <this.getSigma().length;k++) {
            double diffValue = this.getSigma()[k] - dna.getSigma()[k];
            if(DNAValidation.validateSigma(this.getSigma()[k]+ (sigma*diffValue))) {
                this.getSigma()[k] += sigma * diffValue;
            } else {
                if(diffValue > 0) {
                    this.getSigma()[k] = sigma * Constants.DNA_MAX_SIGMA;
                } else {
                    this.getSigma()[k] = sigma * Constants.DNA_MIN_SIGMA;
                }
            }


            if(DNAValidation.validateSigma(dna.getSigma()[k]  - (sigma*diffValue))) {
                dna.getSigma()[k] -= sigma * diffValue;
            } else {
                if(diffValue > 0) {
                    dna.getSigma()[k] = sigma * Constants.DNA_MIN_SIGMA;
                } else {
                    dna.getSigma()[k] = sigma * Constants.DNA_MAX_SIGMA;
                }
            }

        }

        double diffValue = this.getTheta() - dna.getTheta();
        if(DNAValidation.validateTheta(this.getTheta()+ (sigma * Math.pow(diffValue,2) ))) {
            this.setTheta(this.getTheta()+ sigma * Math.pow(diffValue,2) );
        } else {
            if(diffValue > 0 ) {
                this.setTheta(sigma*Constants.DNA_MAX_THETA);
            } else {
                this.setTheta(sigma*Constants.DNA_MIN_THETA);
            }
        }

        if(DNAValidation.validateTheta(dna.getTheta()- (sigma * Math.pow(diffValue,2) ))) {
            dna.setTheta(dna.getTheta()-sigma * Math.pow(diffValue,2) );
        } else {
            if(diffValue > 0) {
                dna.setTheta(sigma*Constants.DNA_MIN_THETA);
            } else {
                dna.setTheta(sigma*Constants.DNA_MAX_THETA);
            }
        }

    }
    public void mutate() {
        double random =Math.random();
        for(int j =0 ; j < this.getDistances().length;j++) {
            double value = Constants.DNA_MAX_DISTANCES - this.getDistances()[j];
            this.getDistances()[j] = this.getDistances()[j] + ((random > 0.5)? -1*(Math.random()*this.getDistances()[j]): Math.random()*value);
        }

        for(int j =0 ; j < this.getWeights().length;j++) {
            double value = Constants.DNA_MAX_WEIGHT - this.getWeights()[j];
            this.getWeights()[j] = this.getWeights()[j] + ((random > 0.5)? (Math.random()*((-1*Constants.DNA_MAX_WEIGHT) -this.getWeights()[j])): Math.random()*value);
        }

        for(int j =0 ; j < this.getSigma().length;j++) {
            double value = Constants.DNA_MAX_SIGMA - this.getSigma()[j];
            this.getSigma()[j] = this.getSigma()[j] + ((random > 0.5)? -1*(Math.random()*this.getSigma()[j]): Math.random()*value);
        }

        double value = Constants.DNA_MAX_THETA - this.getTheta();
        this.setTheta( this.getTheta() + ((random > 0.5)? -1*(Math.random()*this.getTheta()): Math.random()*value));
    }

    @Override
    public String toString() {
        String result = "";

        result += String.format("%.10f", theta);

        for (double value : weights ) {
            result += String.format(" %.10f", value);
        }

        for (double value : distances ) {
            result += String.format(" %.10f", value);
        }

        for (double value : sigma ) {
            result += String.format(" %.10f", value);
        }

        return result;
    }
}
