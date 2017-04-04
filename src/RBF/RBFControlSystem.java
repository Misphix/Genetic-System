package RBF;

public class RBFControlSystem {

    public RBF rbf;
    public RBFControlSystem(RBF _rbf) {
        this.rbf = _rbf;
    }

    public double getSteeringAngle(double[] input) {
        return this.rbf.getOutput(input);
    }
}
