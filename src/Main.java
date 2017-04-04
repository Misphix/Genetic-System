import GeneticSystem.GeneticParameter;
import RBF.RBF;
import GeneticSystem.GeneticSystem;
import GeneticSystem.DNA;
import Util.FileUtility;
import Constants.Constants;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int neuronNumber = 7;
        GeneticParameter geneticParameter = new GeneticParameter();
        geneticParameter.setCrossOverProbability("0.6");
        geneticParameter.setFitValueThreshold("2.5");
        geneticParameter.setMutationProbability("0.7");
        geneticParameter.setPopulationSize("500");
        geneticParameter.setIterationCount("50");

        GeneticSystem geneticSystem = new GeneticSystem(Constants.RBF_DEFAULT_DIMENSION,geneticParameter, new RBF(neuronNumber));

        try {
            geneticSystem.loadTrainingData(FileUtility.getLines(FileUtility.getFileName("data")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        geneticSystem.run();
        RBF rbf = new RBF(neuronNumber);
        rbf.setParameter(
                geneticSystem.getSolutionDNA().getTheta(),
                geneticSystem.getSolutionDNA().getWeights(),
                geneticSystem.getSolutionDNA().getDistances(),
                geneticSystem.getSolutionDNA().getSigma()
        );
        System.out.println();
        DNA solution = geneticSystem.getSolutionDNA();
        System.out.println(solution);
    }
}
