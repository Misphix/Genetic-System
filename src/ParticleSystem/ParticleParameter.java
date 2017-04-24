package ParticleSystem;

public class ParticleParameter {
    private int iterationCount;
    private double fitValueThreshold;
    private int populationSize;

    public void setIterationCount(String count) {
        iterationCount = Integer.valueOf(count);
    }

    public void setPopulationSize(String count) {
        populationSize = Integer.valueOf(count);
    }

    public void setFitValueThreshold(String rate) {
        fitValueThreshold = Double.valueOf(rate);
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
