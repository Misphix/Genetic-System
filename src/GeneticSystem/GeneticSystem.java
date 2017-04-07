package GeneticSystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import RBF.RBF;
import Constants.Constants;

public class GeneticSystem {

    private String trainingData[];
    private int iterationCount;
    private int populationSize;
    private double crossProbability;
    private double mutProbability;
    private RBF rbf;
    private Dna solutionDNA;
    private List<Dna> dnaList;
    private int dimensions;
    private double fitValueThreshold;


    public GeneticSystem(int dimensions, GeneticParameter geneticParameter, RBF rbf) {
        this.crossProbability = geneticParameter.getCrossOverProbability();
        this.mutProbability = geneticParameter.getMutationProbability();
        this.iterationCount = geneticParameter.getIterationCount();
        this.populationSize = geneticParameter.getPopulationSize();
        this.dimensions = dimensions;
        this.fitValueThreshold = geneticParameter.getFitValueThreshold();
        this.rbf = rbf;
        dnaList = new ArrayList<>();

    }

    public void loadTrainingData(String trainingData[]) {
        this.trainingData = trainingData;
    }

    public void run() {
        for (int i = 0; i < populationSize; i++) {
            dnaList.add(generateDNA(dimensions, rbf.getNeuronCount()));
        }

        solutionDNA = copyDNA(dnaList.get(0));
        int t = 0;
        while (t++ < iterationCount) {
            reproduction();
            crossOver();
            mutate();
            if (solutionDNA.getFitnessValue() < fitValueThreshold) {
                break;
            }
        }
    }

    public Dna getSolutionDNA() {
        return solutionDNA;
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

    private void reproduction() {

        ArrayList<Dna> poolsDNAs = new ArrayList<>();
        double totalValue = 0;

        for (Dna dna : dnaList) {
            dna.setFitnessValue(calculateDiffError(dna));
            if (dna.getFitnessValue() < solutionDNA.getFitnessValue()) {
                solutionDNA = copyDNA(dna);
            }
            totalValue += dna.getFitnessValue();
        }

        dnaList.sort(DnaComparator);
        System.out.println(calculateDiffError(dnaList.get(0)));
        int greatDNACount = (int) ((double) populationSize * Constants.DNA_FIRST_PERCENT);
        int secondDNACount = (int) ((double) populationSize * Constants.DNA_SECOND_PERCENT);

        Dna greatDNA = dnaList.get(0);
        while (greatDNACount > 0) {
            poolsDNAs.add(copyDNA(greatDNA));
            greatDNACount--;
        }
        int i = 0;

        while (secondDNACount > 0) {
            double result = (totalValue / dnaList.get(i).getFitnessValue());
            int reproduceCount = (int) Math.round(result / populationSize);
            if (reproduceCount > 0) {
                for (int j = 0; j < reproduceCount; j++) {
                    poolsDNAs.add(copyDNA(dnaList.get(i)));
                    secondDNACount--;
                }
            } else {
                secondDNACount--;
            }
            i++;
        }

        while (poolsDNAs.size() < dnaList.size()) {
            Dna newDNA = generateDNA(dimensions, rbf.getNeuronCount());
            poolsDNAs.add(newDNA);

            if (newDNA.getFitnessValue() < solutionDNA.getFitnessValue()) {
                solutionDNA = copyDNA(newDNA);
            }
        }

        dnaList.clear();
        dnaList = poolsDNAs;

        assert dnaList.size() == populationSize;
    }

    private void crossOver() {
        int reserveCount = (int) (populationSize * Constants.DNA_RESERVE_PERCENT);

        for (int i = reserveCount; i < populationSize / 2; i++) {
            double probability = Math.random();
            if (probability < crossProbability) {
                dnaList.get(i).crossOver(dnaList.get(populationSize / 2 + i));
            }
        }
    }

    private void mutate() {
        int reserveCount = (int) (populationSize * Constants.DNA_RESERVE_PERCENT);

        for (int i = reserveCount; i < populationSize; i++) {
            double probability = Math.random();
            if (probability < mutProbability) {
                if (dnaList.get(i).getFitnessValue() > solutionDNA.getFitnessValue()) {
                    dnaList.get(i).mutate();
                }
            }
        }

    }

    private Comparator<Dna> DnaComparator = (Comparator<Dna>) (o1, o2) -> {
        if (o1.getFitnessValue() > o2.getFitnessValue()) {
            return 1;
        } else if (o1.getFitnessValue() < o2.getFitnessValue()) {
            return -1;
        } else {
            return 0;
        }
    };

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
