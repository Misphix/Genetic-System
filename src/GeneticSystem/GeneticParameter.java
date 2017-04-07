package GeneticSystem;

public class GeneticParameter {
    private int iterationCount;
    private double mutationProbability;
    private double crossOverProbability;
    private double fitValueThreshold;
    private int populationSize;

    public void setIterationCount(String count) {
        iterationCount = Integer.valueOf(count);
    }

    public void setPopulationSize(String count) {
        populationSize = Integer.valueOf(count);
    }

    public void setMutationProbability(String rate) {
        mutationProbability = Double.valueOf(rate);
    }

    public void setCrossOverProbability(String rate) {
        crossOverProbability = Double.valueOf(rate);
    }

    public void setFitValueThreshold(String rate) {
        fitValueThreshold = Double.valueOf(rate);
    }

    Double getMutationProbability() {
        return mutationProbability;
    }

    Double getCrossOverProbability() {
        return crossOverProbability;
    }

    Double getFitValueThreshold() {
        return fitValueThreshold;
    }

    int getIterationCount() {
        return iterationCount;
    }

    int getPopulationSize() {
        return populationSize;
    }
}
