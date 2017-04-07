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
        return this.mutationProbability;
    }

    Double getCrossOverProbability() {
        return this.crossOverProbability;
    }

    Double getFitValueThreshold() {
        return this.fitValueThreshold;
    }

    int getIterationCount() {
        return this.iterationCount;
    }

    int getPopulationSize() {
        return this.populationSize;
    }
}
