package GeneticSystem;

/**
 * Created by jiaching on 2016/5/21.
 */
public class GeneticParameter {
    int iterationCount;
    double mutationProbability;
    double crossOverProbability;
    double fitValueThreshold;
    int populationSize;

    public void setIterationCount(String count) {
        this.iterationCount = Integer.valueOf(count);
    }

    public void setPopulationSize(String count) {
        this.populationSize = Integer.valueOf(count);
    }

    public void setMutationProbability(String rate) {
        this.mutationProbability = Double.valueOf(rate);
    }

    public void setCrossOverProbability(String rate) {
        this.crossOverProbability = Double.valueOf(rate);
    }

    public void setFitValueThreshold(String rate) {
        this.fitValueThreshold = Double.valueOf(rate);
    }

    public Double getMutationProbability() {return this.mutationProbability;}
    public Double getCrossOverProbability() {return this.crossOverProbability;}
    public Double getFitValueThreshold() {return this.fitValueThreshold;}
    public int getIterationCount() {return this.iterationCount; }
    public int getPopulationSize() {return this.populationSize;}
}
