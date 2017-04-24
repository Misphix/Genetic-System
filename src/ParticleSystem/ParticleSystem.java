package ParticleSystem;

import RBF.RBF;
import Constants.Constants;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ParticleSystem {
    private String trainingData[];
    private int populationSize, dimensions, iterationCount;
    private double fitValueThreshold;
    private RBF rbf;
    private List<Dna> dnaList;
    private Dna bestDna;

    private Comparator<Dna> DnaComparator = (Comparator<Dna>) (o1, o2) -> {
        if (o1.getFitnessValue() > o2.getFitnessValue()) {
            return 1;
        } else if (o1.getFitnessValue() < o2.getFitnessValue()) {
            return -1;
        } else {
            return 0;
        }
    };

    public ParticleSystem(int dimensions, ParticleParameter particleParameter, RBF rbf) {
        this.rbf = rbf;
        this.dimensions = dimensions;
        this.populationSize = particleParameter.getPopulationSize();
        this.iterationCount = particleParameter.getIterationCount();
        this.fitValueThreshold = particleParameter.getFitValueThreshold();
        dnaList = new ArrayList<Dna>();
    }

    public void loadTrainingData(String trainingData[]) {
        this.trainingData = trainingData;
    }

    public void run() {
        for (int i = 0; i < populationSize; i++) {
            dnaList.add(generateDNA(dimensions, rbf.getNeuronCount()));
        }

        int t = 0;
        while (t++ < iterationCount) {
            dnaList.sort(DnaComparator);
            bestDna = copyDNA(dnaList.get(0));
        }
    }

    public Dna getBestDna() {
        return bestDna;
    }

    private Dna generateDNA(int dimensions, int neuronNumber) {
        double weights[] = new double[neuronNumber];
        double distances[] = new double[neuronNumber * dimensions];
        double sigma[] = new double[neuronNumber];
        double theta = Math.random();

        for (int i = 0; i < neuronNumber; i++) {
            weights[i] = (Math.random() * Constants.DNA_MAX_WEIGHT * 2) - Constants.DNA_MAX_WEIGHT;
            sigma[i] = (Math.random() * 10);
            for (int j = 0; j < dimensions; j++) {
                distances[i * dimensions + j] = (Math.random() * 30);
            }
        }

        Dna dna = new Dna(weights, distances, sigma, theta);
        dna.setFitnessValue(calculateDiffError(dna));

        return dna;
    }

    private double calculateDiffError(Dna dna) {
        rbf.setParameter(dna.getTheta(), dna.getWeights(), dna.getDistances(), dna.getSigma());
        double error = 0;

        for (String aTrainingData : trainingData) {
            String data[] = aTrainingData.split(" ");
            double input[] = {Double.valueOf(data[0]), Double.valueOf(data[1]), Double.valueOf(data[2])};
            error += Math.abs(Double.valueOf(data[3]) - rbf.getOutput(input));
        }

        return error / trainingData.length;
    }

    private Dna copyDNA(Dna copyDna) {
        Dna dna = new Dna(copyValue(copyDna.getWeights()), copyValue(copyDna.getDistances()), copyValue(copyDna.getSigma()), copyDna.getTheta());
        dna.setFitnessValue(copyDna.getFitnessValue());
        return dna;
    }

    private double[] copyValue(double copyValue[]) {
        double value[] = new double[copyValue.length];
        System.arraycopy(copyValue, 0, value, 0, copyValue.length);
        return value;
    }
}
